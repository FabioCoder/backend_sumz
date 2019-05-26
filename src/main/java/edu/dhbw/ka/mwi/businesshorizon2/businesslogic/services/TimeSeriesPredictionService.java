package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.ITimeSeriesPredictionService;
import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.MultiPeriodAccountingFigureRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.PredictionRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.PredictionRequestTimeSeriesDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.PredictionResponseDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.PredictionResponseTimeSeriesDto;

//this service sends a request to the python backend to receive a prediction for one or more time series
//you could probably get rid of a lot of classes by just allowing predictions for one time series per request

/**
 *
 * @author WWI DHBW KA
 */
@Service
public class TimeSeriesPredictionService implements ITimeSeriesPredictionService{
	
        //bad style: hardcoded endpoint
	final String uri = "http://sumz1718.dh-karlsruhe.de:5000/predict";
	
    /**
     *
     * @param historicAccountingFigures
     * @param stochasticAccountingFigures
     * @param periods
     * @param numSamples
     */
    @Override
	public void MakePredictions(
			List<MultiPeriodAccountingFigureRequestDto> historicAccountingFigures, 
			HashMap<MultiPeriodAccountingFigureNames, HashMap<Integer, List<Double>>> stochasticAccountingFigures,
			Integer periods,
			Integer numSamples) {
		
                //create a list of time series
		List<PredictionRequestTimeSeriesDto> timeSeries = new ArrayList<PredictionRequestTimeSeriesDto>();
		Double[] amountsArr = new Double[historicAccountingFigures.size()];
		
                //add each time series to the list of time series
		for (MultiPeriodAccountingFigureRequestDto figure : historicAccountingFigures) {
			timeSeries.add(new PredictionRequestTimeSeriesDto(figure.getFigureName(), figure.getTimeSeriesAmountsSortedAscByDate().toArray(amountsArr)));
		}
		
                //create a request with  the help of the PredictionRequestDto class
		PredictionRequestDto request = new PredictionRequestDto(timeSeries, periods, numSamples);
		
		System.out.println("-----------------------REQUEST-----------------------");
		System.out.println(request);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<PredictionRequestDto> x = new HttpEntity<>(request);
		
                //receive the result
		PredictionResponseDto result = restTemplate.postForObject(uri, x, PredictionResponseDto.class);
			
		System.out.println("-----------------------RESPONSE-----------------------");
		System.out.println(result);
		
		for (PredictionResponseTimeSeriesDto ts : result.getTimeSeries()) {
			MultiPeriodAccountingFigureNames name = MultiPeriodAccountingFigureNames.valueOf(ts.getId());		
			stochasticAccountingFigures.put(name, new HashMap<Integer, List<Double>>());
			
                        //write the predictions in the delivered stochasticAccountingFigures object
			for (int i = 0; i < numSamples; i++) {
				stochasticAccountingFigures.get(name).put(i + 1, Arrays.asList(ts.getPreds()[i]));
			}				
		}
		
		for (MultiPeriodAccountingFigureNames key : stochasticAccountingFigures.keySet()) {
			System.out.println(stochasticAccountingFigures.get(key));
		}
	}
}
