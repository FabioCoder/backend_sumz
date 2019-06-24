/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DHBW KA WWI
 */
public class PredictedHistoricAccountingFigureDto {

    //this includes the historic time series
    private MultiPeriodAccountingFigureRequestDto multiPeriodAccountingFigureRequestDto;
    private DoubleKeyValueListDto fcastWithStdError;

    public PredictedHistoricAccountingFigureDto(MultiPeriodAccountingFigureRequestDto multiPeriodAccountingFigureRequestDto) {
        this.multiPeriodAccountingFigureRequestDto = multiPeriodAccountingFigureRequestDto;
    }

    public MultiPeriodAccountingFigureRequestDto getMultiPeriodAccountingFigureRequestDto() {
        return multiPeriodAccountingFigureRequestDto;
    }

    public void setMultiPeriodAccountingFigureRequestDto(MultiPeriodAccountingFigureRequestDto multiPeriodAccountingFigureRequestDto) {
        this.multiPeriodAccountingFigureRequestDto = multiPeriodAccountingFigureRequestDto;
    }

    public DoubleKeyValueListDto getFcastWithStdError() {
        return fcastWithStdError;
    }

    public void setFcastWithStdError(DoubleKeyValueListDto fcastWithStdError) {
        this.fcastWithStdError = fcastWithStdError;
    }
    
    public List<Double> getForecasts() {
        List<Double> list = new ArrayList<>();
        for(SimpleEntry<Double,Double> entry : this.fcastWithStdError)
            list.add(entry.getKey());
        return list;
    }
    
    public List<Double> getStdErrors() {
        List<Double> list = new ArrayList<>();
        for(SimpleEntry<Double,Double> entry : this.fcastWithStdError)
            list.add(entry.getValue());
        return list;
    }
    
}
