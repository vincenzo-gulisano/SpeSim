package queue;

import java.util.LinkedList;

public class QueueBase implements Queue{

    private final java.util.Queue<Long> q;
    private final long maxSize;

    public QueueBase(long maxSize) {
        this.maxSize = maxSize;
        q = new LinkedList<Long>();
    }

    public QueueBase() {
        this(-1);
    }

    public void add(long tupleTs) {
        q.add(tupleTs);
    }

    public long get() {
        return q.poll();
    }

    public boolean isEmpty() {
        return q.isEmpty();
    }

    public boolean isFull() {
        return maxSize!=-1 && getSize()==maxSize;
    }

    public int getSize() {
        return q.size();
    }
}
