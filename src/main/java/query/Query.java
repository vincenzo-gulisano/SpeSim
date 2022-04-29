package query;

import operator.Operator;

import java.util.Iterator;

public interface Query {

    public Query addSource(long perTupleProcessingTime);

    public Query addOperator(long perTupleProcessingTime, double selectivity);

    public Query addSink(long perTupleProcessingTime);

    public Iterator<Operator> getOperatorIterator();

    public int getNumberOfOperators();

}
