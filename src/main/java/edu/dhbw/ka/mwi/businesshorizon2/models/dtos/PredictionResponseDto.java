package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

public class PredictionResponseDto {
    
    //This class represents a response of the python backend containing a time series with forecasted values
    
	private Double[] values;

	public Double[] getValues() {
            return values;
        }
        
        public void setValues(Double[] values) {
            this.values = values;
        }
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		if(this.values != null) {
                    sb.append(this.values);
		}
				
		return sb.toString();
	}
}
