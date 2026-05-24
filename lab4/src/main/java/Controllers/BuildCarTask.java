package Controllers;

import Items.Accessory;
import Items.Auto;
import Items.Body;
import Items.Engine;
import Storeges.Storage;
import IdGenerator.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class BuildCarTask implements Runnable {
    private final Storage<Engine> engineStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Auto> autoStorage;
    private final int accessoryCountRequired;

    public BuildCarTask(Storage<Engine> engineStorage, Storage<Body> bodyStorage,
                        Storage<Accessory> accessoryStorage, Storage<Auto> autoStorage, int accessoryCountRequired) {
        this.accessoryCountRequired = accessoryCountRequired;
        this.autoStorage = autoStorage;
        this.accessoryStorage = accessoryStorage;
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
    }

    @Override
    public void run() {
        try {
            Body b = bodyStorage.get();
            List<Accessory> extractedAccories = new ArrayList<>();
            for(int i = 0; i < accessoryCountRequired; i++) {
                extractedAccories.add(accessoryStorage.get());
            }
            Engine e = engineStorage.get();

            Auto auto = new Auto(IdGenerator.nextId(), b, e, extractedAccories);
            autoStorage.put(auto);

            // Добавь это, чтобы увидеть жизнь в консоли!

        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
