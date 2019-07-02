package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Time series item: Response ", description="Item einer Zahlenreihe")
public class TimeSeriesItemResponseDto {
	private TimeSeriesItemDateResponseDto date;
	private Double amount;
	
	public TimeSeriesItemResponseDto() {
	}
	
	public TimeSeriesItemResponseDto(TimeSeriesItemDateResponseDto date, Double amount) {
		this.date = date;
		this.amount = amount;
	}
	
	public Double getAmount() { return amount; }
	public void setAmount(Double amount) { this.amount = amount; }
	
	public TimeSeriesItemDateResponseDto getDate() { return date; }
	public void setDate(TimeSeriesItemDateResponseDto date) { this.date = date; }
}
