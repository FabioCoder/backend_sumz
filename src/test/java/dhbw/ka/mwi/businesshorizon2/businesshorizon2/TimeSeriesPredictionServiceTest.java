/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw.ka.mwi.businesshorizon2.businesshorizon2;

import edu.dhbw.ka.mwi.businesshorizon2.App;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services.TimeSeriesPredictionService;
import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.MultiPeriodAccountingFigureRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.PredictedHistoricAccountingFigureDto;
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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TimeSeriesPredictionServiceTest {

    @Autowired
    TimeSeriesPredictionService predictionService;

    @Test
    public void performTimeSeriesPrediction_BrownRozeff() {

        int predSteps = 5;
        Integer[] order = {0, 1, 1};
        Integer[] seasonalOrder = {1, 0, 0};
        double[] expecteds = {40.56603766277165,
            41.1400852914352,
            41.72225623454996,
            42.31266544466408,
            42.91142950101249};

        List<TimeSeriesItemRequestDto> timeSeries = new ArrayList();
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2012, 1), 30.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2012, 2), 40.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2012, 3), 35.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2012, 4), 45.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2013, 1), 40.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2013, 2), 35.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2013, 3), 45.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2013, 4), 40.0));

        PredictedHistoricAccountingFigureDto phaf = new PredictedHistoricAccountingFigureDto(new MultiPeriodAccountingFigureRequestDto(MultiPeriodAccountingFigureNames.FreeCashFlows, true, timeSeries, order, seasonalOrder));

        predictionService.MakePredictions(phaf, predSteps);

        List<Double> predictions = phaf.getForecasts();

        double[] preds = new double[predictions.size()];
        for (int j = 0; j < preds.length; j++) {
            preds[j] = predictions.get(j);
        }

        Assert.assertArrayEquals(expecteds, preds, 0.1);

    }

    @Test
    public void performTimeSeriesPrediction_NonBrownRozeff() {

        int predSteps = 5;
        //TODO: adjust expecteds
        double[] expecteds = {40.56603766277165,
            41.1400852914352,
            41.72225623454996,
            42.31266544466408,
            42.91142950101249};

        List<TimeSeriesItemRequestDto> timeSeries = new ArrayList();
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2012), 30.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2013), 40.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014), 35.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015), 45.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016), 40.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2017), 35.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2018), 45.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2019), 40.0));

        PredictedHistoricAccountingFigureDto phaf = new PredictedHistoricAccountingFigureDto(new MultiPeriodAccountingFigureRequestDto(MultiPeriodAccountingFigureNames.FreeCashFlows, true, timeSeries));

        predictionService.MakePredictions(phaf, predSteps);

        List<Double> predictions = phaf.getForecasts();

        double[] preds = new double[predictions.size()];
        for (int j = 0; j < preds.length; j++) {
            preds[j] = predictions.get(j);
        }

        Assert.assertArrayEquals(expecteds, preds, 0.1);

    }

}
