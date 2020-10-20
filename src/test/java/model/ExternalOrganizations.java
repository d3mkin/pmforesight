package model;

public class ExternalOrganizations {
    private String name;
    private String description;
    private String contacts;

    public String getName() {
        return name;
    }

    public ExternalOrganizations setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ExternalOrganizations setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getContacts() {
        return contacts;
    }

    public ExternalOrganizations setContacts(String contacts) {
        this.contacts = contacts;
        return this;
    }
}
