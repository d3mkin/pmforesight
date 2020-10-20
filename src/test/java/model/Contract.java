package model;

import java.util.List;

public class Contract {
    private String contractNumber;
    private String contractName;
    private String ShortName;
    private String project;
    private String contractType;
    private String stage;
    private String law;
    private String exceptedPrice;
    private String actualPrice;
    private String description;
    private String linkToEtp;

    //Роли
    private String customer;
    private String executor;
    private String responsiblePerson; // ответственный
    private List<String> admins;
    private List<String> members;

    public String getContractNumber() {
        return contractNumber;
    }

    public Contract setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
        return this;

    }

    public String getName() {
        return contractName;
    }

    public Contract setContractName(String contractName) {
        this.contractName = contractName;
        return this;
    }

    public String getShortName() {
        return ShortName;
    }

    public Contract setShortName(String shortName) {
        ShortName = shortName;
        return this;
    }

    public String getProject() {
        return project;
    }

    public Contract setProject(String project) {
        this.project = project;
        return this;
    }

    public String getContractType() {
        return contractType;
    }

    public Contract setContractType(String contractType) {
        this.contractType = contractType;
        return this;
    }

    public String getStage() {
        return stage;
    }

    public Contract setStage(String stage) {
        this.stage = stage;
        return this;
    }

    public String getLaw() {
        return law;
    }

    public Contract setLaw(String law) {
        this.law = law;
        return this;
    }

    public String getExceptedPrice() {
        return exceptedPrice;
    }

    public Contract setExceptedPrice(String exceptedPrice) {
        this.exceptedPrice = exceptedPrice;
        return this;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public Contract setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Contract setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLinkToEtp() {
        return linkToEtp;
    }

    public Contract setLinkToEtp(String linkToEtp) {
        this.linkToEtp = linkToEtp;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public Contract setCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public String getExecutor() {
        return executor;
    }

    public Contract setExecutor(String executor) {
        this.executor = executor;
        return this;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public Contract setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
        return this;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public Contract setAdmins(List<String> admins) {
        this.admins = admins;
        return this;
    }

    public List<String> getMembers() {
        return members;
    }

    public Contract setMembers(List<String> members) {
        this.members = members;
        return this;
    }
}
