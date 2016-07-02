package pereberge.sumproject.utils;

import android.content.Context;

import pereberge.sumproject.repository.ReservationRepository;
import pereberge.sumproject.services.ReservationService;

public class ServiceFactory {

    private static ReservationService reservationService;

    public static ReservationService getReservationService(Context context) {
        if (reservationService == null)
            reservationService = new ReservationService(new ReservationRepository(context));
        return reservationService;
    }
}
