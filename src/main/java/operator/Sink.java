package operator;

import util.AverageStatistic;

public class Sink extends OperatorBase {

  private final AverageStatistic latencyLogger;

  public Sink(String id, long cost, int inputQueueCapacity) {
    super(id, cost, 1, inputQueueCapacity);
    this.latencyLogger = new AverageStatistic(id + " latency", 1);
  }

  public long process(long t, long remainingTime) {
    if (!canRun(remainingTime)) {
      return 0;
    }
    latencyLogger.report(t - input.poll());
    return cost();
  }

  @Override
  public Operator downstream() {
    return null;
  }

  @Override
  public boolean canRun(long remainingTime) {
    final boolean hasInput = !input.isEmpty();
    final boolean enoughTimeToProcessOneTuple = remainingTime > cost();
    return hasInput && enoughTimeToProcessOneTuple;
  }

}
