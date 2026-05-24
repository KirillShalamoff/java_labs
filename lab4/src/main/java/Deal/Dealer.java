package Deal;

import Items.Accessory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Items.Auto;
import Storeges.Storage;

import java.util.ArrayList;
import java.util.List;

public class Dealer implements Runnable{
    private static final Logger logger = LogManager.getLogger(Dealer.class);
    private final int id;
    private final boolean logEnabled;
    private final Storage<Auto> storage;
    private static int delay;

    public Dealer(Storage<Auto> storage, int initialDelay, int id, boolean logEnabled) {
        this.storage = storage;
        this.delay = initialDelay;
        this.id = id;
        this.logEnabled = logEnabled;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(delay);
                Auto auto = storage.get();
                List<Accessory> accesories = auto.getAccessories();
                List<Integer> accessoryIds = new ArrayList<>();
                for(int i = 0; i < accesories.size(); i++) {
                    accessoryIds.add(accesories.get(i).getId());
                }

                if (logEnabled) {
                    logger.info("Dealer {}: Auto {} (Body: {}, Motor: {}, Accessory: {})",
                            id,
                            auto.getId(),
                            auto.getBody().getId(),
                            auto.getEngine().getId(),
                            accessoryIds);
                }
            }
        } catch(InterruptedException e) {
            logger.warn("dealer {} interrupted", id);
            Thread.currentThread().interrupt();

        }
    }
}
