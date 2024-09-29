package edu.vt.ece.locks;

public interface TimestampSystem {
    public Timestamp[] scan();
    public void label(Timestamp timestamp, int i);
}
