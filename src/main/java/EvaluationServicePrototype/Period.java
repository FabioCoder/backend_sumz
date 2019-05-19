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

//Todo: make this an interface and distinguish between directPeriod (fcf given by user) and indirectPeriod (calculate fcf)
public class Period {
    
    private final Period prev;
    private final Period next;
    
    private PredictableVariable<Double> depreciation;
    private PredictableVariable<Double> additionalIncome;
    private PredictableVariable<Double> additionalCosts;
    private PredictableVariable<Double> investments;
    private PredictableVariable<Double> disvestments;
    private PredictableVariable<Double> revenue;
    private PredictableVariable<Double> costOfMaterial;
    private PredictableVariable<Double> costOfStaff;
    private PredictableVariable<Double> liabilities;
    private PredictableVariable<Double> flowToEquity;
    
    private PredictableVariable<Double> freeCashFlow;
    
    private Double companyValue;

    public Period(Period prev, Period next, PredictableVariable<Double> depreciation, PredictableVariable<Double> additionalIncome, PredictableVariable<Double> additionalCosts, PredictableVariable<Double> investments, 
            PredictableVariable<Double> disvestments, PredictableVariable<Double> revenue, PredictableVariable<Double> costOfMaterial, PredictableVariable<Double> costOfStaff, PredictableVariable<Double> liabilities, PredictableVariable<Double> flowToEquity) {
        this.prev = prev;
        this.next = next;
        this.depreciation = depreciation;
        this.additionalIncome = additionalIncome;
        this.additionalCosts = additionalCosts;
        this.investments = investments;
        this.disvestments = disvestments;
        this.revenue = revenue;
        this.costOfMaterial = costOfMaterial;
        this.costOfStaff = costOfStaff;
        this.liabilities = liabilities;
        this.flowToEquity = flowToEquity;
        
        this.freeCashFlow = this.calcFreeCashFlow();
    }

    public Period(Period prev, Period next, PredictableVariable<Double> freeCashFlow) {
        this.prev = prev;
        this.next = next;
        this.freeCashFlow = freeCashFlow;
    }
    
    public Period(Period prev, Period next) {
        this.prev = prev;
        this.next = next;
        
        this.freeCashFlow = this.predictFreeCashFlow();
    }

    public Double getCompanyValue() {
        return companyValue;
    }

    public Period getPrev() {
        return prev;
    }

    public Period getNext() {
        return next;
    }
    
    //Todo: outsource this to since this is business logic
    public Double calcCompanyValue() {
        //Todo: include current cashflow in calculation
        return this.prev.getCompanyValue();
    }
    
    //Todo: outsource this since this is business logic
    public PredictableVariable<Double> calcFreeCashFlow() {
        //Todo: calculate current cashflow by considering the other variables 
        return new PredictableVariable(true, 0.0);
    }
    
    //Todo: outsource this since this is business logic
    public PredictableVariable<Double> predictFreeCashFlow() {
        //Todo: predict fcf:  build time series by using as many periods as possible as time series until there is a gap or the period has an already predicted value itself (gap or estimated value should be predicted again as well);
        //Example: 4 periods in the beginning, then period with estimated value; then period with no fcf -> use the 4 periods and estimate this and the other two
        return new PredictableVariable(true, 0.0);
    }

}


