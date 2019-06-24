package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TimeSeriesItemDateResponseDto {

	@NotNull(message="Es muss ein Jahr angegeben werden.")
	@DecimalMin(value="1900", message="Das Jahr muss zwischen 1900 und 2100 liegen.")
	@DecimalMax(value="2100", message="Das Jahr muss zwischen 1900 und 2100 liegen.")
	private Integer year;

	@Min(value=1, message="Die Angabe des Quartals muss zwischen 1 und 4 liegen.")
	@Max(value=4, message="Die Angabe des Quartals muss zwischen 1 und 4 liegen.")
	private Integer quarter;
	
	public TimeSeriesItemDateResponseDto() {
	}
	
	public TimeSeriesItemDateResponseDto(Integer year) {
		this.year = year;
	}
	
	public TimeSeriesItemDateResponseDto(Integer year, Integer quarter) {
		this.year = year;
		this.quarter = quarter;
	}
		
	public Integer getYear() { return year; }
	public void setYear(Integer year) { 
		this.year = year;
	}
	
	public Integer getQuarter() { return quarter; }
	public void setQuarter(Integer quarter) { 
		this.quarter = quarter; 
	}
}
