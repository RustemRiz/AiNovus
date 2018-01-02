package ru.rustem.beans;

import ru.rustem.User;
import ru.rustem.ejb.UserManager;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

//CDI Bean for user registration
@Named
@RequestScoped
public class RegistrationBean {

    private final static String UNSUITABLE_LOGIN_MESSAGE = "Имя пользователя должно быть длиннее 4 символов и " +
            "состоять из цифр, букв английского алфавита и точек";
    private final static String UNSUITABLE_PASSWORD_MESSAGE = "Пароль недостаточно сложен: должны быть цифры, заглавные и " +
            "строчные буквы и длина минимум 8 символов";
    private final static String PASSWORDS_DO_NOT_MATCH_MESSAGE = "Пароль и повтор пароля не совпадают";

    private final static String USER_ALREADY_EXISTS_MESSAGE = "Пользователь с таким именем уже существует";

    private final static String LOGIN_PATTERN = "[a-zA-Z0-9.]{5,}";
    private final static String PASSWORD_PATTERN = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}";

    private String newLogin = "";
    private String newPassword = "";
    private String newPasswordRepeat = "";
    private String registrationErrorMessage;

    @EJB
    private UserManager userManager;
    @Inject
    private UserBean userBean;


    public void register() {
        //Убираем пробелы в начале и в конце строки
        newLogin = newLogin.trim();
        //Проверка валидности вводимых логина и пароля
        if (!newLogin.matches(LOGIN_PATTERN)) {
            registrationErrorMessage = UNSUITABLE_LOGIN_MESSAGE;
            return;
        }
        if (!newPassword.matches(PASSWORD_PATTERN)) {
            registrationErrorMessage = UNSUITABLE_PASSWORD_MESSAGE;
            return;
        }
        if (!newPassword.equals(newPasswordRepeat)) {
            registrationErrorMessage = PASSWORDS_DO_NOT_MATCH_MESSAGE;
            return;
        }

        User newUser = userManager.createUser(newLogin, newPassword.hashCode());
        //Если пользователь уже существует выводим сообщение об ошибке
        if (newUser == null) {
            registrationErrorMessage = USER_ALREADY_EXISTS_MESSAGE;
        } else {
            userBean.doLogin(newUser.getName(), newPassword.hashCode());
        }
    }

    //Getters and setters
    public String getNewLogin() {
        return newLogin;
    }

    public void setNewLogin(String newLogin) {
        this.newLogin = newLogin;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRepeat() {
        return newPasswordRepeat;
    }

    public void setNewPasswordRepeat(String newPasswordRepeat) {
        this.newPasswordRepeat = newPasswordRepeat;
    }

    public String getRegistrationErrorMessage() {
        return registrationErrorMessage;
    }
}
