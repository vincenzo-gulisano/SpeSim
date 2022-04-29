package scheduler;

public class StepResult {

    private final long consumedTime;
    private final boolean canContinue;

    public StepResult(long consumedTime, boolean canContinue) {
        this.consumedTime = consumedTime;
        this.canContinue = canContinue;
    }

    public long getConsumedTime() {
        return consumedTime;
    }

    public boolean canContinue() {
        return canContinue;
    }

}
