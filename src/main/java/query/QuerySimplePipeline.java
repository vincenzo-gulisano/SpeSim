package query;

import operator.Operator;
import operator.Operator1In1Out;
import operator.OperatorSink;
import operator.OperatorSource;
import queue.Queue;
import queue.QueueBase;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class QuerySimplePipeline implements Query{
    
    private final List<Operator> ops;
    private final List<Queue> qs;
    private final long maxQueueSize;

    public QuerySimplePipeline(long maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
        ops = new LinkedList<Operator>();
        qs = new LinkedList<Queue>();
    }

    public QuerySimplePipeline() {
        this(-1);
    }

    public Query addSource(long perTupleProcessingTime) {
        assert(ops.isEmpty());
        qs.add(new QueueBase(maxQueueSize));
        ops.add(new OperatorSource(qs.get(0), perTupleProcessingTime));
        return this;
    }

    public Query addOperator(long perTupleProcessingTime, double selectivity) {
        assert(!ops.isEmpty());
        qs.add(new QueueBase(maxQueueSize));
        ops.add(new Operator1In1Out(qs.get(qs.size()-2),qs.get(qs.size()-1),perTupleProcessingTime,selectivity));
        return this;
    }

    public Query addSink(long perTupleProcessingTime) {
        assert(!ops.isEmpty());
        ops.add(new OperatorSink(qs.get(qs.size()-1),null,perTupleProcessingTime));
        return this;
    }

    public Iterator<Operator> getOperatorIterator() {
        return ops.iterator();
    }

    public int getNumberOfOperators() {
        return ops.size();
    }
}
