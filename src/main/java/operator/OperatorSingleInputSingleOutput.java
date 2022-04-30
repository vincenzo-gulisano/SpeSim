package operator;

import java.util.Random;

public class OperatorSingleInputSingleOutput extends OperatorBase {

  private static final long RANDOM_SEED = 0;
  private final Random rnd = new Random(RANDOM_SEED);

  public OperatorSingleInputSingleOutput(String id, long cost, double selectivity, int inputCapacity) {
    super(id, cost, selectivity, inputCapacity);
  }

  public long process(long t, long remainingTime) {
    if (!canRun(remainingTime)) {
      return 0;
    }
    long tupleTimestamp = input.poll();
    if (rnd.nextDouble() <= selectivity()) {
      downstream().addTuple(tupleTimestamp);
    }
    return cost();
  }


  @Override
  public boolean canRun(long remainingTime) {
    final boolean hasInput = !input.isEmpty();
    final boolean canOutput = downstream().hasInputSpace();
    final boolean enoughTimeToProcessOneTuple = remainingTime > cost();
    return hasInput && canOutput && enoughTimeToProcessOneTuple;
  }

}
