package edu.dhbw.ka.mwi.businesshorizon2.models.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "MultiPeriodAccountingFigure")
@Table(name = "MultiPeriodAccountingFigure")
public class MultiPeriodAccountingFigureDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MultiPeriodAccountingFigureId")
    private Long multiPeriodAccountingFigureId;

    @Column(name = "FigureName", columnDefinition = "nvarchar")
    private String figureName;

    @Column(name = "IsHistoric")
    private Boolean isHistoric;

    @OneToMany(mappedBy = "accountingFigure")
    private List<TimeSeriesItemDao> timeSeriesItems = new ArrayList<TimeSeriesItemDao>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ScenarioId")
    private ScenarioDao scenario;

    private Integer[] normalOrder;

    private Integer[] seasonalOrder;

    public MultiPeriodAccountingFigureDao() {
    }

    public MultiPeriodAccountingFigureDao(String figureName, Boolean isHistoric, List<TimeSeriesItemDao> timeSeriesItems, Integer[] normalOrder, Integer[] seasonalOrder) {
        this.figureName = figureName;
        this.isHistoric = isHistoric;
        this.timeSeriesItems = timeSeriesItems;
        this.normalOrder = normalOrder;
        this.seasonalOrder = seasonalOrder;
    }

    public Long getMultiPeriodAccountingFigureId() {
        return multiPeriodAccountingFigureId;
    }

    public String getFigureName() {
        return figureName;
    }

    public void setFigureName(String figureName) {
        this.figureName = figureName;
    }

    public Boolean getIsHistoric() {
        return isHistoric;
    }

    public void setIsHistoric(Boolean isHistoric) {
        this.isHistoric = isHistoric;
    }

    public List<TimeSeriesItemDao> getTimeSeriesItems() {
        return timeSeriesItems;
    }

    public void setTimeSeriesItems(List<TimeSeriesItemDao> timeSeriesItems) {
        this.timeSeriesItems = timeSeriesItems;
    }

    public ScenarioDao getScenario() {
        return scenario;
    }

    public void setScenario(ScenarioDao scenario) {
        this.scenario = scenario;
    }

    public Integer[] getOrder() {
        return normalOrder;
    }

    public void setOrder(Integer[] order) {
        this.normalOrder = order;
    }

    public Integer[] getSeasonalOrder() {
        return seasonalOrder;
    }

    public void setSeasonalOrder(Integer[] seasonalOrder) {
        this.seasonalOrder = seasonalOrder;
    }

}
