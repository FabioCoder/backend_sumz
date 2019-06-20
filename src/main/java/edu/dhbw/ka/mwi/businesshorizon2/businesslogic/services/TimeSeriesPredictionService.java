package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

//this service sends a request to the python backend to receive a prediction for one or more time series
//you could probably get rid of a lot of classes by just allowing predictions for one time series per request
/**
 *
 * @author WWI DHBW KA
 */
@Service
public class TimeSeriesPredictionService implements ITimeSeriesPredictionService {

    @Value("${prediction.backend.host}")
    private String uri;

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
        Double[] amountsArr = new Double[historicAccountingFigures.size()];

        //for each time series make an request
        for (MultiPeriodAccountingFigureRequestDto figure : historicAccountingFigures) {

            //TODO: remove amountsArr since might not be safe
            PredictionRequestTimeSeriesDto ts = new PredictionRequestTimeSeriesDto(figure.getFigureName(), figure.getTimeSeriesAmountsSortedAscByDate().toArray(amountsArr));

            //create a request with  the help of the PredictionRequestDto class
            //if fcf provided and frequency is quarterly, brown rozeff is applied; otherwise: best possible model
            PredictionRequestDto request;
            if (figure.getFigureName() == MultiPeriodAccountingFigureNames.FreeCashFlows && figure.getFrequency() == 4) {
                System.out.println("Applying brown rozeff...");
                Integer[] order = {1, 0, 0};
                Integer[] seasonalOrder = {0, 1, 1, 4};
                request = new PredictionRequestDto(ts, periods, numSamples, order, seasonalOrder);
            } else {
                request = new PredictionRequestDto(ts, periods, numSamples);
            }

            //contacting the python backend...
            PredictionResponseDto result = sendPostRequest(request);

            //receiving the predicted time series...
            PredictionResponseTimeSeriesDto responseTs = new PredictionResponseTimeSeriesDto(figure.getFigureName(), result.getValues());

            stochasticAccountingFigures.put(responseTs.getId(), new HashMap<Integer, List<Double>>());

            //put the predictions into the delivered stochasticAccountingFigures object
            //TODO: check numSamples magic, currently putting i times the same timeseries in it
            for (int i = 0; i < numSamples; i++) {
                stochasticAccountingFigures.get(responseTs.getId()).put(i + 1, Arrays.asList(responseTs.getPreds()));
            }
        }

        for (MultiPeriodAccountingFigureNames key : stochasticAccountingFigures.keySet()) {
            System.out.println(stochasticAccountingFigures.get(key));
        }
    }

    private PredictionResponseDto sendPostRequest(PredictionRequestDto predictionRequestDto) {
        Gson gson = new Gson();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(uri);
        StringEntity postingString;
        PredictionResponseDto predictionResponseDto;
        ObjectMapper mapper = new ObjectMapper();

        try {
            //postingString = new StringEntity(gson.toJson(predictionRequestDto)); -> TODO: doesn't work because predictionrequestdto class is not yet reworked
            postingString = new StringEntity("{" + predictionRequestDto.toString() + "}");
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");

            System.out.println("---REQUEST---");
            System.out.println("{" + predictionRequestDto.toString() + "}");

            //execute the request and receive the httpResponse
            HttpResponse response = httpClient.execute(post);

            //handling http error codes
            switch (response.getStatusLine().getStatusCode()) {
                default:
                    break;
                case 404:
                    throw new RuntimeException("Bad Request - request is not valid");
                case 500:
                    throw new RuntimeException("Internal Server Error from Python Backend durin modeling process - use more observations");
            }

            //Todo: validate response against schema
            
            //get the predicted time series out of the response
            predictionResponseDto = mapper.readValue(response.getEntity().getContent(), PredictionResponseDto.class);

            //return the time series
            return predictionResponseDto;

        } catch (ConnectException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Python Backend is not available.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Undefined exception while trying to get a prediction from the python backend: " + ex.getMessage());
        }

    }

}
