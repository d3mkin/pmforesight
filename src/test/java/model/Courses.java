package model;

public class Courses {
    private String name;
    private String courseCode;
    private String duration;
    private String courseProgram;
    private String weight;
    private String editors;
    private String description;

    public String getName() {
        return name;
    }

    public Courses setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Courses setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public Courses setCourseCode(String courseCode) {
        this.courseCode = courseCode;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public Courses setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public String getCourseProgram() {
        return courseProgram;
    }

    public Courses setCourseProgram(String courseProgram) {
        this.courseProgram = courseProgram;
        return this;
    }
    public String getWeight() {
        return weight;
    }

    public Courses setWeight(String weight) {
        this.weight = weight;
        return this;
    }
    public String getEditors() {
        return editors;
    }

    public Courses seteditors(String editors) {
        this.editors = editors;
        return this;
    }
}
