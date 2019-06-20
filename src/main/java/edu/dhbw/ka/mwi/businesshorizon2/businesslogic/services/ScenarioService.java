package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IAccountingFigureCalculationsService;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.ICompanyValuationService;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IScenarioService;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.ITimeSeriesPredictionService;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IAppUserRepository;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IScenarioGraphRepository;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IScenarioRepository;
import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppUserDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.MultiPeriodAccountingFigureDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.ScenarioDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ApvCompanyValuationResultDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.CompanyValueDistributionDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.FcfCompanyValuationResultDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.FteCompanyValuationResultDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.MultiPeriodAccountingFigureRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioPostRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioPutRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioResponseDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.ApvCompanyValuationResultMapper;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.CompanyValueDistributionMapper;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.FcfCompanyValuationResultMapper;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.FteCompanyValuationResultMapper;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.ScenarioMapper;
import javassist.tools.web.BadHttpRequest;

//this is a key class since it basically initiates the whole scenario creation and calculation
//the ScenarioRepository / ScenarioGraphRepository (difference?) is the storage to which all scenarios are written to  / deleted from
/**
 *
 * @author WWI DHBW KA
 */
@Service
public class ScenarioService implements IScenarioService {

    //why would you need request 500 samples (excact same prediction -> unnecessary load) -> to calculate the distribution?
    private final Integer numSamples = 500;

    @Autowired
    private IAccountingFigureCalculationsService accountingService;

    @Autowired
    private ITimeSeriesPredictionService predictionService;

    @Autowired
    private ICompanyValuationService companyValuationService;

    @Autowired
    private IScenarioRepository scenarioRepository;

    @Autowired
    private IScenarioGraphRepository scenarioGraphRepository;

    /**
     *
     * @param scenarioDto
     * @param appUserId
     * @return
     */
    @Override
    public Long create(ScenarioPostRequestDto scenarioDto, Long appUserId) {

        //this is a key-value object for all calculation inputs that is filled later?
        //the list represents the time series values for each Accouting figure
        HashMap<MultiPeriodAccountingFigureNames, List<Double>> deterministicAccountingFigures
                = new HashMap<MultiPeriodAccountingFigureNames, List<Double>>();
        
        //HashMap<Integer, List<Double>> because for stochastic valuation we later request a lot of samples and we identify one sample by the integer -> not only one time series per figure
        HashMap<MultiPeriodAccountingFigureNames, HashMap<Integer, List<Double>>> stochasticAccountingFigures
                = new HashMap<MultiPeriodAccountingFigureNames, HashMap<Integer, List<Double>>>();

        //contains all of the required variables (including the time series etc.) for company valuation if the user enters future values -> user input
        List<MultiPeriodAccountingFigureRequestDto> nonHistoricAccountingFigures = scenarioDto.getAllMultiPeriodAccountingFigures();
        //since the user might not have entered all values, we need to remove the ones with null value
        nonHistoricAccountingFigures.removeIf(x -> x == null || x.getTimeSeries() == null || x.getIsHistoric() == null || x.getIsHistoric().equals(true));

        //contains all of the required variables for company valuation if the user enters historical values -> user  input
        List<MultiPeriodAccountingFigureRequestDto> historicAccountingFigures = scenarioDto.getAllMultiPeriodAccountingFigures();
        //since the user might not have entered all values, we need to remove the ones with null value
        historicAccountingFigures.removeIf(x -> x == null || x.getTimeSeries() == null || x.getIsHistoric() == null || x.getIsHistoric().equals(false));

        //if the historical data is not empty the valuation is stochastic
        boolean isValuationStochastic = !historicAccountingFigures.isEmpty();

        //freeCashFlowsProvided variable is set later
        boolean freeCashFlowsProvided = false;

        //calculate the effective Tax rate
        double effectiveTaxRate = accountingService.calculateEffectiveTaxRate(scenarioDto.getBusinessTaxRate(), scenarioDto.getCorporateTaxRate(), scenarioDto.getSolidaryTaxRate());

        //for each figure/variable entered by the user:
        //first we only consider nonHistoricAccountingFigures
        for (MultiPeriodAccountingFigureRequestDto figure : nonHistoricAccountingFigures) {
            //since nonHistoricAccountingFigures occur if the user enters future values (so no prediction is needed), a deterministic calculation can be done
            deterministicAccountingFigures.put(figure.getFigureName(), figure.getTimeSeriesAmountsSortedAscByDate());

            //check whether free cash flows are provided, if this is the case the calculation is easy
            if (figure.getFigureName() == MultiPeriodAccountingFigureNames.FreeCashFlows) {
                freeCashFlowsProvided = true;
            }
        }
        //if the nonhistoricaccountingfigures do not contain free cash flows, check whether free cash flows are provided for the historic accounting figures
        if (!freeCashFlowsProvided) {
            for (MultiPeriodAccountingFigureRequestDto figure : historicAccountingFigures) {
                if (figure.getFigureName() == MultiPeriodAccountingFigureNames.FreeCashFlows) {
                    freeCashFlowsProvided = true;
                    break;
                }
            }
        }

        //if a stochastic valuation has to be done, we need predictions that we request here for all historic accounting figures
        //TODO: return value not checked for bad response of the python backend?
        if (isValuationStochastic) {
            predictionService.MakePredictions(historicAccountingFigures, stochasticAccountingFigures, scenarioDto.getPeriods(), numSamples);
        }

        ScenarioDao scenarioDao = ScenarioMapper.mapDtoToDao(scenarioDto);
        ApvCompanyValuationResultDto apvRes;
        FteCompanyValuationResultDto fteRes;
        FcfCompanyValuationResultDto fcfRes;

        //calculate the company's value with stochastic calculation
        if (isValuationStochastic) {

            //this for loop is to receive a lot of samples of the prediction to determine the distribution later?
            List<Double> companyValues = new ArrayList<Double>();
            for (int sampleNum = 1; sampleNum <= numSamples; sampleNum++) {

                List<Double> liabilities = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.Liabilities, deterministicAccountingFigures, stochasticAccountingFigures, sampleNum);

                List<Double> freeCashFlows;

                if (freeCashFlowsProvided) {
                    freeCashFlows = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.FreeCashFlows, deterministicAccountingFigures, stochasticAccountingFigures, sampleNum);
                } else {
                    //if free cash flows are not provided, we have to calculate them
                    freeCashFlows = getFreeCashFlows(deterministicAccountingFigures, stochasticAccountingFigures, scenarioDto.getPeriods(), scenarioDto.getBusinessTaxRate(), scenarioDto.getCorporateTaxRate(), scenarioDto.getSolidaryTaxRate(), sampleNum);
                }

                List<Double> ftes = accountingService.calculateFlowToEquity(
                        freeCashFlows,
                        liabilities,
                        scenarioDto.getInterestOnLiabilitiesRate(),
                        effectiveTaxRate);

                if (!stochasticAccountingFigures.containsKey(MultiPeriodAccountingFigureNames.FreeCashFlows)) {
                    stochasticAccountingFigures.put(MultiPeriodAccountingFigureNames.FreeCashFlows, new HashMap<Integer, List<Double>>());
                }
                stochasticAccountingFigures.get(MultiPeriodAccountingFigureNames.FreeCashFlows).put(sampleNum, freeCashFlows);

                if (!stochasticAccountingFigures.containsKey(MultiPeriodAccountingFigureNames.FlowToEquity)) {
                    stochasticAccountingFigures.put(MultiPeriodAccountingFigureNames.FlowToEquity, new HashMap<Integer, List<Double>>());
                }
                stochasticAccountingFigures.get(MultiPeriodAccountingFigureNames.FlowToEquity).put(sampleNum, ftes);

                ApvCompanyValuationResultDto res = companyValuationService.performApvCompanyValuation(
                        freeCashFlows,
                        liabilities,
                        scenarioDto.getEquityInterestRate(),
                        scenarioDto.getInterestOnLiabilitiesRate(),
                        effectiveTaxRate);

                companyValues.add(res.getCompanyValue());
            }

            //mean / average values are calculated that are used in the stochastic valuation later
            List<Double> meanFreeCashFlows = deterministicAccountingFigures.containsKey(MultiPeriodAccountingFigureNames.FreeCashFlows)
                    ? deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.FreeCashFlows)
                    : accountingService.getMeanAccountingFigureValues(stochasticAccountingFigures, MultiPeriodAccountingFigureNames.FreeCashFlows, scenarioDto.getPeriods());

            List<Double> meanLiabilities = deterministicAccountingFigures.containsKey(MultiPeriodAccountingFigureNames.Liabilities)
                    ? deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.Liabilities)
                    : accountingService.getMeanAccountingFigureValues(stochasticAccountingFigures, MultiPeriodAccountingFigureNames.Liabilities, scenarioDto.getPeriods());

            List<Double> meanFlowToEquity = deterministicAccountingFigures.containsKey(MultiPeriodAccountingFigureNames.FlowToEquity)
                    ? deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.FlowToEquity)
                    : accountingService.getMeanAccountingFigureValues(stochasticAccountingFigures, MultiPeriodAccountingFigureNames.FlowToEquity, scenarioDto.getPeriods());

            //perform valuation calculations with the different company calculation methods, used are averages / means
            apvRes = companyValuationService.performApvCompanyValuation(
                    meanFreeCashFlows,
                    meanLiabilities,
                    scenarioDto.getEquityInterestRate(),
                    scenarioDto.getInterestOnLiabilitiesRate(),
                    effectiveTaxRate);

            fteRes = companyValuationService.performFteCompanyValuationResult(
                    meanFlowToEquity,
                    meanLiabilities,
                    scenarioDto.getEquityInterestRate(),
                    scenarioDto.getInterestOnLiabilitiesRate(),
                    effectiveTaxRate);

            fcfRes = companyValuationService.performFcfCompanyValuationResult(
                    meanFreeCashFlows,
                    meanLiabilities,
                    scenarioDto.getEquityInterestRate(),
                    scenarioDto.getInterestOnLiabilitiesRate(),
                    effectiveTaxRate);

            //receive the distribution of the company values
            CompanyValueDistributionDto companyValueDistribution = companyValuationService.getCompanyValueDistribution(companyValues);
            scenarioDao.setCompanyValueDistributionPoints(CompanyValueDistributionMapper.mapDtoToDao(companyValueDistribution));
        } //calculate the company's value with deterministic calculation
        else {
            List<Double> liabilities = deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.Liabilities);
            List<Double> freeCashFlows;

            if (freeCashFlowsProvided) {
                freeCashFlows = deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.FreeCashFlows);
            } else {

                //if free cash flows are not provided, we have to calculate them
                freeCashFlows = getFreeCashFlows(
                        deterministicAccountingFigures,
                        scenarioDto.getPeriods(),
                        scenarioDto.getBusinessTaxRate(),
                        scenarioDto.getCorporateTaxRate(),
                        scenarioDto.getSolidaryTaxRate());
            }
            for (int i = 0; i < freeCashFlows.size(); i++) {
                System.out.println(freeCashFlows.get(i) + ", ");
            }
            //calculate the  flow to equity that is needed for the fte method
            List<Double> ftes = accountingService.calculateFlowToEquity(
                    freeCashFlows,
                    liabilities,
                    scenarioDto.getInterestOnLiabilitiesRate(),
                    effectiveTaxRate);

            //perform valuation calculations with the different company calculation methods
            apvRes = companyValuationService.performApvCompanyValuation(
                    freeCashFlows,
                    liabilities,
                    scenarioDto.getEquityInterestRate(),
                    scenarioDto.getInterestOnLiabilitiesRate(),
                    effectiveTaxRate);

            fteRes = companyValuationService.performFteCompanyValuationResult(
                    ftes,
                    liabilities,
                    scenarioDto.getEquityInterestRate(),
                    scenarioDto.getInterestOnLiabilitiesRate(),
                    effectiveTaxRate);

            fcfRes = companyValuationService.performFcfCompanyValuationResult(
                    freeCashFlows,
                    liabilities,
                    scenarioDto.getEquityInterestRate(),
                    scenarioDto.getInterestOnLiabilitiesRate(),
                    effectiveTaxRate);
        }

        //create & save the scenario by mapping it to the DAO
        scenarioDao.setApvCompanyValuationResultDao(ApvCompanyValuationResultMapper.mapDtoToDao(apvRes));
        scenarioDao.setFteCompanyValuationResultDao(FteCompanyValuationResultMapper.mapDtoToDao(fteRes));
        scenarioDao.setFcfCompanyValuationResultDao(FcfCompanyValuationResultMapper.mapDtoToDao(fcfRes));

        Long scenarioInDbId = scenarioGraphRepository.create(scenarioDao, appUserId);

        return scenarioInDbId;
    }

    /**
     *
     * @param scenarioId
     * @param appUserId
     */
    @Override
    public void delete(Long scenarioId, Long appUserId) {

        //This deletes an existing scenario
        ScenarioDao dao = scenarioRepository.get(scenarioId);

        if (dao == null) {
            throw new IllegalArgumentException("The requested scenario does not exist.");
        }

        if (!dao.getAppUser().getAppUserId().equals(appUserId)) {
            throw new AccessDeniedException("The requested user is not authorized to delete the requested scenario.");
        }

        scenarioRepository.delete(dao);
    }

    /**
     *
     * @param appUserId
     * @return
     */
    @Override
    public List<ScenarioResponseDto> getAll(Long appUserId) {

        //This returns all scenarios for an user
        List<ScenarioDao> daos = scenarioRepository.getAll(appUserId);
        List<ScenarioResponseDto> dtos = ScenarioMapper.mapDaoToDto(daos);

        return dtos;
    }

    /**
     *
     * @param scenarioId
     * @param appUserId
     * @return
     */
    @Override
    public ScenarioResponseDto get(Long scenarioId, Long appUserId) {

        //this returns a specific scenario identified by the scenarioId and appUserId
        ScenarioDao dao = scenarioRepository.get(scenarioId);

        if (dao == null) {
            throw new IllegalArgumentException("The requested scenario does not exist.");
        }

        if (!dao.getAppUser().getAppUserId().equals(appUserId)) {
            throw new AccessDeniedException("The requested user is not authorized to access the requested scenario.");
        }

        return ScenarioMapper.mapDaoToDto(dao);
    }

    /**
     *
     * @param scenarioDto
     * @param appUserId
     * @return
     */
    @Override
    public Long update(ScenarioPutRequestDto scenarioDto, Long appUserId) {

        //this updates an existing scenario by deleting it and creating a new one
        ScenarioDao dao = scenarioRepository.get(scenarioDto.getId());

        if (dao == null) {
            throw new IllegalArgumentException("The requested scenario does not exist.");
        }

        if (!dao.getAppUser().getAppUserId().equals(appUserId)) {
            throw new AccessDeniedException("The requested user is not authorized to update the requested scenario.");
        }
        
        try {
            Long newId = create(ScenarioMapper.mapPutDtoToPostDto(scenarioDto), appUserId);
            scenarioRepository.delete(dao);
            return newId;
        } catch(RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Exception while trying to update the scenario. Therefore, old one has not been deleted.");
        }

    }

    private List<Double> getFreeCashFlows(
            //this provides free cash flows by using the accouting service
            HashMap<MultiPeriodAccountingFigureNames, List<Double>> deterministicAccountingFigures,
            HashMap<MultiPeriodAccountingFigureNames, HashMap<Integer, List<Double>>> stochasticAccountingFigures,
            Integer periods,
            Double businessTaxRate,
            Double corporateTaxRate,
            Double solidaryTaxRate,
            Integer sampleNum) {

        List<Double> revenues = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.Revenue, deterministicAccountingFigures, stochasticAccountingFigures, sampleNum);
        List<Double> additionalIncomes = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.AdditionalIncome, deterministicAccountingFigures, stochasticAccountingFigures, sampleNum);
        List<Double> costOfMaterials = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.CostOfMaterial, deterministicAccountingFigures, stochasticAccountingFigures, sampleNum);
        List<Double> costOfStaffs = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.CostOfStaff, deterministicAccountingFigures, stochasticAccountingFigures, sampleNum);
        List<Double> additionalCostss = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.AdditionalCosts, deterministicAccountingFigures, stochasticAccountingFigures, sampleNum);
        List<Double> depreciations = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.Depreciation, deterministicAccountingFigures, stochasticAccountingFigures, sampleNum);
        List<Double> investments = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.Investments, deterministicAccountingFigures, stochasticAccountingFigures, sampleNum);
        List<Double> divestments = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.Divestments, deterministicAccountingFigures, stochasticAccountingFigures, sampleNum);

        List<Double> freeCashFlows = accountingService.calculateFreeCashFlow(
                revenues,
                additionalIncomes,
                costOfMaterials,
                costOfStaffs,
                additionalCostss,
                depreciations,
                businessTaxRate,
                corporateTaxRate,
                solidaryTaxRate,
                investments,
                divestments);

        return freeCashFlows;
    }

    private List<Double> getFreeCashFlows(
            HashMap<MultiPeriodAccountingFigureNames, List<Double>> deterministicAccountingFigures,
            Integer periods,
            Double businessTaxRate,
            Double corporateTaxRate,
            Double solidaryTaxRate) {

        List<Double> revenues = deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.Revenue);
        List<Double> additionalIncomes = deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.AdditionalIncome);
        List<Double> costOfMaterials = deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.CostOfMaterial);
        List<Double> costOfStaffs = deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.CostOfStaff);
        List<Double> additionalCostss = deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.AdditionalCosts);
        List<Double> depreciations = deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.Depreciation);
        List<Double> investments = deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.Investments);
        List<Double> divestments = deterministicAccountingFigures.get(MultiPeriodAccountingFigureNames.Divestments);

        List<Double> freeCashFlows = accountingService.calculateFreeCashFlow(
                revenues,
                additionalIncomes,
                costOfMaterials,
                costOfStaffs,
                additionalCostss,
                depreciations,
                businessTaxRate,
                corporateTaxRate,
                solidaryTaxRate,
                investments,
                divestments);

        return freeCashFlows;
    }

    private List<Double> getDeterministicOrStochasticAccountingFigure(
            MultiPeriodAccountingFigureNames figureName,
            HashMap<MultiPeriodAccountingFigureNames, List<Double>> deterministicAccountingFigures,
            HashMap<MultiPeriodAccountingFigureNames, HashMap<Integer, List<Double>>> stochasticAccountingFigures,
            Integer sampleNum
    ) {
        return deterministicAccountingFigures.containsKey(figureName)
                ? deterministicAccountingFigures.get(figureName)
                : stochasticAccountingFigures.get(figureName).get(sampleNum);
    }
}
