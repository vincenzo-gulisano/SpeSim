package operator;

import java.util.ArrayDeque;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class OperatorBase implements Operator{

  private final String id;
  private final long cost;
  private final double selectivity;
  private final int inputQueueCapacity;
  private Operator upstream;
  private Operator downstream;
  protected final ArrayDeque<Long> input;

  public OperatorBase(String id, long cost, double selectivity, int inputQueueCapacity) {
    this.id = id;
    this.cost = cost;
    this.selectivity = selectivity;
    this.input = new ArrayDeque<>(inputQueueCapacity);
    this.inputQueueCapacity = inputQueueCapacity;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public long cost() {
    return cost;
  }

  @Override
  public double selectivity() {
    return selectivity;
  }

  @Override
  public void addTuple(long tupleTimestamp) {
    Validate.isTrue(input.offer(tupleTimestamp),
        "Tried to add to full queue, this should never happen!");
  }

  @Override
  public void setUpstream(Operator upstream) {
    Validate.notNull(upstream);
    this.upstream = upstream;
  }

  @Override
  public Operator upstream() {
    return upstream;
  }

  @Override
  public void setDownstream(Operator downstream) {
    Validate.notNull(downstream);
    this.downstream = downstream;
  }

  @Override
  public Operator downstream() {
    return downstream;
  }

  @Override
  public boolean hasInputSpace() {
    return input.size() < inputQueueCapacity;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("cost", cost)
        .append("selectivity", selectivity)
        .append("inputCapacity", inputQueueCapacity)
        .append("upstream", upstream)
        .append("downstream", downstream)
        .append("input", input)
        .toString();
  }
}
