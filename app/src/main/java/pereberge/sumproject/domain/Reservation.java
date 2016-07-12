package pereberge.sumproject.domain;

import java.util.Date;

import pereberge.sumproject.utils.Entity;

public class Reservation implements Entity {

    private String id;
    private String person;
    private Date date;
    private String password;

    public Reservation() {

    }

    public Reservation(String person, Date date, String password){
        this.date = date;
        this.person = person;
        this.password = password;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
