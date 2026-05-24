import Controllers.CarOrderController;
import Controllers.UIController;
import Deal.Dealer;
import Items.Accessory;
import Items.Auto;
import Items.Body;
import Items.Engine;
import Pool.ThreadPool;
import Storeges.Storage;
import Supply.ItemSupplier;
import Utils.ConfigReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import IdGenerator.IdGenerator;

import java.util.ArrayList;
import java.util.List;
//готовиться к теории

public class MainApp extends Application {

    private final List<Thread> allThreads = new ArrayList<>();
    private final List<ItemSupplier<Body>> bodySuppliers = new ArrayList<>();
    private final List<ItemSupplier<Engine>> engineSuppliers = new ArrayList<>();
    private final List<ItemSupplier<Accessory>> accessorySuppliers = new ArrayList<>();

    private final List<Dealer> allDealers = new ArrayList<>();

    private ConfigReader config;
    private ThreadPool workersPool;

    @Override
    public void start(Stage primaryStage) {
        try {
            config = new ConfigReader("config.properties");
            workersPool = new ThreadPool(config.workers);

            Storage<Body> bodyStorage = new Storage<>(config.storageBodySize, "Body Storage");
            Storage<Engine> engineStorage = new Storage<>(config.storageEngineSize, "Engine Storage");
            Storage<Accessory> accessoryStorage = new Storage<>(config.storageAccessorySize, "Accessory Storage");
            Storage<Auto> autoStorage = new Storage<>(config.storageAutoSize, "Auto Storage");
            int accessoryCountRequired = config.accessoryCountRequired;

            CarOrderController controller = new CarOrderController(workersPool, autoStorage,
                    bodyStorage, engineStorage, accessoryStorage, accessoryCountRequired);
            autoStorage.setObserver(controller);


            for (int i = 0; i < config.accessorySuppliers; i++) {
                accessorySuppliers.add(new ItemSupplier<>(accessoryStorage, () -> new Accessory(IdGenerator.nextId()), 1000));
            }
            for (int i = 0; i < 3; i++) {
                bodySuppliers.add(new ItemSupplier<>(bodyStorage, () -> new Body(IdGenerator.nextId()), 1000));
            }
            for (int i = 0; i < 3; i++) {
                engineSuppliers.add(new ItemSupplier<>(engineStorage, () -> new Engine(IdGenerator.nextId()), 1000));
            }

            for (int i = 0; i < config.dealers; i++) {
                allDealers.add(new Dealer(autoStorage, i, 1000, config.logSale));
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/factory_view.fxml"));
            Parent root = loader.load();
            UIController uiController = loader.getController();

            uiController.initData(bodyStorage, engineStorage, accessoryStorage,
                    autoStorage, workersPool, bodySuppliers, engineSuppliers, accessorySuppliers, allDealers);

            primaryStage.setTitle("NSU Car Factory Monitor");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            new Thread(() -> {
                try {
                    for (ItemSupplier<Body> s : bodySuppliers) {
                        startThread(s, "BodySupplier");
                    }
                    // Запускаем поставщиков двигателей
                    for (ItemSupplier<Engine> s : engineSuppliers) {
                        startThread(s, "EngineSupplier");
                    }
                    // Запускаем поставщиков аксессуаров
                    for (ItemSupplier<Accessory> s : accessorySuppliers) {
                        startThread(s, "AccessorySupplier");
                    }

                    Thread.sleep(2000);

                    for (Dealer d : allDealers) {
                        startThread(d, "Dealer");
                    }

                    controller.update();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Logic-Starter").start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startThread(Runnable runnable, String name) {
        Thread t = new Thread(runnable, name);
        t.setDaemon(true); // Процесс убьется при закрытии окна
        allThreads.add(t);
        t.start();
    }

    @Override
    public void stop() {
        if (workersPool != null) workersPool.shutdown();
        for (Thread t : allThreads) t.interrupt();
    }

    public static void main(String[] args) {
        launch(args);
    }
}