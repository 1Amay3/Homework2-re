package edu.vt.ece.bench;

public class TestThread extends Thread implements ThreadId {
    private static int ID_GEN = 0;
    public static void reset() {
        ID_GEN = 0;
    }

    private static final int MAX_COUNT = 1000;

    private Counter counter;
    private int id;
    public TestThread(Counter counter) {
        id = ID_GEN++;
        this.counter = counter;
    }

    @Override
    public void run() {
        for(int i=0; i<MAX_COUNT; i++)
            counter.getAndIncrement();
        System.out.println("Thread " + id + " DONE.. <Counter:" + counter + ">");
    }

    public int getThreadId(){
        return id;
    }
}
