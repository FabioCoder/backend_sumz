/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EvaluationServicePrototype;

import java.util.LinkedList;

/**
 *
 * @author Fabian Wallisch
 */

enum Frequency {
    monthly, quarterly;
}

public class Scenario {
    
    private int id;
    private final LinkedList<Period> periods;
    private Frequency frequency;

    public Scenario(int id, Frequency frequency) {
        this.id = id;
        this.frequency = frequency;
        this.periods = new LinkedList();
    }
    
    public Period getPeriod(int index) throws PeriodNotAvailableException {
        try {
            return getPeriod(periods.getFirst(), index);
        } catch(NullPointerException e) {
            throw new PeriodNotAvailableException();
        }
    }
    
    private Period getPeriod(Period start, int remainingIterations) {
        if(remainingIterations == 0)
            return start;
        else
            return getPeriod(start.getNext(), --remainingIterations);
    }
    
}
