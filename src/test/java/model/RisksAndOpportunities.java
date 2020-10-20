package model;

public class RisksAndOpportunities {
    //Общая информация
    private String risksAndOpportunitiesName;
    private String controlObject;
    private String typeRisksOpportunities;
    private String categoryR;
    private String state;
    private String riskProbability;
    private String levelOfInfluence;
    private String monitoringFrequency;
    private String approvingDocument;
    //Роли
    private String initiator;
    private String responsible;
    private String name;


    public String getName() {
        return name;
    }

    public RisksAndOpportunities setName(String name) {
        this.name = name;
        return this;
    }

    public String getRisksAndOpportunitiesName() {
        return risksAndOpportunitiesName;
    }

    public RisksAndOpportunities setRisksAndOpportunitiesName(String risksAndOpportunitiesName) {
        this.risksAndOpportunitiesName = risksAndOpportunitiesName;
        return this;
    }

    public String getControlObject() {
        return controlObject;
    }

    public RisksAndOpportunities setControlObject(String controlObject) {
        this.controlObject = controlObject;
        return this;
    }

    public String getTypeRisksOpportunities() {
        return typeRisksOpportunities;
    }

    public RisksAndOpportunities setTypeRisksOpportunities(String typeRisksOpportunities) {
        this.typeRisksOpportunities = typeRisksOpportunities;
        return this;
    }

    public String getCategory() {
        return categoryR;
    }

    public RisksAndOpportunities setCategory(String categoryR) {
        this.categoryR = categoryR;
        return this;
    }

    public String getState() {
        return state;
    }

    public RisksAndOpportunities setState(String state) {
        this.state = state;
        return this;
    }

    public String getRiskProbability() {
        return riskProbability;
    }

    public RisksAndOpportunities setriskProbability(String riskProbability) {
        this.riskProbability = riskProbability;
        return this;
    }

    public String getlevelOfInfluence() {
        return levelOfInfluence;
    }

    public RisksAndOpportunities setlevelOfInfluence(String levelOfInfluence) {
        this.levelOfInfluence = levelOfInfluence;
        return this;
    }

    public String getMonitoringFrequency() {
        return monitoringFrequency;
    }

    public RisksAndOpportunities setMonitoringFrequency(String monitoringFrequency) {
        this.monitoringFrequency = monitoringFrequency;
        return this;
    }

    public String getApprovingDocument() {
        return approvingDocument;
    }

    public RisksAndOpportunities setApprovingDocument(String approvingDocument) {
        this.approvingDocument = approvingDocument;
        return this;
    }

    public String getInitiator() {
        return initiator;
    }

    public RisksAndOpportunities setInitiator(String initiator) {
        this.initiator = initiator;
        return this;
    }

    public String getResponsible() {
        return responsible;
    }

    public RisksAndOpportunities setResponsible(String responsible) {
        this.responsible = responsible;
        return this;
    }

}
