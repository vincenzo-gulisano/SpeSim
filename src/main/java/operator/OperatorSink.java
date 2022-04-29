package operator;

import queue.Queue;
import scheduler.StepResult;

public class OperatorSink extends OperatorBase {

    private final long perTupleProcessingTime;

    public OperatorSink(Queue in, Queue out, long perTupleProcessingTime) {
        super(in, out);
        this.perTupleProcessingTime = perTupleProcessingTime;
    }

    public StepResult process(long ts, long timeLeft) {
        if (!in.isEmpty()) {
            in.get();
        }
        if (timeLeft<perTupleProcessingTime) {
            return new StepResult(0,false);
        }
        return new StepResult(perTupleProcessingTime, true);
    }

}
