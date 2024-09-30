package edu.vt.ece.locks;
import edu.vt.ece.bench.Counter;
import edu.vt.ece.bench.TestThread;


import edu.vt.ece.bench.ThreadId;

public class TreePeterson implements Lock {

    private final Peterson[] tree;
    private final int numThreads;
    private final int[] path;

    public TreePeterson(int numThreads) {
        this.numThreads = numThreads;
        this.tree = new Peterson[numThreads - 1];
        this.path = new int[(int) (Math.log(numThreads) / Math.log(2)) + 1];

        for (int i = 0; i < tree.length; i++) {
            tree[i] = new Peterson();
        }
    }

    @Override
    public void lock() {
        int id = ((ThreadId) Thread.currentThread()).getThreadId();
        int node = id + numThreads - 1;
        int level = 0;

        while (node > 0) {
            int parent = (node - 1) / 2;
            tree[parent].lock();
            path[level++] = parent;
            node = parent;
        }
    }

    @Override
    public void unlock() {
        int level = path.length - 1;

        while (level >= 0) {
            int node = path[level--];
            tree[node].unlock();
}
}
}