
package model;

public class Portfolio {
    private String nameValue;
    private String description;
    private String purpose;

    private String supervisor;
    private String administrator;
    private String workingGroup;

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getAdministrator() {
        return administrator;
    }

    public String getDescription() {
        return description;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getWorkingGroup() {
        return workingGroup;
    }

    public Portfolio setDescription(String description) {
        this.description = description;
        return this;
    }

    public Portfolio setSupervisor(String supervisor) {
        this.supervisor = supervisor;
        return this;
    }

    public Portfolio setPurpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public Portfolio setWorkingGroup(String workingGroup) {
        this.workingGroup = workingGroup;
        return this;
    }
    public String getNameValue() {
        return nameValue;
    }

    public Portfolio setNameValue(String nameValue) {
        this.nameValue = nameValue;
        return this;
    }
}
