package edu.vt.ece.locks;

import edu.vt.ece.bench.ThreadId;

public class TreePeterson implements Lock {

    private final Peterson[] tree;  // Array of Peterson locks representing the tree
    private final int numThreads;   // Total number of threads
    private final int[] path;       // To store the path from leaf to root for each thread

    public TreePeterson(int numThreads) {
        this.numThreads = numThreads;
        this.tree = new Peterson[numThreads - 1];  // Number of Peterson locks = numThreads - 1
        this.path = new int[(int) (Math.log(numThreads) / Math.log(2)) + 1];  // Path for each thread

        // Initialize the tree with Peterson locks
        for (int i = 0; i < tree.length; i++) {
            tree[i] = new Peterson();
        }
    }

    @Override
    public void lock() {
        int id = ((ThreadId) Thread.currentThread()).getThreadId();  // Get the thread's ID
        int node = id + numThreads - 1;  // Start at the leaf lock (thread's unique node)
        int level = 0;

        // Traverse from the leaf to the root acquiring locks
        while (node > 0) {
            int parent = (node - 1) / 2;  // Get the parent node index
            tree[parent].lock();  // Acquire the Peterson lock at the parent node
            path[level++] = parent;  // Store the path for the unlock phase
            node = parent;  // Move to the parent node
        }
    }

    @Override
    public void unlock() {
        int level = path.length - 1;  // Start from the topmost acquired lock

        // Traverse from the root back to the leaf releasing locks
        while (level >= 0) {
            int node = path[level--];
            tree[node].unlock();  // Release the Peterson lock at the current node
}
}
}