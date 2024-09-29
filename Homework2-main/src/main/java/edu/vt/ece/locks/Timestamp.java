package edu.vt.ece.locks;

public interface Timestamp {
    long getValue();
    boolean compare(Timestamp t1);
}