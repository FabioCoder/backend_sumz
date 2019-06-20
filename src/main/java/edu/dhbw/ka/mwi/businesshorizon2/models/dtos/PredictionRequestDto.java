package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

//this class helps to easily send a request for prediction to the python backend; it represents the request sent to the python backend containing a time series, amount of prediction steps and numSamples
/**
 *
 * @author DHBW KA WWI
 */
public class PredictionRequestDto {

    private PredictionRequestTimeSeriesDto timeSeries;
    private Integer predSteps;
    private Integer numSamples;
    private Integer[] order;
    private Integer[] seasonalOrder;

    /**
     *
     */
    public PredictionRequestDto() {
    }

    /**
     *
     * @param timeSeries
     * @param predSteps
     * @param numSamples
     */
    public PredictionRequestDto(PredictionRequestTimeSeriesDto timeSeries, Integer predSteps, Integer numSamples) {
        this.timeSeries = timeSeries;
        this.predSteps = predSteps;
        this.numSamples = numSamples;
    }

    public PredictionRequestDto(PredictionRequestTimeSeriesDto timeSeries, Integer predSteps, Integer numSamples, Integer[] order, Integer[] seasonalOrder) {
        this.timeSeries = timeSeries;
        this.predSteps = predSteps;
        this.numSamples = numSamples;
        this.order = order;
        this.seasonalOrder = seasonalOrder;
    }

    /**
     *
     * @return
     */
    public PredictionRequestTimeSeriesDto getTimeSeries() {
        return timeSeries;
    }

    /**
     *
     * @param timeSeries
     */
    public void setTimeSeries(PredictionRequestTimeSeriesDto timeSeries) {
        this.timeSeries = timeSeries;
    }

    /**
     *
     * @return
     */
    public Integer getPredSteps() {
        return predSteps;
    }

    /**
     *
     * @param predSteps
     */
    public void setPredSteps(Integer predSteps) {
        this.predSteps = predSteps;
    }

    /**
     *
     * @return
     */
    public Integer getNumSamples() {
        return numSamples;
    }

    /**
     *
     * @param numSamples
     */
    public void setNumSamples(Integer numSamples) {
        this.numSamples = numSamples;
    }

    @Override
    public String toString() {

        String newLine = System.getProperty("line.separator");

        StringBuilder sb = new StringBuilder();

        if(this.predSteps != null) {
            sb.append("\"predSteps\": ");
            sb.append(this.predSteps);
        }

        if (this.order != null) {
            sb.append(", ");
            sb.append(newLine);
            sb.append("\"order\": [");
            for (int i = 0; i < this.order.length; i++) {
                sb.append(this.order[i]);
                if(this.order.length-i > 1)
                    sb.append(", ");
            }
            sb.append("]");
        }

        if (this.seasonalOrder != null) {
            sb.append(", ");
            sb.append(newLine);
            sb.append("\"seasonalOrder\": [");
            for (int i = 0; i < this.seasonalOrder.length; i++) {
                sb.append(this.seasonalOrder[i]);
                if(this.seasonalOrder.length-i > 1)
                    sb.append(", ");
            }
            sb.append("]");
        }

        if (this.timeSeries != null) {
            sb.append(", ");
            sb.append(newLine);
            sb.append(this.timeSeries);
        }
        
        return sb.toString();
    }
}
