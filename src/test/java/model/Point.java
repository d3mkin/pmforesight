package model;

public class Point {
    private String name;
    private String planDate;
    private String forecastDate;
    private String approvingDocument;
    private String Owner;
    private String Responsible;

    public String getName() {
        return name;
    }

    public String getPlanDate() {
        return planDate;
    }

    public String getForecastDate() {
        return forecastDate;
    }

    public String getApprovingDocument() {
        return approvingDocument;
    }

    public String getOwner() {
        return Owner;
    }

    public String getResponsible() {
        return Responsible;
    }

    public Point setName(String name) {
        this.name = name;
        return this;
    }

    public Point setPlanDate(String planDate) {
        this.planDate = planDate;
        return this;
    }

    public Point setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
        return this;
    }

    public Point setApprovingDocument(String approvingDocument) {
        this.approvingDocument = approvingDocument;
        return this;
    }

    public Point setOwner(String owner) {
        Owner = owner;
        return this;
    }

    public Point setResponsible(String responsible) {
        Responsible = responsible;
        return this;
    }
}
