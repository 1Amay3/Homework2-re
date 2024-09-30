package edu.vt.ece.locks;

import edu.vt.ece.bench.ThreadId;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class LBakery implements Lock {

    private final int l;

    private final int x;
    private volatile AtomicInteger criticalThreads;
    private volatile Timestamp[][] label;

    private volatile AtomicBoolean[][] flag;

    private TimestampSystem[] timestampSystems;


    public LBakery(int l, int n) {
        this.l = l;
        this.x = n;
        this.flag = new AtomicBoolean[l][n];
        this.label = new Timestamp[l][n];
        this.timestampSystems = new LBakeryTimestampSystem[l];
        this.criticalThreads = new AtomicInteger(0);


        for(int i = 0; i < l; i++) {
            timestampSystems[i] = new LBakeryTimestampSystem(n);
            for(int j = 0; j < n; j++) {
                flag[i][j] = new AtomicBoolean(false);
                label[i][j] = new LBakeryTimestamp(0,j);
            }
        }
    }

    @Override
    public void lock() {
        int i = ((ThreadId)Thread.currentThread()).getThreadId();
        for(int j = 0; j < l; j++) {
            flag[j][i].set(true);
            Timestamp max = findMaxStamp(timestampSystems[j].scan());
            label[j][i] = new LBakeryTimestamp((max != null) ? max.getValue() + 1 : 1, i);
            timestampSystems[j].label(label[j][i], i);
            for (int k = 0; k < x; k++) {
                while ((flag[j][k].get() && k != i && label[j][i].compare(label[j][k])) || criticalThreads.get() >= l) {

                }
            }
            criticalThreads.incrementAndGet();
        }

    }

    private Timestamp findMaxStamp(Timestamp[] timestamps) {
        long max = Long.MIN_VALUE;

        Timestamp maxTimeStamp = null;

        for(Timestamp timestamp : timestamps) {
            if(timestamp !=null && timestamp.getValue() > max) {
                maxTimeStamp = timestamp;
                max = timestamp.getValue();
            }
        }
        return maxTimeStamp;
    }

    @Override
    public void unlock() {
        int i = ((ThreadId)Thread.currentThread()).getThreadId();
        criticalThreads.decrementAndGet();
        for(int j = l-1; j >= 0 ;j--){
            flag[j][i].set(false);
        }


    }

    private static class LBakeryTimestamp implements Timestamp{
        private long value;
        private int i;
        public LBakeryTimestamp(long value, int i) {
            this.i = i;
            this.value = value;

        }
        public long getValue() {
            return value;
        }
        @Override
        public boolean compare(Timestamp t1) {

            if(t1 instanceof LBakeryTimestamp) {

                LBakeryTimestamp b = (LBakeryTimestamp) t1;

                if(this.value < b.value || (this.i < b.i && this.value == b.value))
                {
                    return true;
                }
            }
            return false;
        }
    }
    private static class LBakeryTimestampSystem implements TimestampSystem{
        private Timestamp[] timestamps;
        LBakeryTimestampSystem(int n) {
            timestamps = new Timestamp[n];
            for(int i = 0 ; i < n ; i++){
                timestamps[i] = new LBakeryTimestamp(0,i);
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