package Storeges;

import Controllers.CarOrderController;
import Items.Item;
import Observers.StorageObserver;

import java.util.LinkedList;
import java.util.Queue;

public class Storage<T extends Item> {
    private final Queue<T> items;
    private final int capacity;
    private final String name;
    private StorageObserver observer;

    public Storage(int capacity, String name) {
        items = new LinkedList<>();
        this.capacity = capacity;
        this.name = name;
    }

    public void setObserver(StorageObserver controller) {
        this.observer = controller;
    }

    public synchronized void put(T item) throws InterruptedException{
        while(items.size() >= capacity) {
            wait();
        }
        items.add(item);
        if (observer != null) {
            observer.update();
        }
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while(items.isEmpty()) {
            wait();
        }

        T item = items.poll();
        notifyAll();
        if (observer != null) {
            observer.update();
        }
        return item;
    }

    public synchronized int getCurrentSize() {
        return items.size();
    }

    public synchronized int getCapacity() {
        return capacity;
    }
}
