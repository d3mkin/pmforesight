package model;

public class Indicator {

    //Общая информация
    private String name;
    private String estimationType;
    private String unit;
    private String basicValue;
    private String kpi;
    private String approvingDoc;


    private String period;
    private String plan;
    private String forecast;
    private String fact;
    //Роли
    private String responsible;

    public String getName() {
        return name;
    }

    public String getEstimationType() {
        return estimationType;
    }

    public String getUnit() {
        return unit;
    }

    public String getBasicValue() {
        return basicValue;
    }

    public String getPeriod() { return period; }

    public String getPlan() { return plan; }

    public String getForecast() { return forecast; }

    public String getFact() { return fact; }

    public String getKPI() { return kpi; }

    public String getResponsible() { return responsible; }

    public String getApprovingDoc() { return approvingDoc; }

    public Indicator setName(String name) {
        this.name = name;
        return this;
    }

    public Indicator setKPI(String kpi) {
        this.kpi = kpi;
        return this;
    }

    public Indicator setEstimationType(String estimationType) {
        this.estimationType = estimationType;
        return this;
    }

    public Indicator setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public Indicator setBasicValue(String basicValue) {
        this.basicValue = basicValue;
        return this;
    }

    public Indicator setPeriod(String period) {
        this.period = period;
        return this;
    }

    public Indicator setPlan(String plan) {
        this.plan = plan;
        return this;
    }

    public Indicator setForecast(String forecast) {
        this.forecast = forecast;
        return this;
    }

    public Indicator setFact(String fact) {
        this.fact = fact;
        return this;
    }

    public Indicator setResponsible(String responsible) {
        this.responsible = responsible;
        return this;
    }

    public Indicator setApprovingDoc(String approvingDoc) {
        this.approvingDoc = approvingDoc;
        return this;
    }

}
