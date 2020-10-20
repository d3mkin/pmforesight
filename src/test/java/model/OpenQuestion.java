package model;

public class OpenQuestion {
    private String name;
    private String executor;
    private String initDate;
    private String planDate;

    public String getName() {
        return name;
    }

    public String getExecutor() {
        return executor;
    }

    public String getInitDate() {
        return initDate;
    }

    public String getPlanDate() {
        return planDate;
    }

    public OpenQuestion setName(String name) {
        this.name = name;
        return this;
    }

    public OpenQuestion setExecutor(String executor) {
        this.executor = executor;
        return this;
    }

    public OpenQuestion setInitDate(String initDate) {
        this.initDate = initDate;
        return this;
    }

    public OpenQuestion setPlanDate(String planDate) {
        this.planDate = planDate;
        return this;
    }
}
