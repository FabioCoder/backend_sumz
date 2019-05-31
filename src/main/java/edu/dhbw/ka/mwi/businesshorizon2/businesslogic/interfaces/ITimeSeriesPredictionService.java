package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.MultiPeriodAccountingFigureRequestDto;

/**
 *
 * @author DHBW KA WWI
 */
@Service
public interface ITimeSeriesPredictionService {

    /**
     *
     * @param historicAccountingFigures
     * @param stochasticAccountingFigures
     * @param periods
     * @param numSamples
     * @param order
     * @param seasonalOrder
     */
    public void MakePredictions(
			List<MultiPeriodAccountingFigureRequestDto> historicAccountingFigures, 
			HashMap<MultiPeriodAccountingFigureNames, HashMap<Integer, List<Double>>> stochasticAccountingFigures,
			Integer periods,
			Integer numSamples, Integer[] order, Integer[] seasonalOrder);
}
