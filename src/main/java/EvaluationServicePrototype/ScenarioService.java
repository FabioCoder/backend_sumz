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
public class ScenarioService {
    
    public void handleScenarioRequest() {
        //Todo: interpret Json and create corresponding objects
        
        Scenario scenario = new Scenario(0, Frequency.quarterly);
        
    }
    
    public Double calcCompanyValue(Scenario scenario) {
        //Todo: include current cashflow in calculation
        return scenario.getPeriod(0).getPrev().getCompanyValue();
    }
    
    public FreeCashFlow calcFreeCashFlow() {
        //Todo: calculate current cashflow by considering the other variables 
        return new FreeCashFlow(false, 0.0);
    }
    
    public FreeCashFlow predictFreeCashFlow() {
        //Todo: predict fcf:  build time series by using as many periods as possible as time series until there is a gap or the period has an already predicted value itself (gap or estimated value should be predicted again as well);
        //Example: 4 periods in the beginning, then period with estimated value; then period with no fcf -> use the 4 periods and estimate this and the other two
        //If frequency is quarterly use Brown Rozeff
        return new FreeCashFlow(true, 0.0);
    }
    
    public FCFProperty predictFCFProperty() {
        //Todo: predict fcf:  build time series by using as many periods as possible as time series until there is a gap or the period has an already predicted value itself (gap or estimated value should be predicted again as well);
        //Example: 4 periods in the beginning, then period with estimated value; then period with no fcf -> use the 4 periods and estimate this and the other two
        return new FCFProperty(true, 0.0);
    }
    
}
