package edu.vt.ece.bench;

import edu.vt.ece.locks.Lock;

public class SharedCounter extends Counter{
    private Lock lock;

    public SharedCounter(int c, Lock lock) {
        super(c);
        this.lock = lock;
    }

    @Override
    public int getAndIncrement() {
        lock.lock();
        int temp = -1;
        try {
            temp = super.getAndIncrement();
        } finally {
            lock.unlock();
        }
        return temp;
    }
}
