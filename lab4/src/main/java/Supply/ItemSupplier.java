package Supply;

import Items.Item;
import Storeges.Storage;

import java.util.function.Supplier;

public class ItemSupplier<T extends Item> implements Runnable {
    private final Storage<T> storage;
    private final Supplier<T> factory;
    private static int delay;

    public ItemSupplier(Storage<T> storage, Supplier<T> factory, int initialDelay) {
        this.storage = storage;
        this.factory = factory;//takes lambda
        this.delay = initialDelay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(delay); //from GUI
                T item = factory.get();
                storage.put(item);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
