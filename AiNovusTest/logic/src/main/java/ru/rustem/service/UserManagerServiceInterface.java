package ru.rustem.service;

import ru.rustem.User;
import javax.ejb.Local;

@Local
public interface UserManagerServiceInterface {
    User doLogin(String login, int passwordHash);
    User createUser(String login, int passwordHash);
    void updateUser(User user);
}
