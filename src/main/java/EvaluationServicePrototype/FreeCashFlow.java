/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EvaluationServicePrototype;

/**
 *
 * @author Fabian Wallisch
 */
public class FreeCashFlow extends PredictableVariable<Double> {

    public FreeCashFlow(boolean predicted, Double value, FreeCashFlow prev, FreeCashFlow next) {
        super(predicted, value, prev, next);
    }
    
    
    
}
