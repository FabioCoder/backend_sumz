package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;

/**
 *
 * @author DHBW KA WWI
 */
//this class represents a single time series object with forecasted values used by the PredictionResponseDto class
public class PredictionResponseTimeSeriesDto {

    private MultiPeriodAccountingFigureNames id;
    private Double[] preds;

    /**
     *
     * @return
     */
    public MultiPeriodAccountingFigureNames getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(MultiPeriodAccountingFigureNames id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Double[] getPreds() {
        return preds;
    }

    /**
     *
     * @param preds
     */
    public void setPreds(Double[] preds) {
        this.preds = preds;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.preds != null) {
            sb.append("\"values\": [");
            for (int i = 0; i < this.preds.length; i++) {
                sb.append(this.preds[i]);
                if(this.preds.length-i > 1)
                    sb.append(", ");
            }
            sb.append("]");
        }

        return sb.toString();
    }
}
