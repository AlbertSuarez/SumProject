package pereberge.sumproject.Repository;

import android.content.Context;

import java.util.List;

import pereberge.sumproject.domain.Reserva;

/**
 * Created by pere on 6/29/16.
 */
public class ReservaRepository extends FirebaseRepository<Reserva>{

    List<Reserva> reserves;

    public ReservaRepository(Context context){
        super(context);
    }

    @Override
    public String getObjectReference() {
        return "Reserva";
    }
}
