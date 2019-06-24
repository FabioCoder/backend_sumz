package edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.ITimeSeriesPredictionService;
import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.PredictionRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.PredictionRequestTimeSeriesDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.PredictionResponseDto;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.PredictedHistoricAccountingFigureDto;
import java.net.ConnectException;
import java.util.AbstractMap.SimpleEntry;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

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
     * @param figure
     * @param periods
     */
    //TODO: RECEIVE ORDER FROM SCENARIO / USER
    @Override
    public void MakePredictions(
            PredictedHistoricAccountingFigureDto figure,
            Integer periods) {

        //TODO: check for is Historic
        
        Double[] values = new Double[figure.getMultiPeriodAccountingFigureRequestDto().getTimeSeriesAmountsSortedAscByDate().size()];
        values = figure.getMultiPeriodAccountingFigureRequestDto().getTimeSeriesAmountsSortedAscByDate().toArray(values);
        PredictionRequestTimeSeriesDto ts = new PredictionRequestTimeSeriesDto(figure.getMultiPeriodAccountingFigureRequestDto().getFigureName(), values);

        //create a request with  the help of the PredictionRequestDto class
        PredictionRequestDto request;
        
        //Wenn eine Ordnung angegeben ist, dann wird diese direkt an das Python Backend
        //weitergegeben. Eine Ordnung besteht aus einer normalen und einer saisonalen Ordnung.
        //Wenn keine Ordnung mitgegeben wurde, dann wird eine Ordnung vom Python-Backend geschätzt
        
        if (figure.getMultiPeriodAccountingFigureRequestDto().getOrder() != null && figure.getMultiPeriodAccountingFigureRequestDto().getSeasonalOrder() != null){
            
            Integer[] normalOrder = figure.getMultiPeriodAccountingFigureRequestDto().getOrder();
            Integer[] seasonalOrder = figure.getMultiPeriodAccountingFigureRequestDto().getSeasonalOrder();
            
            request = new PredictionRequestDto(ts, periods, normalOrder, seasonalOrder);
        }
        else {
            request = new PredictionRequestDto(ts, periods); 
        }
        
        //contacting the python backend...
        PredictionResponseDto result = sendPostRequest(request);
        
        //writing the applied order into the multiperiodaccuntingfigurerequest object (is identical if given by the user, but has to be done since user might provide an order so the python backend decides which order to apply)
        figure.getMultiPeriodAccountingFigureRequestDto().setOrder(result.getOrder());
        figure.getMultiPeriodAccountingFigureRequestDto().setSeasonalOrder(result.getSeasonalOrder());

        //processing the response...
        for(Double[] prediction : result.getValues()) {
            SimpleEntry<Double, Double> hm = new SimpleEntry<>(prediction[0], prediction[1]);
            figure.getFcastWithStdError().add(hm);
        }
        figure.setScore(result.getScore());

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
