package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
public class TimeSeriesPredictionService implements ITimeSeriesPredictionService {

    //TODO: bad style: hardcoded endpoint
    final String uri = "http://127.0.0.1:5000/predict";

    /**
     *
     * @param historicAccountingFigures
     * @param stochasticAccountingFigures
     * @param periods
     * @param numSamples
     * @param order
     * @param seasonalOrder
     */
    @Override
    public void MakePredictions(
            List<MultiPeriodAccountingFigureRequestDto> historicAccountingFigures,
            HashMap<MultiPeriodAccountingFigureNames, HashMap<Integer, List<Double>>> stochasticAccountingFigures,
            Integer periods,
            Integer numSamples, Integer[] order, Integer[] seasonalOrder) {

        //create a list of time series
        Double[] amountsArr = new Double[historicAccountingFigures.size()];

        //for each time series make an request
        for (MultiPeriodAccountingFigureRequestDto figure : historicAccountingFigures) {

            //TODO: remove amountsArr since might not be safe
            PredictionRequestTimeSeriesDto ts = new PredictionRequestTimeSeriesDto(figure.getFigureName(), figure.getTimeSeriesAmountsSortedAscByDate().toArray(amountsArr));

            //create a request with  the help of the PredictionRequestDto class
            PredictionRequestDto request;
            if(order!=null && seasonalOrder!=null)
                request = new PredictionRequestDto(ts, periods, numSamples, order, seasonalOrder);
            else
                request = new PredictionRequestDto(ts, periods, numSamples);
            
            //TODO: handle possible null pointer on request object?
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<PredictionRequestDto> x = new HttpEntity<>(request);

            System.out.println("-----------------------REQUEST-----------------------");
            System.out.println(request);

            //receive the result
            //TODO: response not checked?; restrict length of doubles; handle exception when python backend doesn't provide a forecast (error, not available etc.)
            PredictionResponseDto result = restTemplate.postForObject(uri, x, PredictionResponseDto.class);

            System.out.println("-----------------------RESPONSE-----------------------");
            System.out.println(result);

            PredictionResponseTimeSeriesDto responseTs = result.getTimeSeries();
            result.getTimeSeries().setId(figure.getFigureName());

            stochasticAccountingFigures.put(result.getTimeSeries().getId(), new HashMap<Integer, List<Double>>());

            //put the predictions into the delivered stochasticAccountingFigures object
            //TODO: check numSamples magic, currently putting i times the same timeseries in it
            for (int i = 0; i < numSamples; i++) {
                stochasticAccountingFigures.get(result.getTimeSeries().getId()).put(i + 1, Arrays.asList(responseTs.getPreds()));
            }
        }

        for (MultiPeriodAccountingFigureNames key : stochasticAccountingFigures.keySet()) {
            System.out.println(stochasticAccountingFigures.get(key));
        }
    }
}
