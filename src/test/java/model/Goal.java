package model;

public class Goal {

    private String name;
    private String managementLevel;
    private String editors;

    public String getName() { return name; }

    public Goal setName(String name) {
        this.name = name;
        return this;
    }

    public String getManagementLevel() { return managementLevel; }

    public Goal setManagementLevel(String managementLevel) {
        this.managementLevel = managementLevel;
        return this;
    }

    public String getEditors() { return editors; }

    public Goal setEditors(String editors) {
        this.editors = editors;
        return this;
    }

}
