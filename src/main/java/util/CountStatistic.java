package util;

public class CountStatistic {

  private long sum;
  private long lastReportSecond;
  private final String name;
  private final long reportFrequencySeconds;

  public CountStatistic(String name, long reportFrequencySeconds) {
    this.name = name;
    this.reportFrequencySeconds = reportFrequencySeconds;
  }

  public void report(long value) {
    sum += value;
    final long currentSecond = System.currentTimeMillis() / 1000;
    if (currentSecond - reportFrequencySeconds > lastReportSecond) {
      lastReportSecond = currentSecond;
      System.out.format("%s: %d\n", name, sum);
      sum = 0;
    }
  }

}
