package EvaluationServicePrototype;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fabian Wallisch
 */
public class PredictableVariable<T> {

    private boolean predicted;
    private T value;
    private PredictableVariable<T> prev;
    private PredictableVariable<T> next;

    public PredictableVariable(boolean predicted, T value, PredictableVariable<T> prev, PredictableVariable<T> next) {
        this.predicted = predicted;
        this.value = value;
        this.prev = prev;
        this.next = next;
    }

}
