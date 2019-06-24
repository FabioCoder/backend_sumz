package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services;

import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IStandardErrorCalculationService;

/**
 *
 * @author DHBW KA WWI
 */
public class StandardErrorCalculationService implements IStandardErrorCalculationService {

    @Override
    public Double addition(Double a, Double b) {
        double aVariance = Math.pow(a, 2);
        double bVariance = Math.pow(b, 2);
        double totalVariance = aVariance + bVariance;
        return Math.sqrt(totalVariance);
    }

    @Override
    public Double subtraction(Double a, Double b) {
        double aVariance = Math.pow(a, 2);
        double bVariance = Math.pow(b, 2);
        double totalVariance = aVariance - bVariance;
        return Math.sqrt(totalVariance);
    }

    @Override
    public Double multiplication(Double varX, Double varY, Double x, Double y) {
        //Attention: this refers to addition for two standard errors of random variables, not a constant! (this would just be the simple product)
        //For maths, see: https://en.wikipedia.org/wiki/Variance
        return Math.pow(x, 2) * varY + Math.pow(y, 2) * varX + varX * varY;
    }

}
