package scheduler;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Validate;

public class Schedule {

  private final Map<String, Long> availableTimes = new HashMap<>();
  private long availableTime;
  private final Map<String, Integer> executionCounts = new HashMap<>();


  public Schedule(Map<String, Long> priorities, long stepDuration) {
    final long prioritySum = priorities.values().stream().mapToLong(Long::valueOf).sum();
    for (String operatorId : priorities.keySet()) {
      final double relativePriority = priorities.get(operatorId) / (double) prioritySum;
      final long executionTime = Math.round(stepDuration * relativePriority);
      Validate.isTrue(executionTime > 0, "Zero execution time for %s. Try increasing stepDuration!", operatorId);
      availableTimes.put(operatorId, executionTime);
      executionCounts.put(operatorId, 0);
      availableTime += executionTime;
    }
  }

  public long totalAvailableTime() {
    return availableTime;
  }

  public long availableExecutionTime(String operatorId) {
    return availableTimes.get(operatorId);
  }

  public void onExecuted(String operatorId, long duration) {
    if (duration == 0) {
      return;
    }
    availableTimes.compute(operatorId, (operator, time) -> time - duration);
    availableTime -= duration;
    executionCounts.compute(operatorId, (operator, count) -> count + 1);
  }

  public void printStatistics() {
    System.out.format("Available time: %d\n", availableTime);
    System.out.println(executionCounts);
  }

}
