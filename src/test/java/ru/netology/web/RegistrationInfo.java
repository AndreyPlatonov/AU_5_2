package ru.netology.web;

public class RegistrationInfo {
    private String login;
    private String password;
    private String status;

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
    public void setLogin(String login) {
        this.login=login;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public void setStatus(String status) {
        this.status=status;
    }

}


