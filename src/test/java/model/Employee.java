package model;

public class Employee {
    private String name;
    private String lastName;
    private String firstName;
    private String secondName;
    private String position;
    private String rank;
    private String phone;
    private String account;
    private String email;
    private String unit;

    private String rights;
    private String active;
    private String assignToRoles;
    private String groups;
    private String getEmails;

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getSecondName() {
        return secondName;
    }

    public Employee setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public Employee setPosition(String position) {
        this.position = position;
        return this;
    }

    public String getRank() {
        return rank;
    }

    public Employee setRank(String rank) {
        this.rank = rank;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Employee setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public Employee setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Employee setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public Employee setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public String getRights() {
        return rights;
    }

    public Employee setRights(String rights) {
        this.rights = rights;
        return this;
    }

    public String getActive() {
        return active;
    }

    public Employee setActive(String active) {
        this.active = active;
        return this;
    }

    public String getAssignToRoles() {
        return assignToRoles;
    }

    public Employee setAssignToRoles(String assignToRoles) {
        this.assignToRoles = assignToRoles;
        return this;
    }

    public String getGroups() {
        return groups;
    }

    public Employee setGroups(String groups) {
        this.groups = groups;
        return this;
    }

    public String getGetEmails() {
        return getEmails;
    }

    public Employee setGetEmails(String getEmails) {
        this.getEmails = getEmails;
        return this;
    }
}
