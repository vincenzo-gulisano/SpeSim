package queue;

public interface Queue {

    public void add(long TupleTs);

    public long get();

    public boolean isEmpty();

    public boolean isFull();

    public int getSize();

}
