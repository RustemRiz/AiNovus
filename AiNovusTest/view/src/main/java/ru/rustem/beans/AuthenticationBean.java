package ru.rustem.beans;

import ru.rustem.ejb.UserManager;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

//CDI Bean for user authentication
@Named
@RequestScoped
public class AuthenticationBean implements Serializable {

    private final static String LOGIN_ERROR_MESSAGE = "Имя пользователя и пароль не подходят";
    private String login = "";
    private String password = "";
    private String loginErrorMessage;

    @EJB
    private UserManager userManager;
    @Inject
    private UserBean userBean;

    public void doLogin() {
        if (!userBean.doLogin(login, password.hashCode())) {
            loginErrorMessage = LOGIN_ERROR_MESSAGE;
        } else {
            loginErrorMessage = "";
        }

    }

    //Getters and setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginErrorMessage() {
        return loginErrorMessage;
    }


}
