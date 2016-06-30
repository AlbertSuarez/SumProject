package pereberge.sumproject.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pereberge.sumproject.R;
import pereberge.sumproject.Repository.ReservationRepository;
import pereberge.sumproject.adapter.TimetableAdapter;
import pereberge.sumproject.domain.Reservation;
import pereberge.sumproject.domain.Timetable;
import pereberge.sumproject.services.ReservationService;

public class TimetableActivity extends ListActivity {

    private String nom;
    private Context context = getApplicationContext();

    Calendar today = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    String data = df.format(today.getTime());

    List<Reservation> r = new ArrayList<>();

    static final String[] HORES =
            new String[] {  "11:00-12:30", "12:30-14:00", "14:00-15:30", "15:30-17:00",
                            "17:00-18:30", "18:30-20:00", "20:00-21:30","21:30-23:00"};

    Timetable timetable = new Timetable(data, HORES);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReservationRepository reserves = new ReservationRepository(context);
        ReservationService reservationService = new ReservationService(reserves);

        setContentView(R.layout.activity_timetable);

        r = reservationService.getReserves(data);
        setListAdapter(new TimetableAdapter(this, HORES, r));
        this.context = getApplicationContext();

        initialize();
    }

    private void initialize() {

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String hora = l.getSelectedItem().toString();
        for (Reservation a: r) {
            if(a.getHora().equals(hora)){
                Toast.makeText(this, "Pista Ocupada", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //Obtenir nom reserva
        Intent intent = new Intent(TimetableActivity.this, ReservationActivity.class);
        intent.putExtra("hora",hora);
        intent.putExtra("dia",today);
        startActivity(intent);
    }


    //no fa res
    private void modifica(View v, int position) {
        timetable.reservaPista(position, nom, data);
        TextView t = (TextView) v.findViewById(R.id.nomReserva);
        t.setText(nom);
        LinearLayout item = (LinearLayout) v.findViewById(R.id.item);
        item.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        item.refreshDrawableState();
    }

}
