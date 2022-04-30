package operator;

import util.CountStatistic;

public class Source extends OperatorBase {

  private final CountStatistic throughputLogger;

  public Source(String id, long cost) {
    super(id, cost, 1, 0);
    this.throughputLogger = new CountStatistic(id + " rate", 1);
  }

  public long process(long currentTime, long remainingTime) {
    if (!canRun(remainingTime)) {
      return 0;
    }
    downstream().addTuple(currentTime);
    throughputLogger.report(1);
    return cost();
  }

  @Override
  public Operator upstream() {
    return null;
  }

  @Override
  public void addTuple(long tupleTimestamp) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean canRun(long remainingTime) {
    final boolean canOutput = downstream().hasInputSpace();
    final boolean enoughTimeToProcessOneTuple = remainingTime > cost();
    return canOutput && enoughTimeToProcessOneTuple;
  }
}
