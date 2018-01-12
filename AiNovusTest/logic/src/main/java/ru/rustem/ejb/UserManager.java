package ru.rustem.ejb;

import org.apache.commons.lang3.StringUtils;
import ru.rustem.User;
import ru.rustem.domain.UserEntity;
import ru.rustem.service.UserManagerServiceInterface;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserManager implements UserManagerServiceInterface {
    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    public User doLogin(String login, int passwordHash) {
        UserEntity userEntity = entityManager.find(UserEntity.class, login);
        if (userEntity == null) {
            return null;
        }
        if(userEntity.getPasswordHashCode() != passwordHash){
            return null;
        }
        return userEntity.toDto();
    }

    public User createUser(String login, int passwordHash){
        UserEntity userEntity = entityManager.find(UserEntity.class, login);
        if (userEntity != null) {
            return null;
        }
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setName(login);
        newUserEntity.setPasswordHashCode(passwordHash);
        entityManager.persist(newUserEntity);
        return newUserEntity.toDto();
    }

    public void updateUser(User user){
        if(user == null || StringUtils.isEmpty(user.getName())){
            return;
        }
        UserEntity userEntity = entityManager.find(UserEntity.class, user.getName());
        if(userEntity == null){
            return;
        }
        userEntity.fromDto(user);
        entityManager.merge(userEntity);
    }

}
