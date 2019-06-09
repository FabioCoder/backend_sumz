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
        
        int numSamples = 1;
        int predSteps = 5;
        double[] expecteds = {40.2823,42.00513,38.5594,40.2823,40.185};
        
        List<MultiPeriodAccountingFigureRequestDto> historicAccountingFigures = new ArrayList();
        List<TimeSeriesItemRequestDto> timeSeries = new ArrayList();
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2012,1), 30.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2012,2), 40.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2012,3), 35.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2012,4), 45.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2013,1), 40.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2013,2), 35.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2013,3), 45.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2013,4), 40.0));
        historicAccountingFigures.add(new MultiPeriodAccountingFigureRequestDto(MultiPeriodAccountingFigureNames.FreeCashFlows, true, timeSeries));
        
        HashMap<MultiPeriodAccountingFigureNames, HashMap<Integer, List<Double>>> stochasticAccountingFigures = new HashMap();
        
        predictionService.MakePredictions(historicAccountingFigures, stochasticAccountingFigures, predSteps, numSamples);
        
        for(int i=0; i<numSamples; i++) {
            List<Double> predictions = stochasticAccountingFigures.get(MultiPeriodAccountingFigureNames.FreeCashFlows).get(1);
            
            double[] preds = new double[predictions.size()];
            for (int j = 0; j < preds.length; j++) {
                preds[j] = predictions.get(j);
            }
            
            Assert.assertArrayEquals(expecteds, preds, 0.1);
        }
        
    }
    
    @Test
    public void performTimeSeriesPrediction_NonBrownRozeff() {
        
        int numSamples = 1;
        int predSteps = 5;
        //TODO: adjust expecteds
        double[] expecteds = {40.2823,42.00513,38.5594,40.2823,40.185};
        
        List<MultiPeriodAccountingFigureRequestDto> historicAccountingFigures = new ArrayList();
        List<TimeSeriesItemRequestDto> timeSeries = new ArrayList();
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2012), 30.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2013), 40.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2014), 35.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2015), 45.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2016), 40.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2017), 35.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2018), 45.0));
        timeSeries.add(new TimeSeriesItemRequestDto(new TimeSeriesItemDateRequestDto(2019), 40.0));
        historicAccountingFigures.add(new MultiPeriodAccountingFigureRequestDto(MultiPeriodAccountingFigureNames.FreeCashFlows, true, timeSeries));
        
        HashMap<MultiPeriodAccountingFigureNames, HashMap<Integer, List<Double>>> stochasticAccountingFigures = new HashMap();
        
        predictionService.MakePredictions(historicAccountingFigures, stochasticAccountingFigures, predSteps, numSamples);
        
        for(int i=0; i<numSamples; i++) {
            List<Double> predictions = stochasticAccountingFigures.get(MultiPeriodAccountingFigureNames.FreeCashFlows).get(1);
            
            double[] preds = new double[predictions.size()];
            for (int j = 0; j < preds.length; j++) {
                preds[j] = predictions.get(j);
            }
            
            Assert.assertArrayEquals(expecteds, preds, 0.1);
        }
        
    }
    
}
