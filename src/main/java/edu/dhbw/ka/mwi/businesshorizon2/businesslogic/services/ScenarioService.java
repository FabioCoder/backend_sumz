package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IAccountingFigureCalculationsService;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.ICompanyValuationService;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IScenarioService;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.ITimeSeriesPredictionService;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IScenarioGraphRepository;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IScenarioRepository;
import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.ScenarioDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ApvCompanyValuationResultDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.CompanyValueDistributionDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.FcfCompanyValuationResultDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.FteCompanyValuationResultDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.PredictedHistoricAccountingFigureDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.MultiPeriodAccountingFigureRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.DoubleKeyValueListDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioPostRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioPutRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioResponseDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.ApvCompanyValuationResultMapper;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.CompanyValueDistributionMapper;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.FcfCompanyValuationResultMapper;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.FteCompanyValuationResultMapper;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.ScenarioMapper;
import java.util.AbstractMap;
import java.util.ArrayList;

//this is a key class since it basically initiates the whole scenario creation and calculation
//the ScenarioRepository / ScenarioGraphRepository (difference?) is the storage to which all scenarios are written to  / deleted from
/**
 *
 * @author WWI DHBW KA
 */
@Service
public class ScenarioService implements IScenarioService {

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

        ScenarioDao scenarioDao = ScenarioMapper.mapDtoToDao(scenarioDto);

        //TODO: give dtos variance???
        ApvCompanyValuationResultDto apvRes;
        FteCompanyValuationResultDto fteRes;
        FcfCompanyValuationResultDto fcfRes;

        //this is a key-value object for all calculation inputs that is filled later?
        //the list represents the time series values for each Accouting figure
        HashMap<MultiPeriodAccountingFigureNames, List<Double>> deterministicAccountingFigures = new HashMap<>();
        //stochastic / predicted time series are represented by historicAccountingFigureDtoList
        List<PredictedHistoricAccountingFigureDto> historicAccountingFigureDtoList = new ArrayList();

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
        for (MultiPeriodAccountingFigureRequestDto figure : historicAccountingFigures) {
            PredictedHistoricAccountingFigureDto historicAccountingFigureDto = new PredictedHistoricAccountingFigureDto(figure);
            predictionService.MakePredictions(historicAccountingFigureDto, scenarioDto.getPeriods());
            historicAccountingFigureDtoList.add(historicAccountingFigureDto);
        }

        DoubleKeyValueListDto liabilities = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.Liabilities, deterministicAccountingFigures, historicAccountingFigureDtoList);
        DoubleKeyValueListDto freeCashFlows;

        if (freeCashFlowsProvided) {
            freeCashFlows = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.FreeCashFlows, deterministicAccountingFigures, historicAccountingFigureDtoList);
        } else {
            //if free cash flows are not provided, we have to calculate them
            //TODO: wenn fcf berechnet werden, sind sie ja unter umständen auch stochastisch -> varianz berechnen über varianz der anderen prognostizierten reihen!!
            freeCashFlows = getFreeCashFlows(deterministicAccountingFigures, historicAccountingFigureDtoList, scenarioDto.getBusinessTaxRate(), scenarioDto.getCorporateTaxRate(), scenarioDto.getSolidaryTaxRate());
        }

        //calculate the  flow to equity that is needed for the fte method
        List<Double> ftes = accountingService.calculateFlowToEquity(freeCashFlows.getKeyList(), liabilities.getKeyList(), scenarioDto.getInterestOnLiabilitiesRate(), effectiveTaxRate);

        //perform valuation calculations with the different company calculation methods
        //TODO: implement StdErrors calculation for apvRes
        apvRes = companyValuationService.performApvCompanyValuation(freeCashFlows, liabilities, scenarioDto.getEquityInterestRate(), scenarioDto.getInterestOnLiabilitiesRate(), effectiveTaxRate);

        fteRes = companyValuationService.performFteCompanyValuationResult(ftes, liabilities.getKeyList(), scenarioDto.getEquityInterestRate(), scenarioDto.getInterestOnLiabilitiesRate(), effectiveTaxRate);

        fcfRes = companyValuationService.performFcfCompanyValuationResult(freeCashFlows.getKeyList(), liabilities.getKeyList(), scenarioDto.getEquityInterestRate(), scenarioDto.getInterestOnLiabilitiesRate(), effectiveTaxRate);

        if (isValuationStochastic) {
            //calculate the distribution of the company values
            //TODO: distribution nicht nur für apv
            try {
                CompanyValueDistributionDto companyValueDistribution = companyValuationService.getCompanyValueDistribution(apvRes.getCompanyValue(), apvRes.getVariance());
                scenarioDao.setCompanyValueDistributionPoints(CompanyValueDistributionMapper.mapDtoToDao(companyValueDistribution));
            } catch (NullPointerException npe) {
                System.out.println("No Distribution can be calculated!");
            }
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
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Exception while trying to update the scenario. Therefore, old one has not been deleted.");
        }

    }

    //TODO: calculate fcfStdErrors if stochastic pnl figures are used for fcf calculation
    private DoubleKeyValueListDto getFreeCashFlows(
            //this provides free cash flows by using the accounting service
            HashMap<MultiPeriodAccountingFigureNames, List<Double>> deterministicAccountingFigures,
            List<PredictedHistoricAccountingFigureDto> historicAccountingFigureDtoList,
            Double businessTaxRate,
            Double corporateTaxRate,
            Double solidaryTaxRate) {

        DoubleKeyValueListDto revenues = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.Revenue, deterministicAccountingFigures, historicAccountingFigureDtoList);
        DoubleKeyValueListDto additionalIncomes = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.AdditionalIncome, deterministicAccountingFigures, historicAccountingFigureDtoList);
        DoubleKeyValueListDto costOfMaterials = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.CostOfMaterial, deterministicAccountingFigures, historicAccountingFigureDtoList);
        DoubleKeyValueListDto costOfStaffs = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.CostOfStaff, deterministicAccountingFigures, historicAccountingFigureDtoList);
        DoubleKeyValueListDto additionalCostss = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.AdditionalCosts, deterministicAccountingFigures, historicAccountingFigureDtoList);
        DoubleKeyValueListDto depreciations = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.Depreciation, deterministicAccountingFigures, historicAccountingFigureDtoList);
        DoubleKeyValueListDto investments = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.Investments, deterministicAccountingFigures, historicAccountingFigureDtoList);
        DoubleKeyValueListDto divestments = getDeterministicOrStochasticAccountingFigure(MultiPeriodAccountingFigureNames.Divestments, deterministicAccountingFigures, historicAccountingFigureDtoList);

        DoubleKeyValueListDto freeCashFlows = accountingService.calculateFreeCashFlow(
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

    private DoubleKeyValueListDto  getDeterministicOrStochasticAccountingFigure(
            MultiPeriodAccountingFigureNames figureName,
            HashMap<MultiPeriodAccountingFigureNames, List<Double>> deterministicAccountingFigures,
            List<PredictedHistoricAccountingFigureDto> historicAccountingFigureDtoList
    ) {

        if (deterministicAccountingFigures.containsKey(figureName)) {
            DoubleKeyValueListDto list = new DoubleKeyValueListDto();
            List<Double> ts = deterministicAccountingFigures.get(figureName);
            for(Double d :  ts) {
                list.add(new AbstractMap.SimpleEntry<>(d, 0.0));
            }
            return list;
        } else {
            DoubleKeyValueListDto ts = null;
            for (PredictedHistoricAccountingFigureDto phaf : historicAccountingFigureDtoList) {
                if (phaf.getMultiPeriodAccountingFigureRequestDto().getFigureName() == figureName) {
                    ts = phaf.getFcastWithStdError();
                }
            }
            return ts;
        }

    }
}
