package operator;

import scheduler.StepResult;

public interface Operator {

    public StepResult process(long ts, long timeLeft);

}
