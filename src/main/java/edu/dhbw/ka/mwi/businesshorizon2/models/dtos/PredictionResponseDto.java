package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

public class PredictionResponseDto {

    //This class represents a response of the python backend containing a time series with forecasted values
    private Double[][] values;
    private Double score;
    private Integer[] order;
    private Integer[] seasonalOrder;

    public Double[][] getValues() {
        return values;
    }

    public void setValues(Double[][] values) {
        this.values = values;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer[] getOrder() {
        return order;
    }

    public void setOrder(Integer[] order) {
        this.order = order;
    }

    public Integer[] getSeasonalOrder() {
        return seasonalOrder;
    }

    public void setSeasonalOrder(Integer[] seasonalOrder) {
        this.seasonalOrder = seasonalOrder;
    }

    @Override
    public String toString() {
        
        String newLine = System.getProperty("line.separator");

        StringBuilder sb = new StringBuilder();
        
        if (this.values != null) {
            sb.append("values: [");
            for(Double[] d : values) {
                sb.append(newLine);
                sb.append("[");
                for(int i=0; i<d.length; i++) {
                    sb.append(d[i]+" ,");
                }
                sb.append("]");
            }
            sb.append("]");
        }

        if(this.score != null) {
            sb.append(newLine);
            sb.append("score: ");
            sb.append(this.score);
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

        return sb.toString();
    }
}
