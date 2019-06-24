package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IAccountingFigureCalculationsService;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IStandardErrorCalculationService;
import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.DoubleKeyValueListDto;
import java.util.AbstractMap.SimpleEntry;

//this class provides methods to calculate necessarry paramters for the company valuation, e.g. free cash flow or flow to equity
@Service
public class AccountingFigureCalculationsService implements IAccountingFigureCalculationsService {

    //this methods calculates the free cash flow by using input variables
    //why do we need a method with a single return and a method for a list return?
    IStandardErrorCalculationService secs;
    
    public AccountingFigureCalculationsService() {
        this.secs = new StandardErrorCalculationService();
    }

    /**
     * @param revenue
     * @param additionalIncome
     * @param costOfMaterial
     * @param costOfStaff
     * @param additionalCosts
     * @param depreciation
     * @param businessTaxRate
     * @param corporateTaxRate
     * @param solidaryTaxRate
     * @param investments
     * @param divestments
     * @return
     */
    public double calculateFreeCashFlow(double revenue, double additionalIncome, double costOfMaterial,
            double costOfStaff, double additionalCosts, double depreciation, double businessTaxRate,
            double corporateTaxRate, double solidaryTaxRate, double investments, double divestments) {

        double proceeds = revenue + additionalIncome;
        double payments = costOfMaterial + costOfStaff + additionalCosts;

        double cashFlow = proceeds - payments;

        double absoluteTaxes = (cashFlow - depreciation) * (businessTaxRate + (corporateTaxRate * (1 + solidaryTaxRate)));
        double operatingCashFlow = cashFlow - absoluteTaxes;

        double freeCashFlow = operatingCashFlow - (investments - divestments);

        return freeCashFlow;
    }

    @Override
    public DoubleKeyValueListDto calculateFreeCashFlow(DoubleKeyValueListDto revenue, DoubleKeyValueListDto additionalIncome, DoubleKeyValueListDto costOfMaterial,
            DoubleKeyValueListDto costOfStaff, DoubleKeyValueListDto additionalCosts, DoubleKeyValueListDto depreciation, Double businessTaxRate,
            Double corporateTaxRate, Double solidaryTaxRate, DoubleKeyValueListDto investments, DoubleKeyValueListDto divestments) {

        DoubleKeyValueListDto freeCashFlow = new DoubleKeyValueListDto();

        for (int i = 0; i < revenue.size(); i++) {
            
             //TODO: calc std error of fcf

            double proceeds = revenue.getKeyList().get(i) + additionalIncome.getKeyList().get(i);
            double proceedsSE = secs.addition(revenue.getValueList().get(i), additionalIncome.getValueList().get(i));

            double payments = costOfMaterial.getKeyList().get(i) + costOfStaff.getKeyList().get(i) + additionalCosts.getKeyList().get(i);
            double paymentsSE = secs.addition(costOfMaterial.getValueList().get(i), secs.addition(costOfStaff.getValueList().get(i) , additionalCosts.getValueList().get(i)));

            double cashFlow = proceeds - payments;
            double cashFlowSE = secs.subtraction(proceedsSE, paymentsSE);

            double absoluteTaxes = (cashFlow - depreciation.getKeyList().get(i)) * (businessTaxRate + (corporateTaxRate * (1 + solidaryTaxRate)));
            double absoluteTaxesSE = secs.subtraction(cashFlowSE, depreciation.getValueList().get(i)) * (businessTaxRate + (corporateTaxRate * (1 + solidaryTaxRate)));

            double operatingCashFlow = cashFlow - absoluteTaxes;
            double operatingCashFlowSE = secs.subtraction(cashFlowSE, absoluteTaxesSE);

            freeCashFlow.add(new SimpleEntry<>(operatingCashFlow - (investments.getKeyList().get(i) - divestments.getKeyList().get(i)), secs.subtraction(operatingCashFlowSE, secs.subtraction(investments.getValueList().get(i), divestments.getValueList().get(i)))));
            
        }

        return freeCashFlow;
    }

    //this method calculates the flow to equity
    /**
     * @param freeCashFlow
     * @param liabilities
     * @param previousLiabilities
     * @param interestOnLiabilities
     * @param effectiveTaxRate
     * @return
     */
    public double calculateFlowToEquity(double freeCashFlow, double liabilities, double previousLiabilities, double interestOnLiabilities,
            double effectiveTaxRate) {

        double taxShield = effectiveTaxRate * interestOnLiabilities * previousLiabilities;
        double totalCashFlow = freeCashFlow + taxShield;
        double flowToEquity = totalCashFlow - (interestOnLiabilities * previousLiabilities) + (liabilities - previousLiabilities);

        return flowToEquity;
    }

    public List<Double> calculateFlowToEquity(List<Double> freeCashFlow, List<Double> liabilities, Double interestOnLiabilities, Double effectiveTaxRate) {
        Double duplicate = liabilities.get(liabilities.size() - 1);

        List<Double> liabilitiesClone = new ArrayList<>(liabilities);
        liabilitiesClone.add(duplicate);

        List<Double> flowToEquity = new ArrayList<Double>();

        for (int i = 0; i < freeCashFlow.size(); i++) {
            flowToEquity.add(calculateFlowToEquity(freeCashFlow.get(i), liabilitiesClone.get(i + 1), liabilitiesClone.get(i), interestOnLiabilities, effectiveTaxRate));
        }

        return flowToEquity;
    }

    //this methods calculates the effective tax rate
    /**
     * @param businessTaxRate
     * @param corporateTaxRate
     * @param solidaryTaxRate
     */
    public double calculateEffectiveTaxRate(double businessTaxRate, double corporateTaxRate, double solidaryTaxRate) {
        double effectiveTaxRate = 0.75 * businessTaxRate + corporateTaxRate * (1 + solidaryTaxRate);

        return effectiveTaxRate;
    }

    public List<Double> getMeanAccountingFigureValues(HashMap<MultiPeriodAccountingFigureNames, HashMap<Integer, List<Double>>> stochasticAccountingFigures, MultiPeriodAccountingFigureNames figureName, int periods) {

        List<Double> meanAccountingFigureValues = new ArrayList<Double>();

        for (int i = 0; i < periods; i++) {
            final int j = i;

            if (stochasticAccountingFigures.get(figureName) == null) {
                System.out.println("Figure: " + figureName + " is null.");
            }

            Double meanAccountingFigureValue = stochasticAccountingFigures.get(figureName)
                    .values()
                    .stream()
                    .map(x -> x.get(j))
                    .mapToDouble(x -> x)
                    .average()
                    .getAsDouble();

            meanAccountingFigureValues.add(meanAccountingFigureValue);
        }

        return meanAccountingFigureValues;
    }
}
