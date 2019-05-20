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

    public PredictableVariable(boolean predicted, T value) {
        this.predicted = predicted;
        this.value = value;
    }

}
