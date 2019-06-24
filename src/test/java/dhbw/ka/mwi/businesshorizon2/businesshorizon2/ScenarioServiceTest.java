/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw.ka.mwi.businesshorizon2.businesshorizon2;

import edu.dhbw.ka.mwi.businesshorizon2.App;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services.ScenarioService;
import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.MultiPeriodAccountingFigureRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioPostRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioPutRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioResponseDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.TimeSeriesItemDateRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.TimeSeriesItemRequestDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author DHBW KA WWI
 */
//TODO: test with not working request, missing, ...
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ScenarioServiceTest {

    @Autowired
    ScenarioService scenarioService;

    Long appUserId;

    ScenarioPostRequestDto postRequestComb1;
    ScenarioPostRequestDto postRequestComb2;
    ScenarioPutRequestDto putRequestComb1;

    MultiPeriodAccountingFigureRequestDto additionalIncomeMPAFR;
    MultiPeriodAccountingFigureRequestDto depreciationMPAFR;
    MultiPeriodAccountingFigureRequestDto additionalCostsMPAFR;
    MultiPeriodAccountingFigureRequestDto investmentsMPAFR;
    MultiPeriodAccountingFigureRequestDto divestmentsMPAFR;
    MultiPeriodAccountingFigureRequestDto revenueMPAFR;
    MultiPeriodAccountingFigureRequestDto costOfMaterialMPAFR;
    MultiPeriodAccountingFigureRequestDto costOfStaffMPAFR;
    MultiPeriodAccountingFigureRequestDto liabilitiesMPAFR;
    MultiPeriodAccountingFigureRequestDto freeCashFlowsMPAFR;

    @Before
    public void setup() {
        //TODO: change to test user account
        appUserId = new Long(79);

        postRequestComb1 = new ScenarioPostRequestDto();
        postRequestComb2 = new ScenarioPostRequestDto();
        putRequestComb1 = new ScenarioPutRequestDto();

        List<TimeSeriesItemRequestDto> additionalIncome = new ArrayList<TimeSeriesItemRequestDto>();
        additionalIncome.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 1), 50.0));
        additionalIncome.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 2), 60.0));
        additionalIncome.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 3), 60.0));
        additionalIncome.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 4), 60.0));
        additionalIncome.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 1), 60.0));
        additionalIncome.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 2), 60.0));
        additionalIncome.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 3), 60.0));
        additionalIncome.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 4), 60.0));
        additionalIncome.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 1), 60.0));
        additionalIncome.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 2), 60.0));
        additionalIncome.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 3), 60.0));
        additionalIncome.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 4), 60.0));

        List<TimeSeriesItemRequestDto> depreciation = new ArrayList<TimeSeriesItemRequestDto>();
        depreciation.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 1), 55.0));
        depreciation.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 2), 60.0));
        depreciation.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 3), 60.0));
        depreciation.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 4), 60.0));
        depreciation.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 1), 60.0));
        depreciation.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 2), 60.0));
        depreciation.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 3), 60.0));
        depreciation.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 4), 60.0));
        depreciation.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 1), 60.0));
        depreciation.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 2), 60.0));
        depreciation.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 3), 60.0));
        depreciation.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 4), 60.0));

        List<TimeSeriesItemRequestDto> additionalCosts = new ArrayList<TimeSeriesItemRequestDto>();
        additionalCosts.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 1), 55.0));
        additionalCosts.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 2), 60.0));
        additionalCosts.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 3), 60.0));
        additionalCosts.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 4), 60.0));
        additionalCosts.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 1), 60.0));
        additionalCosts.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 2), 60.0));
        additionalCosts.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 3), 60.0));
        additionalCosts.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 4), 60.0));
        additionalCosts.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 1), 60.0));
        additionalCosts.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 2), 60.0));
        additionalCosts.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 3), 60.0));
        additionalCosts.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 4), 60.0));

        List<TimeSeriesItemRequestDto> investments = new ArrayList<TimeSeriesItemRequestDto>();
        investments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 1), 55.0));
        investments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 2), 60.0));
        investments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 3), 60.0));
        investments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 4), 60.0));
        investments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 1), 60.0));
        investments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 2), 60.0));
        investments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 3), 60.0));
        investments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 4), 60.0));
        investments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 1), 60.0));
        investments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 2), 60.0));
        investments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 3), 60.0));
        investments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 4), 60.0));

        List<TimeSeriesItemRequestDto> divestments = new ArrayList<TimeSeriesItemRequestDto>();
        divestments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 1), 55.0));
        divestments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 2), 60.0));
        divestments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 3), 60.0));
        divestments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 4), 60.0));
        divestments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 1), 60.0));
        divestments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 2), 60.0));
        divestments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 3), 60.0));
        divestments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 4), 60.0));
        divestments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 1), 60.0));
        divestments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 2), 60.0));
        divestments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 3), 60.0));
        divestments.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 4), 60.0));

        List<TimeSeriesItemRequestDto> revenue = new ArrayList<TimeSeriesItemRequestDto>();
        revenue.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 1), 55.0));
        revenue.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 2), 60.0));
        revenue.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 3), 60.0));
        revenue.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 4), 60.0));
        revenue.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 1), 60.0));
        revenue.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 2), 60.0));
        revenue.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 3), 60.0));
        revenue.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 4), 60.0));
        revenue.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 1), 60.0));
        revenue.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 2), 60.0));
        revenue.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 3), 60.0));
        revenue.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 4), 60.0));

        List<TimeSeriesItemRequestDto> costOfMaterial = new ArrayList<TimeSeriesItemRequestDto>();
        costOfMaterial.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 1), 55.0));
        costOfMaterial.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 2), 60.0));
        costOfMaterial.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 3), 60.0));
        costOfMaterial.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 4), 60.0));
        costOfMaterial.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 1), 60.0));
        costOfMaterial.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 2), 60.0));
        costOfMaterial.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 3), 60.0));
        costOfMaterial.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 4), 60.0));
        costOfMaterial.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 1), 60.0));
        costOfMaterial.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 2), 60.0));
        costOfMaterial.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 3), 60.0));
        costOfMaterial.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 4), 60.0));

        List<TimeSeriesItemRequestDto> costOfStaff = new ArrayList<TimeSeriesItemRequestDto>();
        costOfStaff.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 1), 55.0));
        costOfStaff.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 2), 60.0));
        costOfStaff.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 3), 60.0));
        costOfStaff.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 4), 60.0));
        costOfStaff.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 1), 60.0));
        costOfStaff.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 2), 60.0));
        costOfStaff.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 3), 60.0));
        costOfStaff.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 4), 60.0));
        costOfStaff.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 1), 60.0));
        costOfStaff.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 2), 60.0));
        costOfStaff.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 3), 60.0));
        costOfStaff.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 4), 60.0));

        List<TimeSeriesItemRequestDto> liabilities = new ArrayList<TimeSeriesItemRequestDto>();
        liabilities.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 1), 55.0));
        liabilities.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 2), 60.0));
        liabilities.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 3), 60.0));
        liabilities.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 4), 60.0));
        liabilities.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 1), 60.0));
        liabilities.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 2), 60.0));
        liabilities.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 3), 60.0));
        liabilities.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 4), 60.0));
        liabilities.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 1), 60.0));
        liabilities.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 2), 60.0));
        liabilities.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 3), 60.0));
        liabilities.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 4), 60.0));

        List<TimeSeriesItemRequestDto> freeCashFlowsTimeSeries = new ArrayList<TimeSeriesItemRequestDto>();
        freeCashFlowsTimeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 1), 50.0));
        freeCashFlowsTimeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 2), 60.0));
        freeCashFlowsTimeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 3), 60.0));
        freeCashFlowsTimeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014, 4), 60.0));
        freeCashFlowsTimeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 1), 60.0));
        freeCashFlowsTimeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 2), 60.0));
        freeCashFlowsTimeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 3), 60.0));
        freeCashFlowsTimeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015, 4), 60.0));
        freeCashFlowsTimeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 1), 60.0));
        freeCashFlowsTimeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 2), 60.0));
        freeCashFlowsTimeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 3), 60.0));
        freeCashFlowsTimeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016, 4), 60.0));

        additionalIncomeMPAFR = new MultiPeriodAccountingFigureRequestDto();
        additionalIncomeMPAFR.setTimeSeries(additionalIncome);

        depreciationMPAFR = new MultiPeriodAccountingFigureRequestDto();
        depreciationMPAFR.setTimeSeries(depreciation);

        additionalCostsMPAFR = new MultiPeriodAccountingFigureRequestDto();
        additionalCostsMPAFR.setTimeSeries(additionalCosts);

        investmentsMPAFR = new MultiPeriodAccountingFigureRequestDto();
        investmentsMPAFR.setTimeSeries(investments);

        divestmentsMPAFR = new MultiPeriodAccountingFigureRequestDto();
        divestmentsMPAFR.setTimeSeries(divestments);

        revenueMPAFR = new MultiPeriodAccountingFigureRequestDto();
        revenueMPAFR.setTimeSeries(revenue);

        costOfMaterialMPAFR = new MultiPeriodAccountingFigureRequestDto();
        costOfMaterialMPAFR.setTimeSeries(costOfMaterial);

        costOfStaffMPAFR = new MultiPeriodAccountingFigureRequestDto();
        costOfStaffMPAFR.setTimeSeries(costOfStaff);

        liabilitiesMPAFR = new MultiPeriodAccountingFigureRequestDto();
        liabilitiesMPAFR.setTimeSeries(liabilities);

        freeCashFlowsMPAFR = new MultiPeriodAccountingFigureRequestDto();
        freeCashFlowsMPAFR.setTimeSeries(freeCashFlowsTimeSeries);
    }

    @Test
    public void testScenarioCreation_Combination1_NonHistoric() {
        postRequestComb1.setScenarioName("PostTestComb1");
        postRequestComb1.setScenarioDescription("xyz");
        //prediction steps
        postRequestComb1.setPeriods(2);
        postRequestComb1.setBusinessTaxRate(0.5);
        postRequestComb1.setCorporateTaxRate(0.5);
        postRequestComb1.setSolidaryTaxRate(0.5);
        postRequestComb1.setEquityInterestRate(0.5);
        postRequestComb1.setInterestOnLiabilitiesRate(0.5);

        additionalIncomeMPAFR.setIsHistoric(false);
        depreciationMPAFR.setIsHistoric(false);
        additionalCostsMPAFR.setIsHistoric(false);
        investmentsMPAFR.setIsHistoric(false);
        divestmentsMPAFR.setIsHistoric(false);
        revenueMPAFR.setIsHistoric(false);
        costOfMaterialMPAFR.setIsHistoric(false);
        costOfStaffMPAFR.setIsHistoric(false);
        liabilitiesMPAFR.setIsHistoric(false);
        freeCashFlowsMPAFR.setIsHistoric(false);

        postRequestComb1.setAdditionalIncome(additionalIncomeMPAFR);
        postRequestComb1.setDepreciation(depreciationMPAFR);
        postRequestComb1.setAdditionalCosts(additionalCostsMPAFR);
        postRequestComb1.setInvestments(investmentsMPAFR);
        postRequestComb1.setDivestments(divestmentsMPAFR);
        postRequestComb1.setRevenue(revenueMPAFR);
        postRequestComb1.setCostOfMaterial(costOfMaterialMPAFR);
        postRequestComb1.setCostOfStaff(costOfStaffMPAFR);
        postRequestComb1.setLiabilities(liabilitiesMPAFR);

        Long comb1Id = scenarioService.create(postRequestComb1, appUserId);
        ScenarioResponseDto response = scenarioService.get(comb1Id, appUserId);

        //calculation methods are tested in other tests, therefore it is enough if response is not null / no exception occurs
        Assert.assertNotNull(response.getApvValuationResult());
        Assert.assertNotNull(response.getFteValuationResult());
        Assert.assertNotNull(response.getFcfValuationResult());
    }

    @Test
    public void testScenarioCreation_Combination1_Historic() {
        postRequestComb1.setScenarioName("PostTestComb1");
        postRequestComb1.setScenarioDescription("xyz");
        //prediction steps
        postRequestComb1.setPeriods(2);
        postRequestComb1.setBusinessTaxRate(0.5);
        postRequestComb1.setCorporateTaxRate(0.5);
        postRequestComb1.setSolidaryTaxRate(0.5);
        postRequestComb1.setEquityInterestRate(0.5);
        postRequestComb1.setInterestOnLiabilitiesRate(0.5);

        additionalIncomeMPAFR.setIsHistoric(true);
        depreciationMPAFR.setIsHistoric(true);
        additionalCostsMPAFR.setIsHistoric(true);
        investmentsMPAFR.setIsHistoric(true);
        divestmentsMPAFR.setIsHistoric(true);
        revenueMPAFR.setIsHistoric(true);
        costOfMaterialMPAFR.setIsHistoric(true);
        costOfStaffMPAFR.setIsHistoric(true);
        liabilitiesMPAFR.setIsHistoric(true);
        freeCashFlowsMPAFR.setIsHistoric(true);

        postRequestComb1.setAdditionalIncome(additionalIncomeMPAFR);
        postRequestComb1.setDepreciation(depreciationMPAFR);
        postRequestComb1.setAdditionalCosts(additionalCostsMPAFR);
        postRequestComb1.setInvestments(investmentsMPAFR);
        postRequestComb1.setDivestments(divestmentsMPAFR);
        postRequestComb1.setRevenue(revenueMPAFR);
        postRequestComb1.setCostOfMaterial(costOfMaterialMPAFR);
        postRequestComb1.setCostOfStaff(costOfStaffMPAFR);
        postRequestComb1.setLiabilities(liabilitiesMPAFR);

        Long comb1Id = scenarioService.create(postRequestComb1, appUserId);
        ScenarioResponseDto response = scenarioService.get(comb1Id, appUserId);

        Assert.assertNotNull(response.getApvValuationResult());
        Assert.assertNotNull(response.getFteValuationResult());
        Assert.assertNotNull(response.getFcfValuationResult());
    }
    
    @Test
    public void testScenarioCreation_Combination1_Historic_InsufficientObservations() {
        postRequestComb1.setScenarioName("PostTestComb1");
        postRequestComb1.setScenarioDescription("xyz");
        //prediction steps set to 20 for only 12 observations
        postRequestComb1.setPeriods(20);
        postRequestComb1.setBusinessTaxRate(0.5);
        postRequestComb1.setCorporateTaxRate(0.5);
        postRequestComb1.setSolidaryTaxRate(0.5);
        postRequestComb1.setEquityInterestRate(0.5);
        postRequestComb1.setInterestOnLiabilitiesRate(0.5);

        additionalIncomeMPAFR.setIsHistoric(true);
        depreciationMPAFR.setIsHistoric(true);
        additionalCostsMPAFR.setIsHistoric(true);
        investmentsMPAFR.setIsHistoric(true);
        divestmentsMPAFR.setIsHistoric(true);
        revenueMPAFR.setIsHistoric(true);
        costOfMaterialMPAFR.setIsHistoric(true);
        costOfStaffMPAFR.setIsHistoric(true);
        liabilitiesMPAFR.setIsHistoric(true);
        freeCashFlowsMPAFR.setIsHistoric(true);

        postRequestComb1.setAdditionalIncome(additionalIncomeMPAFR);
        postRequestComb1.setDepreciation(depreciationMPAFR);
        postRequestComb1.setAdditionalCosts(additionalCostsMPAFR);
        postRequestComb1.setInvestments(investmentsMPAFR);
        postRequestComb1.setDivestments(divestmentsMPAFR);
        postRequestComb1.setRevenue(revenueMPAFR);
        postRequestComb1.setCostOfMaterial(costOfMaterialMPAFR);
        postRequestComb1.setCostOfStaff(costOfStaffMPAFR);
        postRequestComb1.setLiabilities(liabilitiesMPAFR);

        Long comb1Id = scenarioService.create(postRequestComb1, appUserId);
        ScenarioResponseDto response = scenarioService.get(comb1Id, appUserId);

        Assert.assertNotNull(response.getApvValuationResult());
        Assert.assertNotNull(response.getFteValuationResult());
        Assert.assertNotNull(response.getFcfValuationResult());
    }

    @Test
    public void testScenarioCreation_Combination2_NonHistoric() {
        postRequestComb2.setScenarioName("PostTestComb2");
        postRequestComb2.setScenarioDescription("xyz");
        //prediction steps
        postRequestComb2.setPeriods(2);
        postRequestComb2.setBusinessTaxRate(0.5);
        postRequestComb2.setCorporateTaxRate(0.5);
        postRequestComb2.setSolidaryTaxRate(0.5);
        postRequestComb2.setEquityInterestRate(0.5);
        postRequestComb2.setInterestOnLiabilitiesRate(0.5);

        additionalIncomeMPAFR.setIsHistoric(false);
        depreciationMPAFR.setIsHistoric(false);
        additionalCostsMPAFR.setIsHistoric(false);
        investmentsMPAFR.setIsHistoric(false);
        divestmentsMPAFR.setIsHistoric(false);
        revenueMPAFR.setIsHistoric(false);
        costOfMaterialMPAFR.setIsHistoric(false);
        costOfStaffMPAFR.setIsHistoric(false);
        liabilitiesMPAFR.setIsHistoric(false);
        freeCashFlowsMPAFR.setIsHistoric(false);

        postRequestComb2.setFreeCashFlows(freeCashFlowsMPAFR);
        postRequestComb2.setLiabilities(liabilitiesMPAFR);

        Long comb2Id = scenarioService.create(postRequestComb2, appUserId);
        ScenarioResponseDto response = scenarioService.get(comb2Id, appUserId);

        Assert.assertNotNull(response.getApvValuationResult());
        Assert.assertNotNull(response.getFteValuationResult());
        Assert.assertNotNull(response.getFcfValuationResult());
    }

    @Test
    public void testScenarioCreation_Combination2_Historic() {
        postRequestComb2.setScenarioName("PostTestComb2");
        postRequestComb2.setScenarioDescription("xyz");
        //prediction steps
        postRequestComb2.setPeriods(2);
        postRequestComb2.setBusinessTaxRate(0.5);
        postRequestComb2.setCorporateTaxRate(0.5);
        postRequestComb2.setSolidaryTaxRate(0.5);
        postRequestComb2.setEquityInterestRate(0.5);
        postRequestComb2.setInterestOnLiabilitiesRate(0.5);

        additionalIncomeMPAFR.setIsHistoric(true);
        depreciationMPAFR.setIsHistoric(true);
        additionalCostsMPAFR.setIsHistoric(true);
        investmentsMPAFR.setIsHistoric(true);
        divestmentsMPAFR.setIsHistoric(true);
        revenueMPAFR.setIsHistoric(true);
        costOfMaterialMPAFR.setIsHistoric(true);
        costOfStaffMPAFR.setIsHistoric(true);
        liabilitiesMPAFR.setIsHistoric(true);
        freeCashFlowsMPAFR.setIsHistoric(true);

        postRequestComb2.setFreeCashFlows(freeCashFlowsMPAFR);
        postRequestComb2.setLiabilities(liabilitiesMPAFR);

        Long comb2Id = scenarioService.create(postRequestComb2, appUserId);
        ScenarioResponseDto response = scenarioService.get(comb2Id, appUserId);

        Assert.assertNotNull(response.getApvValuationResult());
        Assert.assertNotNull(response.getFteValuationResult());
        Assert.assertNotNull(response.getFcfValuationResult());
    }

    @Test
    public void testScenarioDelete() {
        List<ScenarioResponseDto> availableScenarios = scenarioService.getAll(appUserId);
        if (availableScenarios.size() > 1) {
            Long idToUseForDelete = availableScenarios.get((int) (Math.random() * availableScenarios.size())).getId();
            scenarioService.delete(idToUseForDelete, appUserId);
        } else {
            System.out.println("Skipping test due to not enough scenarios in test account");
        }
    }

    @Test
    public void testScenarioUpdate_Comb1Scenario_NonHistoric() {
        List<ScenarioResponseDto> availableScenarios = scenarioService.getAll(appUserId);
        if (availableScenarios.size() > 0) {
            Long idToUseForPutRequest = availableScenarios.get(0).getId();

            putRequestComb1.setScenarioName("blub");
            putRequestComb1.setScenarioDescription("updated description");
            //prediction steps
            putRequestComb1.setPeriods(2);
            putRequestComb1.setBusinessTaxRate(0.5);
            putRequestComb1.setCorporateTaxRate(0.5);
            putRequestComb1.setSolidaryTaxRate(0.5);
            putRequestComb1.setEquityInterestRate(0.5);
            putRequestComb1.setInterestOnLiabilitiesRate(0.5);

            additionalIncomeMPAFR.setIsHistoric(false);
            depreciationMPAFR.setIsHistoric(false);
            additionalCostsMPAFR.setIsHistoric(false);
            investmentsMPAFR.setIsHistoric(false);
            divestmentsMPAFR.setIsHistoric(false);
            revenueMPAFR.setIsHistoric(false);
            costOfMaterialMPAFR.setIsHistoric(false);
            costOfStaffMPAFR.setIsHistoric(false);
            liabilitiesMPAFR.setIsHistoric(false);
            freeCashFlowsMPAFR.setIsHistoric(false);

            putRequestComb1.setAdditionalIncome(additionalIncomeMPAFR);
            putRequestComb1.setDepreciation(depreciationMPAFR);
            putRequestComb1.setAdditionalCosts(additionalCostsMPAFR);
            putRequestComb1.setInvestments(investmentsMPAFR);
            putRequestComb1.setDivestments(divestmentsMPAFR);
            putRequestComb1.setRevenue(revenueMPAFR);
            putRequestComb1.setCostOfMaterial(costOfMaterialMPAFR);
            putRequestComb1.setCostOfStaff(costOfStaffMPAFR);
            putRequestComb1.setLiabilities(liabilitiesMPAFR);

            putRequestComb1.setId(idToUseForPutRequest);
            putRequestComb1.setStochastic(Boolean.FALSE);

            scenarioService.update(putRequestComb1, appUserId);
        } else {
            System.out.println("Skipping test due to not enough scenarios in test account");
        }
    }

    @Test
    public void getAll_Test() {
        scenarioService.getAll(appUserId);
    }

    @Test
    public void get_Test() {
        List<ScenarioResponseDto> availableScenarios = scenarioService.getAll(appUserId);
        if (availableScenarios.size() > 1) {
            availableScenarios.get((int) (Math.random() * availableScenarios.size())).getId();
        } else {
            System.out.println("Skipping test due to not enough scenarios in test account");
        }
    }

}
