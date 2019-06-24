package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces;


import org.springframework.stereotype.Service;

import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.PredictedHistoricAccountingFigureDto;

/**
 *
 * @author DHBW KA WWI
 */
@Service
public interface ITimeSeriesPredictionService {

    /**
     *
     * @param figure
     * @param periods
     */
    public void MakePredictions(
            PredictedHistoricAccountingFigureDto figure,
            Integer periods);
}
