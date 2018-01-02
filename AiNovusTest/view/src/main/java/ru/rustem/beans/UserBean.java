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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Named
@SessionScoped
public class UserBean implements Serializable{

    private User user;
    private String helloMessage = "";

    @EJB
    UserManager userManager;


    public boolean doLogin(String login, int passwordHash) {
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

    //Конструируем сообщение приветствия
    public void constructMessage(String ip) {
        LocalDateTime localDateTime = LocalDateTime.now();
        int hours = localDateTime.getHour();
        StringBuilder builder = new StringBuilder();
        //Определение времени суток
        if (hours >= 6 && hours < 10) {
            builder.append("Доброе утро, ");
        } else if (hours >= 10 && hours < 18) {
            builder.append("Добрый день, ");
        } else if (hours >= 18 && hours <= 22) {
            builder.append("Добрый вечер, ");
        } else {
            builder.append("Добрая ночь, ");
        }
        //Добавление имени пользователя
        builder.append(user.getName()).append("!\n\r");

        //Если мы только что зарегистрировались, не пишем дату последнего захода и ip
        if (user.getDateLastConnection() == null || user.getLastIp() == null) {
            builder.append("Вы заходите в первый раз.");
        } else {
            builder.append("В прошлый раз вы заходили с адреса ").append(user.getLastIp());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    " в HH часов mm минут dd MMM yyyy года", Locale.getDefault());
            Timestamp timestamp = (Timestamp) user.getDateLastConnection();
            LocalDateTime lastConnectLocalDateTime = timestamp.toLocalDateTime();
            builder.append(lastConnectLocalDateTime.format(formatter));
        }
        //Обновляем данные пользователя
        user.setLastIp(ip);
        user.setDateLastConnection(Timestamp.valueOf(localDateTime));
        helloMessage = builder.toString();
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
