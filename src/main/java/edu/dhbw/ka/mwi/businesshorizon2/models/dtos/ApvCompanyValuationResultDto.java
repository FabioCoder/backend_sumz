package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

public class ApvCompanyValuationResultDto {

    private Double companyValue;
    private Double marketValueTotalAssets;
    private Double totalLiabilities;
    private Double presentValueOfCashflows;
    private Double taxShield;
    private Double variance;

    public ApvCompanyValuationResultDto(Double companyValue, Double marketValueTotalAssets, Double totalLiabilities, Double presentValueOfCashflows, Double taxShield, Double variance) {
        this.companyValue = companyValue;
        this.marketValueTotalAssets = marketValueTotalAssets;
        this.totalLiabilities = totalLiabilities;
        this.presentValueOfCashflows = presentValueOfCashflows;
        this.taxShield = taxShield;
        this.variance = variance;
    }

    public Double getCompanyValue() {
        return companyValue;
    }

    public void setCompanyValue(Double companyValue) {
        this.companyValue = companyValue;
    }

    public Double getMarketValueTotalAssets() {
        return marketValueTotalAssets;
    }

    public void setMarketValueTotalAssets(Double marketValueTotalAssets) {
        this.marketValueTotalAssets = marketValueTotalAssets;
    }

    public Double getPresentValueOfCashflows() {
        return presentValueOfCashflows;
    }

    public void setPresentValueOfCashflows(Double presentValueOfCashflows) {
        this.presentValueOfCashflows = presentValueOfCashflows;
    }

    public Double getTotalLiabilities() {
        return totalLiabilities;
    }

    public void setTotalLiabilities(Double totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public Double getTaxShield() {
        return taxShield;
    }

    public void setTaxShield(Double taxShield) {
        this.taxShield = taxShield;
    }

    public Double getVariance() {
        return variance;
    }

    public void setVariance(Double variance) {
        this.variance = variance;
    }

}
