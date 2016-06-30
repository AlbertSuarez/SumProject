package pereberge.sumproject.services;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by pere on 6/29/16.
 */
import pereberge.sumproject.Repository.Repository;
import pereberge.sumproject.Repository.ReservaRepository;
import pereberge.sumproject.domain.Reserva;
import pereberge.sumproject.utils.Service;

public class ReservaService extends Service<Reserva> {

    private static final String TAG = ReservaService.class.getSimpleName();
    private final Repository<Reserva> reservaRepository;
    private int loaded = 0;
    private int loadNeed = 1;

    public ReservaService(Repository<Reserva> repository) {
        super(repository);
        this.reservaRepository = repository;
    }

    @Override
    public Reserva save(Reserva item) {
        Reserva reserva = super.save(item);
        return reserva;
    }


    public List<Reserva> getReserves(String dia) {
        List<Reserva> list;
        list = reservaRepository.all();
        return list;
    }


    public Reserva getReserva(String dia) {
        return reservaRepository.get(dia);
    }

    //@Override
    /*public void setOnChangedListener(final Repository.OnChangedListener listener) {

        repository.setOnChangedListener(new Repository.OnChangedListener() {
            @Override
            public void onChanged(EventType type) {
                triggerListener(listener, type);
            }
        });
        if (dishesRepository != null) {
            loadNeed+=1;
            dishesRepository.setOnChangedListener(new Repository.OnChangedListener() {
                @Override
                public void onChanged(EventType type) {
                    triggerListener(listener, type);
                }
            });
        }

        if (collaborativeMenuAnswerRepository != null) {
            loadNeed+=1;
            collaborativeMenuAnswerRepository.setOnChangedListener(new Repository.OnChangedListener() {
                @Override
                public void onChanged(EventType type) {
                    triggerListener(listener, type);
                }
            });
        }

        if (userRepository != null) {
            loadNeed += 1;
            userRepository.setOnChangedListener(new Repository.OnChangedListener() {
                @Override
                public void onChanged(EventType type) {
                    triggerListener(listener, type);
                }
            });
        }
    }*/

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
