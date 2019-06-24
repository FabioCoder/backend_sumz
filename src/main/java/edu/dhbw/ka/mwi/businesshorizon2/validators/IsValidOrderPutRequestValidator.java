/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.dhbw.ka.mwi.businesshorizon2.validators;

import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.MultiPeriodAccountingFigureRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioPostRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioPutRequestDto;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author DHBW WWI KA
 */
@Component
public class IsValidOrderPutRequestValidator implements ConstraintValidator<IsValidOrderPutRequest, ScenarioPutRequestDto> {

    @Override
    public boolean isValid(ScenarioPutRequestDto arg0, ConstraintValidatorContext arg1) {
        System.out.println("Validating normal order...");

        List<MultiPeriodAccountingFigureRequestDto> multiPeriodAccountingFigures = arg0.getAllMultiPeriodAccountingFigures();
        multiPeriodAccountingFigures.removeIf(x -> x == null);

        for (MultiPeriodAccountingFigureRequestDto mpafr : multiPeriodAccountingFigures) {

            //Constraint: if orders are provided, both have to be provided
            if ((mpafr.getOrder() != null && mpafr.getSeasonalOrder() == null) || mpafr.getOrder() == null && mpafr.getSeasonalOrder() != null) {
                return false;
            }

            if (mpafr.getOrder() != null) {
                Integer[] order = mpafr.getOrder();
                if (order.length != 3 || order[0] < 0 || order[1] < 0 || order[2] < 0) {
                    return false;
                }
            }

            if (mpafr.getSeasonalOrder() != null) {
                Integer[] seasonalOrder = mpafr.getSeasonalOrder();
                if (seasonalOrder.length != 4 || seasonalOrder[0] < 0 || seasonalOrder[1] < 0 || seasonalOrder[2] < 0 || seasonalOrder[3] < 0) {
                    return false;
                }
            }
        }

        return true;
    }

}
