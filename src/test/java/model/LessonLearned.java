package model;

public class LessonLearned {

    private String activity;
    private String name;
    private String description;
    private String impact;
    private String recommendation;
    private String rol;

    public String getName() {
        return name;
    }

    public LessonLearned setName(String name) {
        this.name = name;
        return this;
    }

    public String getActivity() {
        return activity;
    }

    public LessonLearned setActivity(String activity) {
        this.activity = activity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LessonLearned setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImpact() {
        return impact;
    }

    public LessonLearned setImpact(String impact) {
        this.impact = impact;
        return this;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public LessonLearned setRecommendation(String recommendation) {
        this.recommendation = recommendation;
        return this;
    }

    public String getRols() {
        return rol;
    }

    public LessonLearned setRols(String rol) {
        this.rol = rol;
        return this;
    }

}
