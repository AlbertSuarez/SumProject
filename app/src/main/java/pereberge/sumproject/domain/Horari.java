package pereberge.sumproject.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pere on 6/28/16.
 */
public class Horari {

    private String dia;
    private List<Reserva> hores = new ArrayList<>();

    public Horari(String dia, String[] hores){
        this.dia = dia;
        for(String h:hores){
            this.hores.add(new Reserva(null, h));
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
