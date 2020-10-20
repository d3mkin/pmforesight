
package model;

public class NationalProject {
    private String nameValue;
    private String target;

    private String curator;
    private String supervisor;
    private String administrator;

    public String getAdministrator() {
        return administrator;
    }

    public String getCurator() {
        return curator;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public NationalProject setAdministrator(String administrator) {
        this.administrator = administrator;
        return this;
    }

    public NationalProject setCurator(String curator) {
        this.curator = curator;
        return this;
    }

    public NationalProject setSupervisor(String supervisor) {
        this.supervisor = supervisor;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public NationalProject setTarget(String target) {
        this.target = target;
        return this;
    }

    public NationalProject setNameValue(String nameValue) {
        this.nameValue = nameValue;
        return this;
    }

    public String getNameValue() {
        return nameValue;
    }
}