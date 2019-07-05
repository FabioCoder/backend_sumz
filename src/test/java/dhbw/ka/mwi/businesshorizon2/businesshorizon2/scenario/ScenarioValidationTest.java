package dhbw.ka.mwi.businesshorizon2.businesshorizon2.scenario;

import edu.dhbw.ka.mwi.businesshorizon2.App;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services.ScenarioService;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IAppUserRepository;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IScenarioRepository;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppUserDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.ScenarioDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.*;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.ScenarioMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ScenarioValidationTest {

    @Autowired
    private Validator validator;

    @Autowired
    private IAppUserRepository userRepository;

    @Autowired
    ScenarioService scenarioService;

    private AppUserDao appUser;

    ScenarioPostRequestDto scenariodto;

    @Autowired
    private IScenarioRepository scenarioRepository;

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

        appUser = new AppUserDao();
        appUser.setEmail("testmail");
        appUser.setPassword("123Abc@");
        appUser.setIsActive(true);
        if(userRepository.findByEmail(appUser.getEmail()) != null) userRepository.deleteById(userRepository.findByEmail(appUser.getEmail()).getAppUserId());
        userRepository.save(appUser);


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

        scenariodto = new ScenarioPostRequestDto();

        scenariodto.setScenarioName("PostTestComb1");
        scenariodto.setScenarioDescription("xyz");
        scenariodto.setScenarioColor("Green");
        //prediction steps
        scenariodto.setPeriods(2);
        scenariodto.setBusinessTaxRate(0.5);
        scenariodto.setCorporateTaxRate(0.5);
        scenariodto.setSolidaryTaxRate(0.5);
        scenariodto.setEquityInterestRate(0.5);
        scenariodto.setInterestOnLiabilitiesRate(0.5);
        scenariodto.setBrownRozeff(false);

        scenariodto.setAdditionalIncome(additionalIncomeMPAFR);
        scenariodto.setDepreciation(depreciationMPAFR);
        scenariodto.setAdditionalCosts(additionalCostsMPAFR);
        scenariodto.setInvestments(investmentsMPAFR);
        scenariodto.setDivestments(divestmentsMPAFR);
        scenariodto.setRevenue(revenueMPAFR);
        scenariodto.setCostOfMaterial(costOfMaterialMPAFR);
        scenariodto.setCostOfStaff(costOfStaffMPAFR);
        scenariodto.setLiabilities(liabilitiesMPAFR);

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

    }

    @Test
    public void testScenarioColorSettings() {

        scenariodto.setScenarioColor("InvalidColor");
        long id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

    }

    @Test
    public void testScenarioOrderSettings() {
        MultiPeriodAccountingFigureRequestDto addInc = scenariodto.getAdditionalIncome();
        addInc.setOrder(null);
        long id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setOrder(new Integer []{0, 1, 1, 1000, 124124, 44444444, 1, 1, 1});
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setOrder(new Integer []{0});
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setOrder(new Integer []{-54325, -3});
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        //seasonal Order
        addInc.setSeasonalOrder(null);
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setSeasonalOrder(new Integer []{0, 1, 1, 1000, 124124, 44444444, 1, 1, 1});
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setSeasonalOrder(new Integer []{0});
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setSeasonalOrder(new Integer []{-54325, -3});
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());



        addInc.setIsHistoric(true);
        addInc.setOrder(null);
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setOrder(new Integer []{0, 1, 1, 1000, 124124, 44444444, 1, 1, 1});
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setOrder(new Integer []{0});
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setOrder(new Integer []{-54325, -3});
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        //seasonal Order
        addInc.setSeasonalOrder(null);
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setSeasonalOrder(new Integer []{0, 1, 1, 1000, 124124, 44444444, 1, 1, 1});
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setSeasonalOrder(new Integer []{0});
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setSeasonalOrder(new Integer []{-54325, -3});
        id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, appUser.getAppUserId());

        addInc.setIsHistoric(false);
        addInc.setSeasonalOrder(null);
        addInc.setOrder(null);
    }

    @Test(expected = Exception.class)
    public void testScenarioCreateInvalidId() {
        scenarioService.create(scenariodto, -123L);
    }

    @Test(expected = Exception.class)
    public void testScenarioDelete_InvalidUserId() {
        scenarioService.delete(1L, -123L);
    }

    @Test(expected = Exception.class)
    public void testScenarioGet_InvalidScenarioId() {
        scenarioService.get(-123L, appUser.getAppUserId());
    }
    @Test(expected = Exception.class)
    public void testScenarioGet_InvalidUserId() {
        scenarioService.get(0L, -123L);
    }
    @Test(expected = Exception.class)
    public void testScenarioGet_InvalidPermission() {
        AppUserDao sec = new AppUserDao();
        sec.setPassword("Abc123@");
        sec.setEmail("testmailforsec@test.test");
        if(userRepository.findByEmail(sec.getEmail()) != null) userRepository.deleteById(userRepository.findByEmail(sec.getEmail()).getAppUserId());
        userRepository.save(sec);

        long id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.get(id, sec.getAppUserId());

    }

    @Test(expected = Exception.class)
    public void testScenarioDelete_InvalidScenarioId() {
        scenarioService.delete(-123L, appUser.getAppUserId());
    }

    @Test(expected = Exception.class)
    public void testScenarioDelete_InvalidPermission() {
        AppUserDao sec = new AppUserDao();
        sec.setPassword("Abc123@");
        sec.setEmail("testmailforsec@test.test");
        if(userRepository.findByEmail(sec.getEmail()) != null) userRepository.deleteById(userRepository.findByEmail(sec.getEmail()).getAppUserId());
        userRepository.save(sec);

        long id = scenarioService.create(scenariodto, appUser.getAppUserId());
        scenarioService.delete(id, sec.getAppUserId());

    }

    @Test()
    public void testScenarioGetAll_InvalidID() {
        List<ScenarioResponseDto> list = scenarioService.getAll(-123L);
        assertTrue(list.size() == 0);
    }

    @Test
    public void testScenarioValidationSettings() {

        //Test ScenarioName Constraints
        validateScenario(scenariodto, 0);
        scenariodto.setScenarioName(null);
        validateScenario(scenariodto, 1);
        scenariodto.setScenarioName("");
        validateScenario(scenariodto, 1);
        scenariodto.setScenarioName("VERY LONG STRIIIIIIIIIIIIIIING");
        validateScenario(scenariodto, 1);
        scenariodto.setScenarioName("ValidName");

        //Test ScenarioDescription Constraints
        scenariodto.setScenarioDescription(null);
        validateScenario(scenariodto, 1);
        scenariodto.setScenarioDescription("");
        validateScenario(scenariodto, 1);
        scenariodto.setScenarioDescription("-------------------------------------------------------------------------" +
                "----------------------------------------------------------------------------------------------------" +
                "----------------------------------------------------------------------------------------------------");
        validateScenario(scenariodto, 1);
        scenariodto.setScenarioDescription("ValidDescription");

        //Test Periods Constraint
        scenariodto.setPeriods(null);
        validateScenario(scenariodto, 1);
        scenariodto.setPeriods(1);
        validateScenario(scenariodto, 1);
        scenariodto.setPeriods(2);

        //Test Rates Constraint
        scenariodto.setBusinessTaxRate(-0.1);
        scenariodto.setCorporateTaxRate(-0.1);
        scenariodto.setSolidaryTaxRate(-0.1);
        scenariodto.setInterestOnLiabilitiesRate(-0.1);
        validateScenario(scenariodto, 4);
        scenariodto.setBusinessTaxRate(1.1);
        scenariodto.setCorporateTaxRate(1.1);
        scenariodto.setSolidaryTaxRate(1.1);
        scenariodto.setInterestOnLiabilitiesRate(1.1);
        validateScenario(scenariodto, 4);
        scenariodto.setBusinessTaxRate(0.5);
        scenariodto.setCorporateTaxRate(0.5);
        scenariodto.setSolidaryTaxRate(0.5);
        scenariodto.setInterestOnLiabilitiesRate(0.5);

        //Test equityInterestRate Contstraint
        scenariodto.setEquityInterestRate(-0.2);
        validateScenario(scenariodto, 1);
        scenariodto.setEquityInterestRate(1.1);
        validateScenario(scenariodto, 1);

        MultiPeriodAccountingFigureRequestDto addIncome = scenariodto.getAdditionalIncome();
        addIncome.setIsHistoric(null);
        validateScenario(scenariodto, 1);
        addIncome.setIsHistoric(false);

        TimeSeriesItemRequestDto tsdto = addIncome.getTimeSeries().get(0);
        tsdto.setDate(null);
        tsdto.setAmount(null);
        validateScenario(scenariodto, 2);
        tsdto.setDate(new TimeSeriesItemDateRequestDto(2014, 1));
        tsdto.setAmount(50.0);

        TimeSeriesItemDateRequestDto datedto = tsdto.getDate();
        datedto.setYear(0);
        datedto.setQuarter(0);
        validateScenario(scenariodto, 2);
        datedto.setYear(2200);
        datedto.setQuarter(5);
        validateScenario(scenariodto, 2);
        datedto.setYear(2014);
        datedto.setQuarter(1);
        validateScenario(scenariodto, 0);

    }

    /**
     * Validates a scenariodto.
     * @param source dto to validate
     * @param expectErrors should this instance of the dto normally produce a validation error
     */
    private void validateScenario(ScenarioPostRequestDto source, int expectErrors) {
        Set<ConstraintViolation<ScenarioPostRequestDto>> violationSet = validator.validate(source);
        Iterator<ConstraintViolation<ScenarioPostRequestDto>> iterator = violationSet.iterator();
        String errors = "All Errors: ";
        for(ConstraintViolation<ScenarioPostRequestDto> constraint = iterator.next();;) {
            errors = errors + " " + constraint.getMessage();
            if(!iterator.hasNext()) break;
        }
        assertTrue(errors, violationSet.size() == expectErrors);
    }

}
