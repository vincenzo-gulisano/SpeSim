package operator;

public interface Operator {

    String id();

    long cost();

    double selectivity();

    long process(long t, long remainingTime);
    Operator upstream();

    void setUpstream(Operator upstream);

    Operator downstream();

    void setDownstream(Operator downstream);

    boolean hasInputSpace();

    void addTuple(long tupleTimestamp);

    boolean canRun(long remainingTime);
}
