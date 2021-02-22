package model;

public class Snapshot {
    private String name;
    private String comment;
    private String editor;
    private String reviewer;

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getEditor() {
        return editor;
    }

    public String getReviewer() {
        return reviewer;
    }

    public Snapshot setName(String name) {
        this.name = name;
        return this;
    }

    public Snapshot setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Snapshot setEditor(String editor) {
        this.editor = editor;
        return this;
    }

    public Snapshot setReviewer(String reviewer) {
        this.reviewer = reviewer;
        return  this;
    }
}
