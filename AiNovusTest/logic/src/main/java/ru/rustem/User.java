package ru.rustem;


import java.util.Date;

//DTO
public class User {
    private String name;
    private Date dateLastConnection;
    private String lastIp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }
}
