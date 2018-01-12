package ru.rustem.beans;

import ru.rustem.User;
import ru.rustem.ejb.UserManager;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class UserBean implements Serializable{

    private User user;
    private String helloMessage = "";
    @EJB
    private UserManager userManager;


    boolean doLogin(String login, int passwordHash) {
        User logUser = userManager.doLogin(login, passwordHash);
        if (logUser == null) {
            return false;
        } else {
            user = logUser;
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("greetings");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //При выходе обновляем данные пользователя в базе данных
    public void doLogout() {
        userManager.updateUser(user);
        user = null;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/view/sign-in");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //При завершении сессии обновляем данные пользователя в базе данных
    @PreDestroy
    public void destroy() {
        userManager.updateUser(user);
    }

    //Getters and setters
    public User getUser() {
        return user;
    }

    public String getHelloMessage() {
        return helloMessage;
    }

    public void setHelloMessage(String helloMessage) {
        this.helloMessage = helloMessage;
    }
}
