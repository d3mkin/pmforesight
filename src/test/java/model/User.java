package model;

import com.codeborne.selenide.Credentials;

public class User {
    private final Credentials credentials;
    private String name;
    private String group;
    private Boolean isFA;

    public User(String login, String password) {
        credentials = new Credentials(login, password);
    }

    public String getLogin() {
        return credentials.login;
    }

    public String getPassword() {
        return credentials.password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Boolean getFA() {
        return isFA;
    }

    public void setFA(Boolean FA) {
        isFA = FA;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
