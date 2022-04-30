package query;

import java.util.Collection;
import operator.Operator;

public interface Query {

    Query addSource(String id, long cost);

    Query addOperator(String id, long cost, double selectivity);

    Query addSink(String id, long cost);

    Collection<Operator> operators();
}
