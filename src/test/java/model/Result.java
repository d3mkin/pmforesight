package model;

public class Result {
    //Общая информация
    private String name;
    private String type;
    private String unit;
    private String date;
    private String value;
    private String federalResultLink;
    private String parentEntity;
    //Роли
    private String responsible;

    //Геттеры-Сеттеры
    public String getName() {
        return name;
    }

    public Result setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Result setType(String type) {
        this.type = type;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public Result setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public Result setDate(String date) {
        this.date = date;
        return this;
    }

    public String getDate() {
        return date;
    }

    public String getValue() {
        return value;
    }

    public Result setValue(String value) {
        this.value = value;
        return this;
    }

    public String getResponsible() {
        return responsible;
    }

    public Result setResponsible(String responsible) {
        this.responsible = responsible;
        return this;
    }

    public String getFederalResultLink() { return federalResultLink; }

    public Result setFederalResultLink(String federalResultLink) {
        this.federalResultLink = federalResultLink;
        return this;
    }

    public String getParentEntity() {
        return parentEntity;
    }

    public Result setParentEntity(String parentEntity) {
        this.parentEntity = parentEntity;
        return this;
    }
}
