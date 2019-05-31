package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

public class PredictionResponseDto {
    
    //This class represents a response of the python backend containing a time series with forecasted values
    
	private PredictionResponseTimeSeriesDto timeSeries = new PredictionResponseTimeSeriesDto();

	public PredictionResponseTimeSeriesDto getTimeSeries() { return timeSeries; }
	public void setTimeSeries(PredictionResponseTimeSeriesDto timeSeries) { this.timeSeries = timeSeries; }
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		if(this.timeSeries != null) {
                    sb.append(this.timeSeries);
		}
				
		return sb.toString();
	}
}
