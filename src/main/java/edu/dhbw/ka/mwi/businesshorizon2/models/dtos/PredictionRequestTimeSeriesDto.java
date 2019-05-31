package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;

//this class represents a time series that is identified by an id and contains an array of values
/**
 *
 * @author DHBW Karlsruhe WWI
 */
public class PredictionRequestTimeSeriesDto {

    private MultiPeriodAccountingFigureNames id;
    private Double[] values;

    /**
     *
     */
    public PredictionRequestTimeSeriesDto() {
    }

    /**
     *
     * @param id
     * @param values
     */
    public PredictionRequestTimeSeriesDto(MultiPeriodAccountingFigureNames id, Double[] values) {
        this.id = id;
        this.values = values;
    }

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
    public Double[] getValues() {
        return values;
    }

    /**
     *
     * @param values
     */
    public void setValues(Double[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.values != null) {
            sb.append("\"values\": [");
            for (int i = 0; i < this.values.length; i++) {
                sb.append(this.values[i]);
                if(this.values.length-i > 1)
                    sb.append(", ");
            }
            sb.append("]");
        }

        return sb.toString();
    }
}
