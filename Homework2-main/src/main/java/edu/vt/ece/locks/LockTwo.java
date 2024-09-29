package edu.vt.ece.locks;

import java.util.concurrent.atomic.AtomicInteger;
import edu.vt.ece.bench.ThreadId;

public class LockTwo implements Lock{

    private AtomicInteger victim;

    public LockTwo() {
        victim = new AtomicInteger();
    }

    @Override
    public void lock() {
        int i = ((ThreadId)Thread.currentThread()).getThreadId();
        victim.set(i);
        while(victim.get() == i);
    }

    @Override
    public void unlock() {}

}
