package Controllers;

import Items.Accessory;
import Items.Body;
import Items.Engine;
import Storeges.Storage;
import Pool.ThreadPool;
import Supply.ItemSupplier;
import Deal.Dealer;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import java.util.List;

public class UIController {
    @FXML private ProgressBar bodyBar;
    @FXML private ProgressBar engineBar;
    @FXML private ProgressBar accessoryBar;
    @FXML private ProgressBar autoBar;

    @FXML private Label bodyLabel;
    @FXML private Label engineLabel;
    @FXML private Label accessoryLabel;
    @FXML private Label autoLabel;

    @FXML private Slider bodySupplierSlider;
    @FXML private Slider engineSupplierSlider;
    @FXML private Slider accessorySupplierSlider;
    @FXML private Slider dealerSlider;
    @FXML private Label tasksInQueueLabel;

    private Storage<?> bodyStorage, engineStorage, accessoryStorage, autoStorage;
    private ThreadPool pool;

    public void initData(Storage<?> body, Storage<?> engine, Storage<?> accessory, Storage<?> auto,
                         ThreadPool pool,
                         List<ItemSupplier<Body>> bodySuppliers,
                         List<ItemSupplier<Engine>> engineSuppliers,
                         List<ItemSupplier<Accessory>> accessorySuppliers,
                         List<Dealer> dealers) {
        this.bodyStorage = body;
        this.engineStorage = engine;
        this.accessoryStorage = accessory;
        this.autoStorage = auto;
        this.pool = pool;

        // 1. Слайдер поставщиков КУЗОВОВ
        bodySupplierSlider.valueProperty().addListener((obs, old, val) -> {
            bodySuppliers.forEach(s -> s.setDelay(val.intValue()));
        });

        // 2. Слайдер поставщиков ДВИГАТЕЛЕЙ
        engineSupplierSlider.valueProperty().addListener((obs, old, val) -> {
            engineSuppliers.forEach(s -> s.setDelay(val.intValue()));
        });

        // 3. Слайдер поставщиков АКСЕССУАРОВ
        accessorySupplierSlider.valueProperty().addListener((obs, old, val) -> {
            accessorySuppliers.forEach(s -> s.setDelay(val.intValue()));
        });

        // 4. Слайдер ДИЛЕРОВ
        dealerSlider.valueProperty().addListener((obs, old, val) -> {
            dealers.forEach(d -> d.setDelay(val.intValue()));
        });

        // Главный таймер обновления GUI
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateUI();
            }
        }.start();
    }

    private void updateUI() {
        // Обновляем прогресс-бары и текстовые метки
        updateSingleStorage(bodyStorage, bodyBar, bodyLabel, "Bodies");
        updateSingleStorage(engineStorage, engineBar, engineLabel, "Engines");
        updateSingleStorage(accessoryStorage, accessoryBar, accessoryLabel, "Accessories");
        updateSingleStorage(autoStorage, autoBar, autoLabel, "Autos");

        // Обновляем информацию об очереди в ThreadPool
        if (pool != null && tasksInQueueLabel != null) {
            tasksInQueueLabel.setText("Tasks in Queue: " + pool.getWaitingTasksCount());
        }
    }

    private void updateSingleStorage(Storage<?> storage, ProgressBar bar, Label label, String prefix) {
        if (storage != null && bar != null && label != null) {
            double current = (double) storage.getCurrentSize();
            double capacity = (double) storage.getCapacity();

            // Устанавливаем прогресс (значение от 0.0 до 1.0)
            if (capacity > 0) {
                bar.setProgress(current / capacity);
            } else {
                bar.setProgress(0);
            }

            // Обновляем текст в формате "Autos: 5 / 100"
            label.setText(prefix + ": " + (int)current + " / " + (int)capacity);
        }
    }
}