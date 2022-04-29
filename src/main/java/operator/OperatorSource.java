package operator;

import queue.Queue;
import scheduler.StepResult;

public class OperatorSource extends OperatorBase {

    private final long perTupleProcessingTime;

    public OperatorSource(Queue out, long perTupleProcessingTime) {
        super(null, out);
        this.perTupleProcessingTime = perTupleProcessingTime;
    }

    public StepResult process(long ts, long timeLeft) {
        if (out.isFull()) {
            return new StepResult(0,false);
        }
        out.add(ts);
        return new StepResult(0,true);
    }
}
