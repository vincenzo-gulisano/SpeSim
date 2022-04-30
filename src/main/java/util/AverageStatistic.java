package util;

public class AverageStatistic {

  private long sum;
  private long count;
  private long lastReportSecond;
  private final String name;
  private final long reportFrequencySeconds;

  public AverageStatistic(String name, long reportFrequencySeconds) {
    this.name = name;
    this.reportFrequencySeconds = reportFrequencySeconds;
  }

  public void report(long value) {
    sum += value;
    count += 1;
    final long currentSecond = System.currentTimeMillis() / 1000;
    if (currentSecond - reportFrequencySeconds > lastReportSecond) {
      lastReportSecond = currentSecond;
      System.out.format("%s: %3.2f\n", name, sum / (double) count);
      sum = 0;
      count = 0;
    }
  }

}
