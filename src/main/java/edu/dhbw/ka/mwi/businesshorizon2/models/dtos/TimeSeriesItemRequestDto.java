package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Time series item: Request ", description="Item einer Zahlenreihe")
public class TimeSeriesItemRequestDto{
	
	@NotNull(message="Es muss ein Datum gesetzt sein.")
	private TimeSeriesItemDateRequestDto date;
	
	@NotNull(message="Der Betrag muss gesetzt sein.")
	private Double amount;
	
	public TimeSeriesItemRequestDto() {
	}
	
	public TimeSeriesItemRequestDto(TimeSeriesItemDateRequestDto date, Double amount) {
		this.date = date;
		this.amount = amount;
	}
	
	public Double getAmount() { return amount; }
	public void setAmount(Double amount) { this.amount = amount; }
	
	public TimeSeriesItemDateRequestDto getDate() { return date; }
	public void setDate(TimeSeriesItemDateRequestDto date) { this.date = date; }
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Date: ");
		sb.append(this.date != null ? this.date : "");
		sb.append("\t");
		sb.append("Amount: ");
		sb.append(this.amount != null ? this.amount : "");
		
		return sb.toString();
	}
}
