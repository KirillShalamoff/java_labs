package Utils;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {
    private final Queue<Runnable> tasks = new LinkedList<>();

    // Метод для добавления задачи (используется Контроллером)
    public synchronized void enqueue(Runnable task) {
        tasks.add(task);
        // Будим один из потоков-воркеров, который ждет в методе dequeue
        this.notify();
    }

    // Метод для получения задачи (используется Воркерами)
    public synchronized Runnable dequeue() throws InterruptedException {
        while (tasks.isEmpty()) {
            // Если задач нет, поток "засыпает" и отпускает монитор (synchronized)
            this.wait();
        }
        return tasks.poll();
    }

    public synchronized int size() {
        return tasks.size();
    }
}