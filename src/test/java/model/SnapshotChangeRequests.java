package model;

public class SnapshotChangeRequests {
    private String name;
    private String comment;
    private String projectReasons;

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getProjectReasons() {
        return projectReasons;
    }

    public SnapshotChangeRequests setName(String name) {
        this.name = name;
        return this;
    }

    public SnapshotChangeRequests setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public SnapshotChangeRequests setProjectReasons(String projectReasons) {
        this.projectReasons = projectReasons;
        return this;
    }
}
