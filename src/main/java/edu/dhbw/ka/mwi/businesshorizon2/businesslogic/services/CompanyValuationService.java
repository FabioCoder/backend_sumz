package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services;

import java.util.ArrayList;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.ICompanyValuationService;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ApvCompanyValuationResultDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.CompanyValueDistributionDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.FcfCompanyValuationResultDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.FteCompanyValuationResultDto;

/**
	 * Class which contains three different methods to calculate the company value:
	 * 
	 * - Adjusted Present Value Method (APV)
	 * - Free Cash Flow Method (FCF)
	 * - Flow to Equity Method (FTE)
	 * 
	 * The last Method calculates the x and y values of the result plot. Where y is the probabilityDensity.
	 */
@Service
public class CompanyValuationService implements ICompanyValuationService {
	
	/**
	 * The APV Method assumes that the company has no debts, since the capitalstructure has no impact on the Company Value.
	 * Only taxes have influence.
	 * The Free Cash Flows will be discounted. Afterwards the present value of the Tax Shields will be calculated and added (CompanyValue).
	 * The Tax Shield is the value of debts which raise the company value. Since debts offer Tax advantages. 
	 * 
	 * 
	 * @param freeCashFlow = Used by the Company to pay the dividend or buy shares back
	 * @param liabilities = obligation of the debtor to the creditor 
	 * @param equityInterest = equity a single person holds in a business
	 * @param interestOnLiabilities = amount of interest on the debt
	 * @param effectiveTaxRate = average tax rate paid by a corporation. Total Tax Expense / Earnings Before Taxes
	 */

	public ApvCompanyValuationResultDto performApvCompanyValuation(List<Double> freeCashFlow, List<Double> liabilities,
			double equityInterest, double interestOnLiabilities, double effectiveTaxRate) {
		
		double companyValue = 0;
		double presentValueOfCashflows = 0;
		double capitalStructureEffect = 0;
		
		Double duplicateLast = liabilities.get(liabilities.size() - 1); 
		
		List<Double> liabilitiesClone = new ArrayList<>(liabilities);
		liabilitiesClone.add(duplicateLast);
		
// present Values of the Cash Flow list are calculated and added up.
//  Values of the Tax Shield are calculated and added up to calculate the capitalStructureEffect
		for (int i = 0; i < freeCashFlow.size() - 1; i++) {
			presentValueOfCashflows += (freeCashFlow.get(i) / Math.pow((1 + equityInterest), i + 1));

			double taxShield = effectiveTaxRate * interestOnLiabilities * liabilitiesClone.get(i);
			capitalStructureEffect += (taxShield / Math.pow((1 + interestOnLiabilities), i + 1));
		}
// presentValueOfCashflows is added with the last value of the list dicounted
		presentValueOfCashflows += (freeCashFlow.get(freeCashFlow.size() - 1)
				/ (equityInterest * Math.pow((1 + equityInterest), freeCashFlow.size() - 1)));

		double taxShield = effectiveTaxRate * interestOnLiabilities * liabilitiesClone.get(freeCashFlow.size() - 1);
		capitalStructureEffect += (taxShield
				/ (interestOnLiabilities * Math.pow((1 + interestOnLiabilities), freeCashFlow.size() - 1)));

		//The Free Cash Flows and the present value of the Tax Shields are added up, liabilities are subtracted. 
		companyValue = presentValueOfCashflows + capitalStructureEffect - liabilitiesClone.get(0);

		// returns results: Company Value, Balance Sheet Total, total liabilities, discounted Cashflow, discounted taxShield
		return new ApvCompanyValuationResultDto(companyValue, presentValueOfCashflows + capitalStructureEffect,
				liabilitiesClone.get(0), presentValueOfCashflows, capitalStructureEffect);

	}

	/**
	 * 
	 */
	public FcfCompanyValuationResultDto performFcfCompanyValuationResult(List<Double> freeCashFlow, List<Double> liabilities,
			 double equityInterest, double interestOnLiabilities, double effectiveTaxRate) {
		
		
		
		Double duplicateLast = liabilities.get(liabilities.size() - 1); 
		
		List<Double> liabilitiesClone = new ArrayList<>(liabilities);
		liabilitiesClone.add(duplicateLast);
		
		double companyValue = 0;
		double marketValueTotalAssets = 0;
		double[] capitalStructureEffect = new double[liabilitiesClone.size()];

		for (int e = 0; e < liabilitiesClone.size() - 1; e++) {
			double capitalStructureEffectForPeriod_E = 0;

			int count = 0;

			for (int i = e; i < freeCashFlow.size() - 1; i++) {
				
// calculates the taxShield from Period i and discountes it
				double taxShield = effectiveTaxRate * interestOnLiabilities * liabilitiesClone.get(i);
				capitalStructureEffectForPeriod_E += (taxShield / Math.pow((1 + interestOnLiabilities), count + 1));
				count++;
			}
// calculates the discounted taxShields per period and saves them in an array
			
			double taxShield = effectiveTaxRate * interestOnLiabilities * liabilitiesClone.get(freeCashFlow.size()- 1);
			capitalStructureEffectForPeriod_E += (taxShield
					/ (interestOnLiabilities * Math.pow((1 + interestOnLiabilities), count)));
			capitalStructureEffect[e] = capitalStructureEffectForPeriod_E;
		}

		double taxShield = effectiveTaxRate * interestOnLiabilities * liabilitiesClone.get(liabilitiesClone.size()- 1);
		capitalStructureEffect[capitalStructureEffect.length - 1] = (taxShield / (interestOnLiabilities)
				* (1 + interestOnLiabilities));

// adds an Array with the free Cash Flows with an additional value 0 on index 0
		double[] cashflowsWithPeriodZero = new double[freeCashFlow.size() + 1];
		cashflowsWithPeriodZero[0] = 0;
		for (int i = 0; i < freeCashFlow.size(); i++) {
			cashflowsWithPeriodZero[i + 1] = freeCashFlow.get(i);
		}
// cash flow of the last Period + "TaxShield for the Companies Equity?" = Companies worth according to investors
		double totalCapitalMarketValueOfLastPeriod = (cashflowsWithPeriodZero[cashflowsWithPeriodZero.length - 1]
				+ liabilitiesClone.get(liabilitiesClone.size()- 1) * effectiveTaxRate * equityInterest) / equityInterest;

		double totalCapitalMarketValueOfPeriod_i = totalCapitalMarketValueOfLastPeriod;

		for (int i = liabilitiesClone.size() - 2; i > 0; i--) {
			
//calculation of the companyvalue 
			double ValueEquityOfPeriod_iMinus1 = (totalCapitalMarketValueOfPeriod_i + cashflowsWithPeriodZero[i]
					- liabilitiesClone.get(i - 1)
					- (equityInterest - interestOnLiabilities) * (liabilitiesClone.get(i - 1) - capitalStructureEffect[i - 1])
					- interestOnLiabilities * (1 - effectiveTaxRate) * liabilitiesClone.get(i - 1)) / (1 + equityInterest);
			
// the totalCapitalMarketValue ist the balance sheet total
// the companyValue is the balance sheet in total minus the payables
			totalCapitalMarketValueOfPeriod_i = ValueEquityOfPeriod_iMinus1 + liabilitiesClone.get(i - 1);
			companyValue = ValueEquityOfPeriod_iMinus1;
			marketValueTotalAssets = totalCapitalMarketValueOfPeriod_i;
		}
		
		
		return new FcfCompanyValuationResultDto(companyValue, marketValueTotalAssets, liabilities.get(0));
	}
/**
 * Free cash flow to equity is a measure of how much cash is available to the equity shareholders of a company 
 * after all expenses, reinvestment, and debt are paid. 
 * 
 * @param flowToEquity = Cash Flow the shareholder will get
 * @param liabilities = obligation of the debtor to the creditor 
 * @param equityInterest = equity a single person holds in a business
 * @param interestOnLiabilities = amount of interest on the debt
 * @param effectiveTaxRate = average tax rate paid by a corporation. Total Tax Expense / Earnings Before Taxes
 */
	public FteCompanyValuationResultDto performFteCompanyValuationResult(List<Double> flowToEquity, List<Double> liabilities,
			double equityInterest, double interestOnLiabilities, double effectiveTaxRate) {
		
		Double duplicateLast = liabilities.get(liabilities.size() - 1); 
		
		List<Double> liabilitiesClone = new ArrayList<>(liabilities);
		liabilitiesClone.add(duplicateLast);
		
		double companyValue = 0;
		double[] equity = new double[liabilitiesClone.size()];
		double[] adjustedEquityInterest = new double[flowToEquity.size()];
		double[] marketValueEquity = new double[liabilitiesClone.size()];
		double[] discountedTaxShields = new double[liabilitiesClone.size()];

		// calculate discountedTaxShields
		for (int i = 0; i < discountedTaxShields.length; i++) {
			double taxShield = 0;
			double discountedTaxShield = 0;

			for (int j = i; j < discountedTaxShields.length - 1; j++) {
				taxShield = effectiveTaxRate * interestOnLiabilities * liabilitiesClone.get(j);
				discountedTaxShield += (taxShield / Math.pow((1 + interestOnLiabilities), j + 1 - i));
			}

			discountedTaxShield += (effectiveTaxRate * liabilitiesClone.get(liabilitiesClone.size()- 1))
					/ Math.pow((1 + interestOnLiabilities), liabilitiesClone.size() - 1 - i);
			discountedTaxShields[i] = discountedTaxShield;
			// System.out.println(discountedTaxShield);
		}

		// calculate marketValuesEquity
		marketValueEquity[marketValueEquity.length - 2] = (flowToEquity.get(flowToEquity.size() - 1)
				- (equityInterest - interestOnLiabilities) * (1 - effectiveTaxRate) * liabilitiesClone.get(liabilitiesClone.size() - 2))
				/ equityInterest;
		marketValueEquity[marketValueEquity.length - 1] = marketValueEquity[marketValueEquity.length - 2];

		for (int i = marketValueEquity.length - 3; i >= 0; i--) {
			marketValueEquity[i] = (marketValueEquity[i + 1] + flowToEquity.get(i)
					- (equityInterest - interestOnLiabilities) * ((liabilitiesClone.get(i)) - discountedTaxShields[i]))
					/ (1 + equityInterest);
		}

		return new FteCompanyValuationResultDto(marketValueEquity[0]);

	}
	/**
	 * Defining the x and y value for the companyvalues which are shown in the scenario results as plot
	 */
	
	public CompanyValueDistributionDto getCompanyValueDistribution(List<Double> companyValues) {
		
		System.out.println("Company Values: ");
		for (int i = 0; i < companyValues.size(); i++) {
			System.out.println(companyValues.get(i) + ",");
		}
		
		double mean = companyValues.stream().mapToDouble(x -> x).average().getAsDouble();
		System.out.println("Mean: " + mean);
		
		Double variance = companyValues.stream().mapToDouble(x -> Math.pow((x - mean), 2)).sum() / companyValues.size();
		
		System.out.println("Variance: " + variance);
		
		Double standardDeviation = 1E-10;
		if(variance >= 1E-10) {
			standardDeviation = Math.sqrt(variance);
		}
		
		System.out.println("Standard Deviation: " + standardDeviation);
		
		double xInterval = standardDeviation / 5;
		
		List<Double> xValues = new ArrayList<Double>();
		List<Double> yValues = new ArrayList<Double>();
		
		double currentXValue = mean - (3 * standardDeviation);
		
		for (int i = 0; i < 31; i++) {
			
			// formular for the normal distribution used for the scenario results
			Double probabilityDensity = (1 / Math.sqrt(2 * Math.PI * Math.pow(standardDeviation, 2))) 
					* Math.exp(- Math.pow(currentXValue - mean, 2)/(2 * Math.pow(standardDeviation, 2)));
			
			xValues.add(currentXValue);
			yValues.add(probabilityDensity);		
			
			currentXValue += xInterval;
		}
		
		CompanyValueDistributionDto distribution = new CompanyValueDistributionDto(xValues, yValues);
		
		return distribution;
	}
}
