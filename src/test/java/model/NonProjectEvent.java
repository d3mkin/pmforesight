package model;

public class NonProjectEvent {
    private String name;
    private String parentEntity;
    private String curator;
    private String leader;

    public String getName() {
        return name;
    }

    public String getParentEntity() {
        return parentEntity;
    }

    public String getCurator() {
        return curator;
    }

    public String getLeader() {
        return leader;
    }

    public NonProjectEvent setName(String name) {
        this.name = name;
        return this;
    }

    public NonProjectEvent setParentEntity(String parentEntity) {
        this.parentEntity = parentEntity;
        return this;
    }

    public NonProjectEvent setCurator(String curator) {
        this.curator = curator;
        return this;
    }

    public NonProjectEvent setManager(String leader) {
        this.leader = leader;
        return this;
    }
}
