package model;

import java.util.List;

public class Project {
    private String name;
    private String shortName;
    private String direction;
    private String priority;
    private String projectLevel;
    private String type;
    private String program;
    private String goal;
    private String information;
    private String description;
    private String rationale;
    private String formalGrounds;
    //роли
    private String customer;
    private String curator;
    private String supervisor;
    private List<String> admins;
    private List<String> workGroup;
    private String passportDeveloper;
    private String masterPlanDeveloper;
    private String performers;
    private String concernedParties;

    public String getName() {
        return name;
    }

    public Project setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Project setType(String type) {
        this.type = type;
        return this;
    }

    public String getProgram() {
        return program;
    }

    public Project setProgram(String program) {
        this.program = program;
        return this;
    }

    public String getCurator() {
        return curator;
    }

    public Project setCurator(String curator) {
        this.curator = curator;
        return this;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public Project setSupervisor(String supervisor) {
        this.supervisor = supervisor;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public Project setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public Project setCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Project setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getFormalGrounds() {
        return formalGrounds;
    }

    public Project setFormalGrounds(String formalGrounds) {
        this.formalGrounds = formalGrounds;
        return this;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public Project setAdmins(List<String> admins) {
        this.admins = admins;
        return this;
    }

    public String getGoal() {
        return goal;
    }

    public Project setGoal(String goal) {
        this.goal = goal;
        return this;
    }

    public List<String> getWorkGroup() {
        return workGroup;
    }

    public Project setWorkGroup(List<String> workGroup) {
        this.workGroup = workGroup;
        return this;
    }

    public String getInformation() {
        return information;
    }

    public Project setInformation(String information) {
        this.information = information;
        return this;
    }

    public String getPriority() {
        return priority;
    }

    public Project setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public String getProjectLevel() {
        return projectLevel;
    }

    public Project setProjectLevel(String projectLevel) {
        this.projectLevel = projectLevel;
        return this;
    }

    public String getRationale() {
        return rationale;
    }

    public Project setRationale(String rationale) {
        this.rationale = rationale;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public Project setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getConcernedParties() {
        return concernedParties;
    }

    public Project setConcernedParties(String concernedParties) {
        this.concernedParties = concernedParties;
        return this;
    }

    public String getMasterPlanDeveloper() {
        return masterPlanDeveloper;
    }

    public Project setMasterPlanDeveloper(String masterPlanDeveloper) {
        this.masterPlanDeveloper = masterPlanDeveloper;
        return this;
    }

    public String getPassportDeveloper() {
        return passportDeveloper;
    }

    public Project setPassportDeveloper(String passportDeveloper) {
        this.passportDeveloper = passportDeveloper;
        return this;
    }

    public String getPerformers() {
        return performers;
    }

    public Project setPerformers(String performers) {
        this.performers = performers;
        return this;
    }
}
