package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author DHBW WWI SUMZ
 */
@ApiModel(value="Multiperiod accounting figure: Response", description="Das Model repräsentiert eine Zahlenreihe eines Szenarios.")
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
    @ApiModelProperty(value = "Wurden Vergangenheitswerte (-> Prognose) oder Zukunftswerte eingegeben?", allowableValues = "true, false")
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
    @ApiModelProperty(value = "Zahlenreihe")
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
    
    @ApiModelProperty(value = "Order mit der die Prognose durchgeführt wurde", allowableValues = "[p,d,q], null")
    public Integer[] getOrder() {
        return order;
    }

    public void setOrder(Integer[] order) {
        this.order = order;
    }

    @ApiModelProperty(value = "Saisonale Order mit der die Prognose durchgeführt wurde", allowableValues = "[p,d,q], null")
    public Integer[] getSeasonalOrder() {
        return seasonalOrder;
    }

    public void setSeasonalOrder(Integer[] seasonalOrder) {
        this.seasonalOrder = seasonalOrder;
    }

    @ApiModelProperty(value = "Score der Prognose")
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
