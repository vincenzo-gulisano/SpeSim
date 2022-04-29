package operator;

import queue.Queue;

public abstract class OperatorBase implements Operator {

    protected final Queue in;
    protected final Queue out;

    protected OperatorBase(Queue in, Queue out) {
        this.in = in;
        this.out = out;
    }


}
