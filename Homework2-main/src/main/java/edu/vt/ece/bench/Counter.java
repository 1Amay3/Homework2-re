package edu.vt.ece.bench;

public class Counter {
    private int value;
    public Counter(int c){
        value = c;
    }
    public int getAndIncrement(){
        // System.out.println("Thread " + ((TestThread)Thread.currentThread()).getThreadId() + " value " + value);
        int temp = value;
        value = temp + 1;
        return temp;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
