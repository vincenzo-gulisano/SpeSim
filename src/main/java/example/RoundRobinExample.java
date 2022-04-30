package example;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import operator.Operator;
import query.Query;
import query.QuerySimplePipeline;
import scheduler.RoundRobinScheduler;

public class RoundRobinExample {

  private static final long EXECUTION_DURATION = 1_000_000_000;
  private static final long SCHEDULE_STEP = 1_000;
  private static final Function<Operator, Long> COST_RELATIVE_PRIORITIES = operator -> operator.cost();
  private static final Function<Operator, Long> EQUAL_PRIORITIES = operator -> 10L;

  public static void main(String[] args) {
    final Query query = new QuerySimplePipeline(1000);
    query.addSource("SOURCE", 2);
    query.addOperator("OP1", 16, 0.1);
    query.addOperator("OP2", 4, 0.5);
    query.addOperator("OP3", 8, 0.1);
    query.addSink("SINK", 2);
    RoundRobinScheduler scheduler = new RoundRobinScheduler();
    long currentTime = 0;
    while (currentTime < EXECUTION_DURATION) {
      currentTime = scheduler.schedule(currentTime, SCHEDULE_STEP, query,
          createPriorities(query, EQUAL_PRIORITIES));
    }

  }

  private static Map<String, Long> createPriorities(Query query, Function<Operator, Long> priorityFunction) {
    final Map<String, Long> priorities = new HashMap<>();
    for (Operator operator : query.operators()) {
      priorities.put(operator.id(), priorityFunction.apply(operator));
    }
    return priorities;
  }

}
