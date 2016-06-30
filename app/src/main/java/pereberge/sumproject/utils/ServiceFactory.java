package pereberge.sumproject.utils;

import android.content.Context;

import pereberge.sumproject.Repository.ReservationRepository;
import pereberge.sumproject.services.ReservationService;

/**
 * Created by pere on 6/30/16.
 */
public class ServiceFactory {

    private static ReservationService reservationService;

    public static ReservationService getReservationService(Context context) {
        if (reservationService == null)
            reservationService = new ReservationService(new ReservationRepository(context));
        return reservationService;
    }
}
