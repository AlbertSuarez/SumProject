package pereberge.sumproject.domain;

import pereberge.sumproject.utils.Entity;

/**
 * Created by pere on 6/28/16.
 */
public class Reservation implements Entity {
    private int id;
    private boolean ocupat = false;
    private String personaReserva;
    private String hora;

    int c = 0;

    public Reservation(String personaReserva, String hora){
        this.id = c;
        this.hora = hora;
        this.personaReserva = personaReserva;
        this.ocupat = true;
        c++;
    }

    public boolean isOcupat() {
        return ocupat;
    }

    public void setOcupat(boolean ocupat) {
        this.ocupat = ocupat;
    }

    public String getPersonaReserva() {
        return personaReserva;
    }

    public void setPersonaReserva(String personaReserva) {
        this.personaReserva = personaReserva;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {

    }
}
