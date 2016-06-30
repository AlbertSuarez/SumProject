package pereberge.sumproject.Repository;

import android.content.Context;

import java.util.List;

import pereberge.sumproject.domain.Reservation;

public class ReservationRepository extends FirebaseRepository<Reservation>{

    List<Reservation> reservations;

    public ReservationRepository(Context context){
        super(context);
    }

    @Override
    public String getObjectReference() {
        return "Reservation";
    }
}
