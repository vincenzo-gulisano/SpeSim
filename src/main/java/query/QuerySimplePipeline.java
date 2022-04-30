package query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import operator.Operator;
import operator.OperatorSingleInputSingleOutput;
import operator.Sink;
import operator.Source;
import org.apache.commons.lang3.Validate;
import java.util.List;

public class QuerySimplePipeline implements Query {

  private final List<Operator> operators = new ArrayList<>();
  private final int operatorQueueCapacity;

  public QuerySimplePipeline(int operatorQueueCapacity) {
    this.operatorQueueCapacity = operatorQueueCapacity;
  }

  public Query addSource(String id, long cost) {
    Validate.isTrue(operators.isEmpty(), "Add source before operators!");
    operators.add(new Source(id, cost));
    return this;
  }

  public Query addOperator(String id, long cost, double selectivity) {
    Validate.isTrue(!operators.isEmpty(), "Add source before operators!");
    int operatorIndex = operators.size();
    operators.add(
        new OperatorSingleInputSingleOutput(id, cost, selectivity, operatorQueueCapacity));
    connectLastPair();
    return this;
  }

  public Query addSink(String id, long cost) {
    Validate.isTrue(!operators.isEmpty(), "Add source and operators before sink!");
    operators.add(new Sink(id, cost, operatorQueueCapacity));
    connectLastPair();
    return this;
  }

  private void connectLastPair() {
    final Operator last = operators.get(operators.size() - 1);
    final Operator beforeLast = operators.get(operators.size() - 2);
    beforeLast.setDownstream(last);
    last.setUpstream(beforeLast);
  }

  @Override
  public Collection<Operator> operators() {
    return Collections.unmodifiableCollection(operators);
  }

}
