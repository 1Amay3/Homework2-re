package edu.vt.ece.locks;

import java.util.concurrent.atomic.AtomicBoolean;
import edu.vt.ece.bench.ThreadId;


public class LockOne implements Lock{

    private AtomicBoolean[] flag = new AtomicBoolean[2];

    public LockOne() {
        flag[0] = new AtomicBoolean();
        flag[1] = new AtomicBoolean();
    }

    @Override
    public void lock() {
        int i = ((ThreadId)Thread.currentThread()).getThreadId();
        int j = 1 - i;
        flag[i].set(true);
        while(flag[j].get());
    }

    @Override
    public void unlock() {
        int i = ((ThreadId)Thread.currentThread()).getThreadId();
        flag[i].set(false);
    }
}
