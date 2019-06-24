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

    //normalOrder
    @Column(name = "pOrder")
    private Integer p;

    @Column(name = "dOrder")
    private Integer d;

    @Column(name = "qOrder")
    private Integer q;

    //seasonalOrder
    @Column(name = "spOrder")
    private Integer sP;

    @Column(name = "sdOrder")
    private Integer sD;

    @Column(name = "sqOrder")
    private Integer sQ;

    @Column(name = "sfOrder")
    private Integer sF;

 //   @Column(name = "Score")
//    private Double score;

    public MultiPeriodAccountingFigureDao() {
    }

    public MultiPeriodAccountingFigureDao(String figureName,
            Boolean isHistoric, List<TimeSeriesItemDao> timeSeriesItems, Integer p, Integer d, Integer q,
            Integer sP, Integer sD, Integer sQ, Integer sF) {
        this.figureName = figureName;
        this.isHistoric = isHistoric;
        this.timeSeriesItems = timeSeriesItems;

        this.p = p;
        this.d = d;
        this.q = q;
        this.sP = sP;
        this.sD = sD;
        this.sQ = sQ;
        this.sF = sF;
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

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }

    public Integer getQ() {
        return q;
    }

    public void setQ(Integer q) {
        this.q = q;
    }

    public Integer getsP() {
        return sP;
    }

    public void setsP(Integer sP) {
        this.sP = sP;
    }

    public Integer getsD() {
        return sD;
    }

    public void setsD(Integer sD) {
        this.sD = sD;
    }

    public Integer getsQ() {
        return sQ;
    }

    public void setsQ(Integer sQ) {
        this.sQ = sQ;
    }

    public Integer getsF() {
        return sF;
    }

    public void setsF(Integer sF) {
        this.sF = sF;
    }

   /* public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }*/

}
