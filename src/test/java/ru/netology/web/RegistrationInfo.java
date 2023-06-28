package ru.netology.web;

public class RegistrationInfo {
    private final String login;
    private final String password;
    private final String status;

    public RegistrationInfo(String login, String password, String status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public String getStatus() {
        return this.status;
    }


}


