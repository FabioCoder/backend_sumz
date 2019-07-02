package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.dhbw.ka.mwi.businesshorizon2.models.common.MultiPeriodAccountingFigureNames;
import edu.dhbw.ka.mwi.businesshorizon2.validators.IsContinuousTimeSeriesPutRequest;
import edu.dhbw.ka.mwi.businesshorizon2.validators.IsDateFormatConsistentPutRequest;
import edu.dhbw.ka.mwi.businesshorizon2.validators.IsValidAccountingFigureCombinationPutRequest;
import edu.dhbw.ka.mwi.businesshorizon2.validators.IsValidTimeSeriesRangesPutRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@IsValidAccountingFigureCombinationPutRequest()
@IsDateFormatConsistentPutRequest()
@IsContinuousTimeSeriesPutRequest()
@IsValidTimeSeriesRangesPutRequest()

@ApiModel(value="Scenario Put: Request ", description="Respräsentiert eine Put-Anfrage für die Änderung an einem Szenario")
public class ScenarioPutRequestDto {
	
	@NotNull(message="Die Id des Szenarios muss gesetzt werden.")
	private Long id;
	
	@NotNull(message="Es muss angegeben werden, ob die Bewertung storastisch ist oder nicht")
	private Boolean stochastic;
	
	@NotNull(message = "Das Szenario muss einen Namen haben.")
	@Size(min=1, max=20, message="Die Länge des Namen des Szenarios muss zwischen 1 und 20 Zeichen liegen.")
	private String scenarioName;

	@NotNull(message = "Das Szenario muss eine Beschreibung haben.")
	@Size(min = 1, max = 100, message = "Die Länge der Beschreibung des Szenarios muss zwischen 1 und 100 Zeichen liegen.")
	private String scenarioDescription;

	@NotNull(message = "Die Perioden müssen gesetzt werden.")
	@Min(value = 2, message = "Es müssen mindestens zwei PErioden angegeben werden.")
	private Integer periods;

	@NotNull(message = "Es muss ein Gewerbesteuerhebesatz angegeben werden.")
	@DecimalMin(value = "0.0", message = "Der Gewerbesteuerhebesatz muss zwischen 0 und 1 liegen.")
	@DecimalMax(value = "1.0", message = "Der Gewerbesteuerhebesatz muss zwischen 0 und 1 liegen.")
	private Double businessTaxRate;

	@NotNull(message = "Es muss ein Körperschaftssteuersatz angegeben werden.")
	@DecimalMin(value = "0.0", message = "Der Körperschaftssteuersatz muss zwischen 0 und 1 liegen.")
	@DecimalMax(value = "1.0", message = "Der Körperschaftssteuersatz muss zwischen 0 und 1 liegen.")
	private Double corporateTaxRate;

	@NotNull(message = "Es muss ein Solidaritätssteuersatz angegeben werden.")
	@DecimalMin(value = "0.0", message = "Der Solidaritätssteuersatz muss zwischen 0 und 1 liegen.")
	@DecimalMax(value = "1.0", message = "Der Solidaritätssteuersatz muss zwischen 0 und 1 liegen.")
	private Double solidaryTaxRate;

	@NotNull(message = "Es müssen Eigenkapitalkosten angegeben werden.")
	@DecimalMin(value = "-0.1", message = "Die Eigenkapitalkosten müssen zwischen 0 und 1 liegen.")
	@DecimalMax(value = "1.0", message = "Die Eigenkapitalkosten müssen zwischen 0 und 1 liegen.")
	private Double equityInterestRate;

	@NotNull(message = "Es muss ein Zinssatz für Verbindlichkeiten angegeben werden.")
	@DecimalMin(value = "0.0", message = "Der Zinssatz für Verbindlichkeiten muss zwischen 0 und 1 liegen.")
	@DecimalMax(value = "1.0", message = "Der Zinssatz für Verbindlichkeiten muss zwischen 0 und 1 liegen.")
	private Double interestOnLiabilitiesRate;
        
        private String scenarioColor;
        
        @NotNull
        private Boolean brownRozeff;
	
	@Valid
	private MultiPeriodAccountingFigureRequestDto depreciation;
	
	@Valid
	private MultiPeriodAccountingFigureRequestDto additionalIncome;
	
	@Valid
	private MultiPeriodAccountingFigureRequestDto additionalCosts;
	
	@Valid
	private MultiPeriodAccountingFigureRequestDto investments;
	
	@Valid
	private MultiPeriodAccountingFigureRequestDto divestments;
	
	@Valid
	private MultiPeriodAccountingFigureRequestDto revenue;
	
	@Valid
	private MultiPeriodAccountingFigureRequestDto costOfMaterial;
	
	@Valid
	private MultiPeriodAccountingFigureRequestDto costOfStaff;
	
	@Valid
	private MultiPeriodAccountingFigureRequestDto liabilities;
	
	@Valid
	private MultiPeriodAccountingFigureRequestDto freeCashFlows;
	
	public String getScenarioName() { return scenarioName; }
	public void setScenarioName(String scenarioName) { this.scenarioName = scenarioName; }
	
	public String getScenarioDescription() { return scenarioDescription; }
	public void setScenarioDescription(String scenarioDescription) { this.scenarioDescription = scenarioDescription; }
	
	public Integer getPeriods() { return periods; }
	public void setPeriods(Integer periods) { this.periods = periods; }
	
	public Double getBusinessTaxRate() { return businessTaxRate; }
	public void setBusinessTaxRate(Double businessTaxRate) { this.businessTaxRate = businessTaxRate; }
	
	public Double getCorporateTaxRate() { return corporateTaxRate; }
	public void setCorporateTaxRate(Double corporateTaxRate) { this.corporateTaxRate = corporateTaxRate; }
	
	public Double getSolidaryTaxRate() { return solidaryTaxRate; }
	public void setSolidaryTaxRate(Double solidaryTaxRate) { this.solidaryTaxRate = solidaryTaxRate; }
	
	public Double getEquityInterestRate() { return equityInterestRate; }
	public void setEquityInterestRate(Double equityInterestRate) { this.equityInterestRate = equityInterestRate; }
	
	public Double getInterestOnLiabilitiesRate() { return interestOnLiabilitiesRate; }
	public void setInterestOnLiabilitiesRate(Double interestOnLiabilitiesRate) { this.interestOnLiabilitiesRate = interestOnLiabilitiesRate; }
	
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	public Boolean getStochastic() {return stochastic;}
	public void setStochastic(Boolean stochastic) {this.stochastic = stochastic;}
	
	public MultiPeriodAccountingFigureRequestDto getRevenue() { return revenue; }
	public void setRevenue(MultiPeriodAccountingFigureRequestDto revenue) {
		this.revenue = revenue; 
		if(revenue != null) {
			this.revenue.setFigureName(MultiPeriodAccountingFigureNames.Revenue);
		}
	}
	
	public MultiPeriodAccountingFigureRequestDto getAdditionalIncome() { return additionalIncome; }
	public void setAdditionalIncome(MultiPeriodAccountingFigureRequestDto additionalIncome) { 
		this.additionalIncome = additionalIncome; 
		if(additionalIncome != null) {
			this.additionalIncome.setFigureName(MultiPeriodAccountingFigureNames.AdditionalIncome);
		}
	}
	
	public MultiPeriodAccountingFigureRequestDto getCostOfMaterial() { return costOfMaterial; }
	public void setCostOfMaterial(MultiPeriodAccountingFigureRequestDto costOfMaterial) { 
		this.costOfMaterial = costOfMaterial; 
		if(costOfMaterial != null) {
			this.costOfMaterial.setFigureName(MultiPeriodAccountingFigureNames.CostOfMaterial);
		}
	}
	
	public MultiPeriodAccountingFigureRequestDto getCostOfStaff() { return costOfStaff; }
	public void setCostOfStaff(MultiPeriodAccountingFigureRequestDto costOfStaff) { 
		this.costOfStaff = costOfStaff; 
		if(costOfStaff != null) {
			this.costOfStaff.setFigureName(MultiPeriodAccountingFigureNames.CostOfStaff);
		}
	}
	
	public MultiPeriodAccountingFigureRequestDto getAdditionalCosts() { return additionalCosts; }
	public void setAdditionalCosts(MultiPeriodAccountingFigureRequestDto additionalCosts) { 
		this.additionalCosts = additionalCosts; 
		if(additionalCosts != null){ 
			this.additionalCosts.setFigureName(MultiPeriodAccountingFigureNames.AdditionalCosts);
		}
	}
	
	public MultiPeriodAccountingFigureRequestDto getInvestments() { return investments; }
	public void setInvestments(MultiPeriodAccountingFigureRequestDto investments) { 
		this.investments = investments; 
		if(investments != null){ 
			this.investments.setFigureName(MultiPeriodAccountingFigureNames.Investments);
		}
	}
	
	public MultiPeriodAccountingFigureRequestDto getDivestments() { return divestments; }
	public void setDivestments(MultiPeriodAccountingFigureRequestDto divestments) { 
		this.divestments = divestments; 
		if(divestments != null){ 
			this.divestments.setFigureName(MultiPeriodAccountingFigureNames.Divestments);
		}
	}
		
	public MultiPeriodAccountingFigureRequestDto getLiabilities() { return liabilities; }
	public void setLiabilities(MultiPeriodAccountingFigureRequestDto liabilities) { 
		this.liabilities = liabilities; 
		if(liabilities != null){ 
			this.liabilities.setFigureName(MultiPeriodAccountingFigureNames.Liabilities);
		}
	}
	
	public MultiPeriodAccountingFigureRequestDto getFreeCashFlows() { return freeCashFlows; }
	public void setFreeCashFlows(MultiPeriodAccountingFigureRequestDto freeCashFlows) { 
		this.freeCashFlows = freeCashFlows; 
		if(freeCashFlows != null){ 
			this.freeCashFlows.setFigureName(MultiPeriodAccountingFigureNames.FreeCashFlows);
		}
	}
	
	public MultiPeriodAccountingFigureRequestDto getDepreciation() { return depreciation; }
	public void setDepreciation(MultiPeriodAccountingFigureRequestDto depreciation) { 
		this.depreciation = depreciation; 
		if(depreciation != null){ 
			this.depreciation.setFigureName(MultiPeriodAccountingFigureNames.Depreciation);
		} 
	} 
	
	public List<MultiPeriodAccountingFigureRequestDto> getAllMultiPeriodAccountingFigures(){
		List<MultiPeriodAccountingFigureRequestDto> multiPeriodAccountingFigures = new ArrayList<MultiPeriodAccountingFigureRequestDto>();
		
		multiPeriodAccountingFigures.add(this.depreciation);
		multiPeriodAccountingFigures.add(this.additionalIncome);
		multiPeriodAccountingFigures.add(this.additionalCosts);
		multiPeriodAccountingFigures.add(this.investments);
		multiPeriodAccountingFigures.add(this.divestments);
		multiPeriodAccountingFigures.add(this.revenue);
		multiPeriodAccountingFigures.add(this.costOfMaterial);
		multiPeriodAccountingFigures.add(this.costOfStaff);
		multiPeriodAccountingFigures.add(this.liabilities);
		multiPeriodAccountingFigures.add(this.freeCashFlows);
		
		return multiPeriodAccountingFigures;
	}

    public String getScenarioColor() {
        return scenarioColor;
    }

    public void setScenarioColor(String scenarioColor) {
        this.scenarioColor = scenarioColor;
    }

    @ApiModelProperty(value = "Unternehmensbewertung erfolgt auf Basis von Brown-Rozeff (wird für eine Anfrage nicht benötigt)")
    public Boolean getBrownRozeff() {
        return brownRozeff;
    }

    @ApiModelProperty(value = "Score der Prognose auf Basis von Brown-Rozeff (wird für eine Anfrage nicht benötigt)")
    public void setBrownRozeff(Boolean brownRozeff) {
        this.brownRozeff = brownRozeff;
    }
		
	@Override
	public String toString() {
		
		String newLine = System.getProperty("line.separator");

		StringBuilder sb = new StringBuilder();
		
		sb.append("------------------------------------------------------------------------");
		sb.append(newLine);
		sb.append("Name: ");
		sb.append(this.scenarioName);
		sb.append(", ");
		sb.append(newLine);
		sb.append("Description: ");
		sb.append(this.scenarioDescription);
		sb.append(", ");
		sb.append(newLine);
		sb.append("Periods: ");
		sb.append(this.periods);
		sb.append(", ");
		sb.append(newLine);
		sb.append("Business Tax: ");
		sb.append(this.businessTaxRate);
		sb.append(", ");
		sb.append(newLine);
		sb.append("Corporate Tax: ");
		sb.append(this.corporateTaxRate);
		sb.append(", ");
		sb.append(newLine);
		sb.append("Solidary Tax: ");
		sb.append(this.solidaryTaxRate);
		sb.append(", ");
		sb.append(newLine);
		sb.append("Equity Interest Rate: ");
		sb.append(this.equityInterestRate);
		sb.append(", ");
		sb.append(newLine);
		sb.append("Interest On Liabilites Rate: ");
		sb.append(this.interestOnLiabilitiesRate != null ? this.interestOnLiabilitiesRate : "");
		sb.append(", ");
		sb.append(newLine);
		sb.append("Additional Income: ");
		sb.append(newLine);
		sb.append(this.additionalIncome != null ? this.additionalIncome : "");
		sb.append(", ");
		sb.append(newLine);
		sb.append("Depreciation: ");
		sb.append(newLine);
		sb.append(this.depreciation != null ? this.depreciation : "");
		sb.append(", ");
		sb.append(newLine);
		sb.append("Additional Costs: ");
		sb.append(newLine);
		sb.append(this.additionalCosts != null ? this.additionalCosts : "");
		sb.append(", ");
		sb.append(newLine);
		sb.append("Investments: ");
		sb.append(newLine);
		sb.append(this.investments != null ? this.investments : "");
		sb.append(", ");
		sb.append(newLine);
		sb.append("Divestments: ");
		sb.append(newLine);
		sb.append(this.divestments != null ? this.divestments : "");
		sb.append(", ");
		sb.append(newLine);
		sb.append("Revenue: ");
		sb.append(newLine);
		sb.append(this.revenue != null ? this.revenue : "");
		sb.append(", ");
		sb.append(newLine);
		sb.append("Cost Of Material: ");
		sb.append(newLine);
		sb.append(this.costOfMaterial != null ? this.costOfMaterial : "");
		sb.append(", ");
		sb.append(newLine);
		sb.append("Cost Of Staff: ");
		sb.append(newLine);
		sb.append(this.costOfStaff != null ? this.costOfStaff : "");
		sb.append(", ");
		sb.append(newLine);
		sb.append("Liabilites: ");
		sb.append(newLine);
		sb.append(this.liabilities != null ? this.liabilities : "");
		sb.append(", ");
		sb.append(newLine);
		sb.append("Free Cash Flows:  ");
		sb.append(newLine);
		sb.append(this.freeCashFlows != null ? this.freeCashFlows : "");
		sb.append(", ");
        sb.append(newLine);
        sb.append("CardColor: ");
        sb.append(newLine);
        sb.append(this.scenarioColor != null ? this.scenarioColor : "");
        sb.append(newLine);
		sb.append("------------------------------------------------------------------------");
		
		return sb.toString();
	}
}
