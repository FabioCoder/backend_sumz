package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import edu.dhbw.ka.mwi.businesshorizon2.comparators.TimeSeriesItemByDateComparator;
import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;
import edu.dhbw.ka.mwi.businesshorizon2.models.common.TimeSeriesItemDateFormats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author DHBW KA WWI
 */

@ApiModel(value="Multiperiod accounting figure: Request", description="Das Model repräsentiert eine Zahlenreihe eines Szenarios.")
public class MultiPeriodAccountingFigureRequestDto {
	
    @NotNull(message = "Der Wert isHistoric darf nicht Null sein.")
    private Boolean isHistoric;

    @NotNull(message = "Der Wert Timeseries darf nicht null sein.")
    @Valid
    private List<TimeSeriesItemRequestDto> timeSeries;

    private MultiPeriodAccountingFigureNames figureName;

    //Ordnung für die Schätzung der Zeitreihe
    @Valid
    private Integer[] order;
    @Valid
    private Integer[] seasonalOrder;
    
    private Double score;

    /**
     *
     */
    public MultiPeriodAccountingFigureRequestDto() {
    }

    /**
     *
     * @param figureName
     * @param isHistoric
     * @param timeSeries
     * @param order
     */
    public MultiPeriodAccountingFigureRequestDto(MultiPeriodAccountingFigureNames figureName,
                    Boolean isHistoric, List<TimeSeriesItemRequestDto> timeSeries, Integer[] order, Integer[] seasonalOrder) {
        this.figureName = figureName;
        this.isHistoric = isHistoric;
        this.timeSeries = timeSeries;
        this.order = order;
        this.seasonalOrder = seasonalOrder;
    }

    public MultiPeriodAccountingFigureRequestDto(MultiPeriodAccountingFigureNames figureName,
                    Boolean isHistoric, List<TimeSeriesItemRequestDto> timeSeries) {
        this.figureName = figureName;
        this.isHistoric = isHistoric;
        this.timeSeries = timeSeries;
    }

    @ApiModelProperty(value = "Score der Prognose (wird bei einer Anfrage nicht berücksichtigt)", allowableValues = "null")
    public Double getScore() {
        if(this.score != null)
            return round(score,2);
        else return null;
    }
    
    private double round(double value, int decimalPoints) {
        double d = Math.pow(10, decimalPoints);
        return Math.round(value * d) / d;
     }

    public void setScore(Double score) {
        this.score = score;
    }

    /**
     *
     * @return order
     */
    @ApiModelProperty(value = "Order mit der die Prognose durchgeführt werden soll, Werte: [p,d,q], null")
    public Integer[] getOrder() {
        return order;
    }

    /**
     *
     * @param order
     */
    public void setOrder(Integer[] order) {
        this.order = order;
    }

    @ApiModelProperty(value = "Saisonale Order mit der die Prognose durchgeführt werden soll, Werte: [p,d,q,f], null")
    public Integer[] getSeasonalOrder() {
        return seasonalOrder;
    }

    public void setSeasonalOrder(Integer[] seasonalOrder) {
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
    public List<TimeSeriesItemRequestDto> getTimeSeries() {
        return timeSeries;
    }

    /**
     *
     * @param timeSeries
     */
    public void setTimeSeries(List<TimeSeriesItemRequestDto> timeSeries) {
        this.timeSeries = timeSeries;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "Name der Zahlungsreihe")
    public MultiPeriodAccountingFigureNames getFigureName() {
        return figureName;
    }

    /**
     *
     * @param figureName
     */
    public void setFigureName(MultiPeriodAccountingFigureNames figureName) {
        this.figureName = figureName;
    }

    /**
     *
     * @return
     */
    public TimeSeriesItemDateRequestDto getMaxDate() {
        if (this.timeSeries == null || this.timeSeries.isEmpty()) {
            return null;
        }

        List<TimeSeriesItemDateRequestDto> dates = new ArrayList<TimeSeriesItemDateRequestDto>();
        for (TimeSeriesItemRequestDto item : this.timeSeries) {
            if (item.getDate() != null && item.getDate().getDateFormat() != TimeSeriesItemDateFormats.Invalid) {
                dates.add(item.getDate());
            }
        }

        if (dates.isEmpty()) {
            return null;
        }

        return Collections.max(dates);
    }

    /**
     *
     * @return
     */
    public TimeSeriesItemDateRequestDto getMinDate() {
        if (this.timeSeries == null || this.timeSeries.isEmpty()) {
            return null;
        }

        List<TimeSeriesItemDateRequestDto> dates = new ArrayList<TimeSeriesItemDateRequestDto>();
        for (TimeSeriesItemRequestDto item : this.timeSeries) {
            if (item.getDate() != null && item.getDate().getDateFormat() != TimeSeriesItemDateFormats.Invalid) {
                dates.add(item.getDate());
            }
        }

        if (dates.isEmpty()) {
            return null;
        }

        return Collections.min(dates);
    }

    /**
     *
     * @return
     */
    //this method is to check whether the time series is consistent
    //TOCHECK: might want to rewrite the entire way of building andh andling time series, e.g. there's more potential for errors than using a class that contains start timeSeriesItem, int for frequency
    public boolean isTimeSeriesContinuous() {
        if (this.timeSeries == null) {
            throw new UnsupportedOperationException();
        }

        List<TimeSeriesItemDateRequestDto> dates = new ArrayList<TimeSeriesItemDateRequestDto>();
        for (int i = 0; i < this.timeSeries.size(); i++) {
            if (this.timeSeries.get(i).getDate() != null && this.timeSeries.get(i).getDate().getDateFormat() != TimeSeriesItemDateFormats.Invalid) {
                dates.add(this.timeSeries.get(i).getDate());
            }
        }
        dates.sort(null);

        for (int i = 0; i < dates.size() - 1; i++) {
            TimeSeriesItemDateRequestDto expectedNext = dates.get(i).getNextDate();
            TimeSeriesItemDateRequestDto actualNext = dates.get(i + 1);

            if (!expectedNext.equals(actualNext)) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @return
     */
    public List<Double> getTimeSeriesAmountsSortedAscByDate() {
        if (this.timeSeries == null) {
            throw new UnsupportedOperationException();
        }

        this.timeSeries.sort(new TimeSeriesItemByDateComparator());

        List<Double> amounts = new ArrayList<Double>();

        for (int i = 0; i < this.timeSeries.size(); i++) {
            if (this.timeSeries.get(i).getAmount() != null) {
                amounts.add(this.timeSeries.get(i).getAmount());
            }
        }

        return amounts;
    }

    @ApiModelProperty(value = "Frequenz der Zahlenreihe (vierteljährlich: 4, jährlich: 1)")
    public Integer getFrequency() {
        switch (this.timeSeries.get(0).getDate().getDateFormat()) {
            case Year:
                return 1;
            case YearQuarter:
                return 4;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        String newLine = System.getProperty("line.separator");

        StringBuilder sb = new StringBuilder();
        sb.append(newLine);
        sb.append("\tHistoric: ");
        sb.append(this.isHistoric != null ? this.isHistoric : "");
        sb.append(",");
        sb.append(newLine);
        sb.append("\tFigureName: ");
        sb.append(this.figureName);
        sb.append(",");
        sb.append(newLine);
        sb.append("\tTime Series: [");
        sb.append(newLine);

        for (int i = 0; i < this.timeSeries.size() - 1; i++) {
            sb.append("\t\t");
            sb.append(this.timeSeries.get(i).toString());
            sb.append(newLine);
        }

        sb.append("\t\t");
        sb.append(this.timeSeries.get(this.timeSeries.size() - 1));
        sb.append(newLine);
        sb.append("\t");
        sb.append("]");

        return sb.toString();
    }
}
