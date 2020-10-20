package model;

import java.util.List;

public class Program {
    private String name;
    private String shortName;
    private String direction;
    private String priority;
    private String portfolio;
    private String description;
    private String formalGrounds;
    private String programJustification;
    private String goal;
    private String information;
    //роли
    private String customer;
    private String curator;
    private String supervisor;
    private List<String> admins;
    private String passportDeveloper;
    private String masterPlanDeveloper;
    private String performers;
    private String concernedParties;

    public List<String> getAdmins() {
        return admins;
    }

    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    public String getName() {
        return name;
    }

    public Program setName(String name) {
        this.name = name;
        return this;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public Program setPortfolio(String portfolio) {
        this.portfolio = portfolio;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public Program setCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public String getCurator() {
        return curator;
    }

    public Program setCurator(String curator) {
        this.curator = curator;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormalGrounds() {
        return formalGrounds;
    }

    public void setFormalGrounds(String formalGrounds) {
        this.formalGrounds = formalGrounds;
    }

    public String getProgramJustification() {
        return programJustification;
    }

    public void setProgramJustification(String programJustification) {
        this.programJustification = programJustification;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPassportDeveloper() {
        return passportDeveloper;
    }

    public void setPassportDeveloper(String passportDeveloper) {
        this.passportDeveloper = passportDeveloper;
    }

    public String getMasterPlanDeveloper() {
        return masterPlanDeveloper;
    }

    public void setMasterPlanDeveloper(String masterPlanDeveloper) {
        this.masterPlanDeveloper = masterPlanDeveloper;
    }

    public String getPerformers() {
        return performers;
    }

    public void setPerformers(String performers) {
        this.performers = performers;
    }

    public String getConcernedParties() {
        return concernedParties;
    }

    public void setConcernedParties(String concernedParties) {
        this.concernedParties = concernedParties;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public Program setSupervisor(String supervisor) {
        this.supervisor = supervisor;
        return this;
    }
}
