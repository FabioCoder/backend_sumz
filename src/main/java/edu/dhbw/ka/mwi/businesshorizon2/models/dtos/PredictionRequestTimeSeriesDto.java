package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;

//this class represents a time series that is identified by an id and contains an array of values
public class PredictionRequestTimeSeriesDto {
	private String id;
	private Double[] values;
	
	public PredictionRequestTimeSeriesDto() {
	}
	
	public PredictionRequestTimeSeriesDto(MultiPeriodAccountingFigureNames id, Double[] values) {
		this.id = id.toString();
		this.values = values;
	}
	
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	
	public Double[] getValues() { return values; }
	public void setValues(Double[] values) { this.values = values; }
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Id: ");
		sb.append(this.id != null ? this.id : "");
		sb.append(", \t");
		sb.append("[");
		if(this.values != null) {
			for (int i = 0; i < this.values.length; i++) {
				sb.append(this.values[i]);
				sb.append(", ");
			}
		}
		
		sb.append("]");
		
		return sb.toString();
	}
}
