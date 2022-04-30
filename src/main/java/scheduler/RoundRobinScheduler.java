package scheduler;

import java.util.Map;
import operator.Operator;
import query.Query;

public class RoundRobinScheduler {

  public long schedule(long currentTime, long stepDuration, Query query,
      Map<String, Long> priorities) {
    final Schedule schedule = new Schedule(priorities, stepDuration);
    boolean oneExecuted = true;
    while (oneExecuted && schedule.totalAvailableTime() > 0) {
      oneExecuted = false;
      for (Operator operator : query.operators()) {
        final long operatorExecutionDuration = operator.process(currentTime,
            schedule.availableExecutionTime(operator.id()));
        schedule.onExecuted(operator.id(), operatorExecutionDuration);
        currentTime += operatorExecutionDuration;
        oneExecuted = oneExecuted || (operatorExecutionDuration > 0);
//        System.out.println("Executed " + operator.id() + " for " + operatorExecutionDuration);
      }
    }
//    schedule.printStatistics();
//    System.out.println("Current time: " + currentTime);
    return currentTime;
  }

}
