package model;

public class Order {
    private String name;
    private String priority;
    private String planDate;
    private String executor;

    public String getName() {
        return name;
    }

    public String getPriority() {
        return priority;
    }

    public String getPlanDate() {
        return planDate;
    }

    public String getExecutor() {
        return executor;
    }

    public Order setName(String name) {
        this.name = name;
        return this;
    }

    public Order setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public Order setPlanDate(String planDate) {
        this.planDate = planDate;
        return this;
    }

    public Order setExecutor(String executor) {
        this.executor = executor;
        return this;
    }
}
