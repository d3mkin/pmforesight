package model;

public class Contractor {
    private String name;
    private String description;
    private String INN;
    private String contacts;

    public String getName() {
        return name;
    }

    public Contractor setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Contractor setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getINN() {
        return INN;
    }

    public Contractor setINN(String INN) {
        this.INN = INN;
        return this;
    }

    public String getContacts() {
        return contacts;
    }

    public Contractor setContacts(String contacts) {
        this.contacts = contacts;
        return this;
    }
}
