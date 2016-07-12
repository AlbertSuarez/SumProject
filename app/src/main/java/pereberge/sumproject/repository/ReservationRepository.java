package pereberge.sumproject.repository;

import android.content.Context;

import com.firebase.client.DataSnapshot;

import java.util.Date;

import pereberge.sumproject.domain.Reservation;

public class ReservationRepository extends FirebaseRepository<Reservation>{

    public ReservationRepository(Context context){
        super(context);
    }

    @Override
    protected Reservation convert(DataSnapshot data) {
        if (data == null) return null;

        Reservation reservation = new Reservation();
        reservation.setId(data.getKey());
        for (DataSnapshot d : data.getChildren()) {
            if (d.getKey().equals("person")) {
                reservation.setPerson(d.getValue(String.class));
            } else if (d.getKey().equals("date")) {
                reservation.setDate(d.getValue(Date.class));
            } else if (d.getKey().equals("password")) {
                reservation.setPassword(d.getValue(String.class));
            }
        }
        return reservation;
    }

    @Override
    public String getObjectReference() {
        return "reservations";
    }
}
