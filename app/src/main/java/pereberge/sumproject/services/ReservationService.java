package pereberge.sumproject.services;

import java.util.List;

/**
 * Created by pere on 6/29/16.
 */
import pereberge.sumproject.Repository.Repository;
import pereberge.sumproject.domain.Reservation;
import pereberge.sumproject.utils.Service;

public class ReservationService extends Service<Reservation> {

    private static final String TAG = ReservationService.class.getSimpleName();
    private final Repository<Reservation> reservationRepository;
    private int loaded = 0;
    private int loadNeed = 1;

    public ReservationService(Repository<Reservation> repository) {
        super(repository);
        this.reservationRepository = repository;
    }

    @Override
    public Reservation save(Reservation item) {
        Reservation reservation = super.save(item);
        return reservation;
    }


    public List<Reservation> getReserves(String dia) {
        List<Reservation> list;
        list = reservationRepository.all();
        return list;
    }


    public Reservation getReserva(String dia) {
        return reservationRepository.get(dia);
    }

    @Override
    public void setOnChangedListener(final Repository.OnChangedListener listener) {

        repository.setOnChangedListener(new Repository.OnChangedListener() {
            @Override
            public void onChanged(EventType type) {
                triggerListener(listener, type);
            }
        });
        if (reservationRepository != null) {
            loadNeed+=1;
            reservationRepository.setOnChangedListener(new Repository.OnChangedListener() {
                @Override
                public void onChanged(EventType type) {
                    triggerListener(listener, type);
                }
            });
        }
    }

    private void triggerListener(Repository.OnChangedListener listener, Repository.OnChangedListener.EventType type) {
        if (type == Repository.OnChangedListener.EventType.Full) {
            loaded += 1;
        }
        if (loaded == loadNeed) {
            listener.onChanged(Repository.OnChangedListener.EventType.Full);
        } else {
            if (type == Repository.OnChangedListener.EventType.Full){
                type = Repository.OnChangedListener.EventType.Added;
            }
            listener.onChanged(type);
        }
    }
}
