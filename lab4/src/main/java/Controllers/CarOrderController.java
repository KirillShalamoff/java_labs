package Controllers;

import Items.Accessory;
import Items.Auto;
import Items.Body;
import Items.Engine;
import Observers.StorageObserver;
import Pool.ThreadPool;
import Storeges.Storage;

public class CarOrderController implements StorageObserver {
    private final ThreadPool pool;
    private final Storage<Auto> autoStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<Engine> engineStorage;
    private final Storage<Accessory> accessoryStorage;

    public CarOrderController(ThreadPool pool,
                              Storage<Auto> autoStorage,
                              Storage<Body> bodyStorage,
                              Storage<Engine> motorStorage,
                              Storage<Accessory> accessoryStorage) {
        this.pool = pool;
        this.autoStorage = autoStorage;
        this.bodyStorage = bodyStorage;
        this.engineStorage = motorStorage;
        this.accessoryStorage = accessoryStorage;
    }

    @Override
    public synchronized void update() {
        int carsInStorage = autoStorage.getCurrentSize();

        int tasksInQueue = pool.getWaitingTasksCount();

        int capacity = autoStorage.getCapacity();

        int neededTasks = capacity - (carsInStorage + tasksInQueue);

        if (neededTasks > 1) {
            for (int i = 0; i < neededTasks; i++) {
                pool.execute(new BuildCarTask(engineStorage, bodyStorage, accessoryStorage, autoStorage));
            }
        }
    }
}