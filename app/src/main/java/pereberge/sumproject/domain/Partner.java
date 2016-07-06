package pereberge.sumproject.domain;


import pereberge.sumproject.utils.Entity;

public class Partner implements Entity {

    private String id;
    private String name;

    public Partner() {

    }

    public Partner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
