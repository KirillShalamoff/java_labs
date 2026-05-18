package Controllers;

import Items.Accessory;
import Items.Auto;
import Items.Body;
import Items.Engine;
import Storeges.Storage;
import IdGenerator.IdGenerator;

public class BuildCarTask implements Runnable {
    private final Storage<Engine> engineStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Auto> autoStorage;

    public BuildCarTask(Storage<Engine> engineStorage, Storage<Body> bodyStorage,
                        Storage<Accessory> accessoryStorage, Storage<Auto> autoStorage) {

        this.autoStorage = autoStorage;
        this.accessoryStorage = accessoryStorage;
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
    }

    @Override
    public void run() {
        try {
            Body b = bodyStorage.get();
            Accessory a = accessoryStorage.get();
            Engine e = engineStorage.get();

            Auto auto = new Auto(IdGenerator.nextId(), b, e, a);
            autoStorage.put(auto);

            // Добавь это, чтобы увидеть жизнь в консоли!

        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
