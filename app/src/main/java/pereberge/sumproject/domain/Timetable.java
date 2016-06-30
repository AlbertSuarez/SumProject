package pereberge.sumproject.domain;

import java.util.ArrayList;
import java.util.List;

public class Timetable {

    private String day;
    private List<Reservation> hores = new ArrayList<>();

    public Timetable(String day, String[] hores){
        this.day = day;
        for (String h : hores) {
            this.hores.add(new Reservation(null, h));
        }
    }

    public boolean pistaOcupada(int n) {
        return hores.get(n).isOcupat();
    }

    public void reservaPista(int n, String nom , String dia) {
        hores.get(n).setOcupat(true);
        hores.get(n).setPersonaReserva(nom);
    }
}
