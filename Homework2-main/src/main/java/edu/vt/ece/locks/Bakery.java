package edu.vt.ece.locks;
import edu.vt.ece.bench.Counter;
import edu.vt.ece.bench.TestThread;
import edu.vt.ece.bench.ThreadId;

import java.util.concurrent.atomic.AtomicBoolean;


public class Bakery implements Lock {
    private int n;
    private volatile AtomicBoolean[] flag;

    private volatile Timestamp[] label;
    private TimestampSystem timestampSystem;
    public Bakery() {
        this(2);
    }

    public Bakery(int n) {
        this.n = n;
        this.flag = new AtomicBoolean[n];
        this.label = new Timestamp[n];
        this.timestampSystem = new BakeryTimestampSystem(n);


        for(int i =0 ; i < n ; i++){
            this.flag[i]= new AtomicBoolean();
            this.label[i] = null;
        }

    }

    private Timestamp findMaxTimestamp(Timestamp[] timestamps) {
        long max = Long.MIN_VALUE;
        Timestamp maxTimestamp = timestamps[0];
        for (Timestamp timestamp : timestamps) {
            if (timestamp!=null && max < timestamp.getValue()) {
                maxTimestamp = timestamp;
                max = timestamp.getValue();
            }
        }
        return maxTimestamp;
    }

    @Override
    public void lock() {
        int i = ((ThreadId)Thread.currentThread()).getThreadId();
        flag[i].set(true);
        Timestamp max = (BakeryTimestamp)findMaxTimestamp(timestampSystem.scan());
        label[i] = new BakeryTimestamp(max.getValue()+1,i);
        timestampSystem.label(label[i],i);
        for(int k=0; k < n ; k++){
            if(k!=i){
                while(flag[k].get() && label[k] !=null && (label[k].compare(label[i]) ) ){

                }
            }
        }

    }

    @Override
    public void unlock() {
        flag[((ThreadId)Thread.currentThread()).getThreadId()].set(false);
    }
    private static class BakeryTimestamp implements Timestamp{
        private long value;
        private int i;
        public BakeryTimestamp(long value, int i) {
            this.value = value;
            this.i = i;
        }
        public long getValue() {
            return value;
        }
        @Override
        public boolean compare(Timestamp t1) {
            if(t1 instanceof BakeryTimestamp) {
                BakeryTimestamp b = (BakeryTimestamp) t1;
                if(this.value < b.value || (b.i < this.i && this.value == b.value))
                {
                    return true;
                }
            }
            return false;
        }
    }
    private static class BakeryTimestampSystem implements TimestampSystem{
        private Timestamp[] timestamps;
        BakeryTimestampSystem(int n) {
            timestamps = new Timestamp[n];
            for(int i = 0 ; i < n ; i++){
                timestamps[i] = new BakeryTimestamp(0,i);
            }
        }
        @Override
        public Timestamp[] scan() {

            return timestamps;
        }

        @Override
        public void label(Timestamp timestamp, int i) {
            timestamps[i] = timestamp;
        }

    }
}