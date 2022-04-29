package scheduler;

import operator.Operator;
import query.Query;

import java.util.Iterator;
import java.util.TreeMap;

public class SimpleScheduler {

    private final Query query;
    private long currentTs;
    private final long executionDuration;
    private final long stepDuration;

    public SimpleScheduler(Query query, long executionDuration, long stepDuration) {
        this.query = query;
        this.currentTs = 0;
        this.executionDuration = executionDuration;
        this.stepDuration = stepDuration;
    }

    public void schedule() {

        // before starting scheduling operators, create structure to keep track of which can be run
        TreeMap<Integer,Boolean> canContinueMap = new TreeMap<Integer, Boolean>();

        int i = 0;
        for (Iterator<Operator> it = query.getOperatorIterator(); it.hasNext(); ) {
            it.next();
            canContinueMap.put(i,true);
            i++;
        }

        long stepTimeLeft = stepDuration;

        while (currentTs <= executionDuration) {


            // Check if we can continue
            boolean canContinue = true;
            for(Boolean b : canContinueMap.values()) {
                canContinue&=b;
            }

            if (!canContinue) {
                currentTs+=stepTimeLeft;
            } else {
                i = 0;
                for (Iterator<Operator> it = query.getOperatorIterator(); it.hasNext(); ) {
                    Operator op = it.next();
                    if (canContinueMap.get(i)) {
                        StepResult r = op.process(currentTs,stepTimeLeft);
                        stepTimeLeft -= r.getConsumedTime();
                        canContinueMap.put(i,r.canContinue());
                    }
                    i++;
                }
            }



        }

        for (Iterator<Operator> it = query.getOperatorIterator(); it.hasNext(); ) {
            Operator op = it.next();


        }
    }

}
