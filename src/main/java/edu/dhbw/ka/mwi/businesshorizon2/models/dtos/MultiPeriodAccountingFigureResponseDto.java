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
	
    /**
     *
     */
    public MultiPeriodAccountingFigureResponseDto() {}
	
    /**
     *
     * @param isHistoric
     * @param timeSeries
     */
    public MultiPeriodAccountingFigureResponseDto(Boolean isHistoric, List<TimeSeriesItemResponseDto> timeSeries) {
		this.isHistoric = isHistoric;
		this.timeSeries = timeSeries;
	}
	
    /**
     *
     * @return
     */
    public Boolean getIsHistoric() { return isHistoric; }

    /**
     *
     * @param isHistoric
     */
    public void setIsHistoric(Boolean isHistoric) { this.isHistoric = isHistoric; }

    /**
     *
     * @return
     */
    public List<TimeSeriesItemResponseDto> getTimeSeries() { return timeSeries; }

    /**
     *
     * @param timeSeries
     */
    public void setTimeSeries(List<TimeSeriesItemResponseDto> timeSeries) { this.timeSeries = timeSeries; }
}
