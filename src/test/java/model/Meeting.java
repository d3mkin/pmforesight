package model;

import java.util.List;

public class Meeting {
    private String name;
    private String location;
    private String controlObject;
    private String duration;
    private String description;
    //Роли
    private String chairman;
    private String secretary;
    private List<String> members;
    private List<String> missing;
    private String other;

    public Meeting setName(String name) {
        this.name = name;
        return this;
    }

    public Meeting setLocation(String location) {
        this.location = location;
        return this;
    }

    public Meeting setControlObject(String controlObject) {
        this.controlObject = controlObject;
        return this;
    }

    public Meeting setChairman(String chairman) {
        this.chairman = chairman;
        return this;
    }

    public Meeting setSecretary(String secretary) {
        this.secretary = secretary;
        return this;
    }

    public Meeting setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public Meeting setDescription(String description) {
        this.description = description;
        return this;
    }

    public Meeting setOther(String other) {
        this.other = other;
        return this;
    }

    public Meeting setMissing(List<String> missing) {
        this.missing = missing;
        return this;
    }

    public Meeting setMembers(List<String> members) {
        this.members = members;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getControlObject() {
        return controlObject;
    }

    public String getChairman() {
        return chairman;
    }

    public String getSecretary() {
        return secretary;
    }

    public String getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public String getOther() {
        return other;
    }

    public List<String> getMembers() {
        return members;
    }

    public List<String> getMissing() {
        return missing;
    }
}
