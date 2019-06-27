package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.DoubleKeyValueListDto;

@Service
public interface IAccountingFigureCalculationsService {

    public double calculateFreeCashFlow(double revenue, double additionalIncome, double costOfMaterial,
            double costOfStaff, double additionalCosts, double depreciation, double businessTaxRate,
            double corporateTaxRate, double solidaryTaxRate, double investments, double divestments);

    public DoubleKeyValueListDto calculateFreeCashFlow(DoubleKeyValueListDto revenue, DoubleKeyValueListDto additionalIncome, DoubleKeyValueListDto costOfMaterial,
            DoubleKeyValueListDto costOfStaff, DoubleKeyValueListDto additionalCosts, DoubleKeyValueListDto depreciation, Double businessTaxRate,
            Double corporateTaxRate, Double solidaryTaxRate, DoubleKeyValueListDto investments, DoubleKeyValueListDto divestments);

    public List<Double> calculateFlowToEquity(
            List<Double> freeCashFlow,
            List<Double> liabilities,
            Double interestOnLiabilities,
            Double effectiveTaxRate);

    public double calculateEffectiveTaxRate(double businessTaxRate, double corporateTaxRate, double solidaryTaxRate);

    public List<Double> getMeanAccountingFigureValues(
            HashMap<MultiPeriodAccountingFigureNames, HashMap<Integer, List<Double>>> stochasticAccountingFigures,
            MultiPeriodAccountingFigureNames figureName,
            int periods);
}
