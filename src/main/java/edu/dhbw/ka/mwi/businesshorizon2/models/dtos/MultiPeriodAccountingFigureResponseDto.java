package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DHBW WWI SUMZ
 */
public class MultiPeriodAccountingFigureResponseDto {

    private Boolean isHistoric;
    private List<TimeSeriesItemResponseDto> timeSeries = new ArrayList<>();
    private Integer[] order;
    private Integer[] seasonalOrder;
    private Double score;

    /**
     *
     */
    public MultiPeriodAccountingFigureResponseDto() {
    }

    /**
     *
     * @param isHistoric
     * @param timeSeries
     * @param order
     * @param seasonalOrder
     */
    public MultiPeriodAccountingFigureResponseDto(Boolean isHistoric, List<TimeSeriesItemResponseDto> timeSeries, Integer[] order, Integer[] seasonalOrder) {
        this.isHistoric = isHistoric;
        this.timeSeries = timeSeries;
        this.order = order;
        this.seasonalOrder = seasonalOrder;
    }

    /**
     *
     * @return
     */
    public Boolean getIsHistoric() {
        return isHistoric;
    }

    /**
     *
     * @param isHistoric
     */
    public void setIsHistoric(Boolean isHistoric) {
        this.isHistoric = isHistoric;
    }

    /**
     *
     * @return
     */
    public List<TimeSeriesItemResponseDto> getTimeSeries() {
        return timeSeries;
    }

    /**
     *
     * @param timeSeries
     */
    public void setTimeSeries(List<TimeSeriesItemResponseDto> timeSeries) {
        this.timeSeries = timeSeries;
    }

    public Integer[] getOrder() {
        return order;
    }

    public void setOrder(Integer[] order) {
        this.order = order;
    }

    public Integer[] getSeasonalOrder() {
        return seasonalOrder;
    }

    public void setSeasonalOrder(Integer[] seasonalOrder) {
        this.seasonalOrder = seasonalOrder;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
