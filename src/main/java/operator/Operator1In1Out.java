package operator;

import queue.Queue;
import scheduler.StepResult;

import java.util.Random;

public class Operator1In1Out extends OperatorBase {

    private final double selectivity;
    private final long perTupleProcessingTime;
    private final Random rnd;

    public Operator1In1Out(Queue in, Queue out, long perTupleProcessingTime, double selectivity) {
        super(in, out);
        this.selectivity = selectivity;
        this.perTupleProcessingTime = perTupleProcessingTime;
        this.rnd = new Random();
    }

    public StepResult process(long ts, long timeLeft) {
        if (in.isEmpty()) {
            return new StepResult(0,true);
        }
        if (timeLeft<perTupleProcessingTime) {
            return new StepResult(0,false);
        }
        long tupleTS = in.get();
        if (rnd.nextDouble()<=selectivity) {
            out.add(tupleTS);
        }
        return new StepResult(perTupleProcessingTime,!out.isFull());
    }
}
