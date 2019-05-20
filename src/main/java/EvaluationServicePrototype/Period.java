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
    
    private FCFProperty depreciation;
    private FCFProperty additionalIncome;
    private FCFProperty additionalCosts;
    private FCFProperty investments;
    private FCFProperty disvestments;
    private FCFProperty revenue;
    private FCFProperty costOfMaterial;
    private FCFProperty costOfStaff;
    private FCFProperty liabilities;
    private FCFProperty flowToEquity;
    
    private FreeCashFlow freeCashFlow;
    
    private Double companyValue;

    public Period(Period prev, Period next, FCFProperty depreciation, FCFProperty additionalIncome, FCFProperty additionalCosts, FCFProperty investments, 
            FCFProperty disvestments, FCFProperty revenue, FCFProperty costOfMaterial, FCFProperty costOfStaff, FCFProperty liabilities, FCFProperty flowToEquity) {
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
    }

    public Period(Period prev, Period next, FreeCashFlow freeCashFlow) {
        this.prev = prev;
        this.next = next;
        this.freeCashFlow = freeCashFlow;
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

}


