package pereberge.sumproject.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pereberge.sumproject.domain.Partner;
import pereberge.sumproject.repository.Repository;
import pereberge.sumproject.domain.Reservation;
import pereberge.sumproject.utils.DateUtils;
import pereberge.sumproject.utils.Service;

public class ReservationService extends Service<Reservation> {

    private int loaded = 0;
    private int loadNeed = 1;

    private Repository<Partner> partnerRepository;

    public ReservationService(Repository<Reservation> repository, Repository<Partner> partnerRepository) {
        super(repository);
        this.partnerRepository = partnerRepository;
    }

    public List<Reservation> getReservations() {
        return repository.all();
    }

    public List<Reservation> getReservationsByDay(Integer day, Integer month, Integer year) {
        List<Reservation> list = new ArrayList<>();
        Date date = DateUtils.createDate(day, month, year);
        for (Reservation reservation : repository.all()) {
            if (DateUtils.isSameDay(reservation.getDate(), date))
                list.add(reservation);
        }
        return list;
    }

    public List<Reservation> getReservationsByTodayAndTomorrow() {
        List<Reservation> list = new ArrayList<>();
        Date today = DateUtils.getToday();
        Date tomorrow = DateUtils.getTomorrow();
        for (Reservation reservation : repository.all()) {
            if (DateUtils.isSameDay(reservation.getDate(), today) ||
                    DateUtils.isSameDay(reservation.getDate(), tomorrow))
                list.add(reservation);
        }
        return list;
    }

    public List<Reservation> getReservationsByToday() {
        List<Reservation> list = new ArrayList<>();
        Date today = DateUtils.getToday();
        for (Reservation reservation : repository.all()) {
            if (DateUtils.isSameDay(reservation.getDate(), today))
                list.add(reservation);
        }
        return list;
    }

    public List<Reservation> getReservationsByTomorrow() {
        List<Reservation> list = new ArrayList<>();
        Date tomorrow = DateUtils.getTomorrow();
        for (Reservation reservation : repository.all()) {
            if (DateUtils.isSameDay(reservation.getDate(), tomorrow))
                list.add(reservation);
        }
        return list;
    }

    public Reservation getReservation(String id) {
        return repository.get(id);
    }

    public List<String> getPartnerNames() {
        List<String> names = new ArrayList<>();
        for (Partner partner : partnerRepository.all()) {
            names.add(partner.getName());
        }
        return names;
    }

    @Override
    public void setOnChangedListener(final Repository.OnChangedListener listener) {

        repository.setOnChangedListener(new Repository.OnChangedListener() {
            @Override
            public void onChanged(EventType type) {
                triggerListener(listener, type);
            }
        });
        if (repository != null) {
            loadNeed+=1;
            repository.setOnChangedListener(new Repository.OnChangedListener() {
                @Override
                public void onChanged(EventType type) {
                    triggerListener(listener, type);
                }
            });
        }
        if (partnerRepository != null) {
            loadNeed += 1;
            partnerRepository.setOnChangedListener(new Repository.OnChangedListener() {
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
