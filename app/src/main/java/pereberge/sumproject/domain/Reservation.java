package pereberge.sumproject.domain;

import java.util.Date;

import pereberge.sumproject.utils.Entity;

public class Reservation implements Entity {

    private String id;
    private String person;
    private Date date;

    public Reservation() {

    }

    public Reservation(String person, Date date){
        this.date = date;
        this.person = person;
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
}
