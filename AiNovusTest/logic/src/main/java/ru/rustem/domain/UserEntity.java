package ru.rustem.domain;

import ru.rustem.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Table(name = "SIMPLE_USER")
public class UserEntity {
    @Id
    private String name;
    @Min(value = 8)
    @Column(name = "PASSWORD")
    private int passwordHashCode;
    @Column(name = "DATE_CONNECTION")
    private Date dateLastConnection;
    @Column(name = "IP")
    private String lastIp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPasswordHashCode() {
        return passwordHashCode;
    }

    public void setPasswordHashCode(int passwordHashCode) {
        this.passwordHashCode = passwordHashCode;
    }

    public Date getDateLastConnection() {
        return dateLastConnection;
    }

    public void setDateLastConnection(Date dateLastConnection) {
        this.dateLastConnection = dateLastConnection;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String ip) {
        this.lastIp = ip;
    }

    public User toDto(){
        User user = new User();
        user.setName(name);
        user.setDateLastConnection(dateLastConnection);
        user.setLastIp(lastIp);
        return user;
    }

    public void fromDto(User user){
        dateLastConnection = user.getDateLastConnection();
        lastIp = user.getLastIp();
    }
}
