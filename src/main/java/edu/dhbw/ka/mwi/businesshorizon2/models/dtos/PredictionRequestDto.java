package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import java.util.List;

//this class helps to easily send a request for prediction to the python backend

/**
 *
 * @author Fabian Wallisch
 */
public class PredictionRequestDto {
	private List<PredictionRequestTimeSeriesDto> timeSeries;
	private Integer predSteps;
	private Integer numSamples;
	
    /**
     *
     */
    public PredictionRequestDto() { }
	
    /**
     *
     * @param timeSeries
     * @param predSteps
     * @param numSamples
     */
    public PredictionRequestDto(List<PredictionRequestTimeSeriesDto> timeSeries, Integer predSteps, Integer numSamples) {
		this.timeSeries = timeSeries;
		this.predSteps = predSteps;
		this.numSamples = numSamples;
	}
	
    /**
     *
     * @return
     */
    public List<PredictionRequestTimeSeriesDto> getTimeSeries() {
		return timeSeries;
	}

    /**
     *
     * @param timeSeries
     */
    public void setTimeSeries(List<PredictionRequestTimeSeriesDto> timeSeries) { this.timeSeries = timeSeries; }
	
    /**
     *
     * @return
     */
    public Integer getPredSteps() { return predSteps; }

    /**
     *
     * @param predSteps
     */
    public void setPredSteps(Integer predSteps) { this.predSteps = predSteps; }
	
    /**
     *
     * @return
     */
    public Integer getNumSamples() { return numSamples; }

    /**
     *
     * @param numSamples
     */
    public void setNumSamples(Integer numSamples) { this.numSamples = numSamples; }
	
	@Override
	public String toString() {
		
		String newLine = System.getProperty("line.separator");
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("PredSteps: ");
		sb.append(this.predSteps != null ? this.predSteps : "");
		sb.append(", ");
		sb.append("NumSamples: ");
		sb.append(this.numSamples != null ? this.numSamples : "");
		sb.append(", ");
		sb.append("TimeSeries: ");
		if(this.timeSeries != null) {
			for(int i = 0; i < this.timeSeries.size(); i++) {
				sb.append(newLine);
				sb.append(this.timeSeries.get(i));
			}
		}
		
		return sb.toString();
	}
}
