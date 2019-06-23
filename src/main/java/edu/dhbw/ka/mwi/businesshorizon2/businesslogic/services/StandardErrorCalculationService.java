/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services;

import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IStandardErrorCalculationService;

/**
 *
 * @author DHBW KA WWI
 */
public class StandardErrorCalculationService implements IStandardErrorCalculationService {

    @Override
    public Double addition(Double a, Double b) {
        return a+b;
    }

    @Override
    public Double subtraction(Double a, Double b) {
        return a-b;
    }

    @Override
    public Double multiplication(Double a, Double b) {
        //TODO: INSERT MATH
        return a*b;
    }

    @Override
    public Double division(Double a, Double b) {
        //TODO: INSERT MATH
        return a/b;
    }
    
}
