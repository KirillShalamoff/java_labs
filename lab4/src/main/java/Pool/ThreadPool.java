package Pool;

import Utils.TaskQueue;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadPool {
    private static final Logger logger = LogManager.getLogger(ThreadPool.class);

    private final TaskQueue taskQueue = new TaskQueue();
    private final List<Worker> workers = new ArrayList<>();

    public ThreadPool(int threadCount) {
        for (int i = 0; i < threadCount; i++) {
            Worker worker = new Worker();
            worker.setName("Worker-Thread-" + i);
            workers.add(worker);
            worker.start();
        }
        logger.info("ThreadPool создан с {} потоками.", threadCount);
    }

    public void execute(Runnable task) {
        taskQueue.enqueue(task);
    }

    public int getWaitingTasksCount() {
        return taskQueue.size();
    }

    public void shutdown() {
        logger.info("Завершение работы ThreadPool...");
        for (Worker worker : workers) {
            worker.interrupt();
        }
    }

    // Внутренний класс воркера
    private class Worker extends Thread {
        @Override
        public void run() {
            try {
                // Работаем, пока поток не прерван сигналом shutdown()
                while (!isInterrupted()) {
                    // Здесь поток будет ждать, пока в очереди не появится задача
                    Runnable task = taskQueue.dequeue();

                    if (task != null) {
                        task.run();
                    }
                }
            } catch (InterruptedException e) {
                // interrupt() во время ожидания (wait) бросает исключение
                // Это штатный выход из потока
                Thread.currentThread().interrupt();
            }
            logger.debug("{} завершил работу.", getName());
        }
    }
}