package pereberge.sumproject.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pereberge.sumproject.R;
import pereberge.sumproject.adapter.TimetableAdapter;
import pereberge.sumproject.domain.Reservation;
import pereberge.sumproject.domain.Timetable;
import pereberge.sumproject.services.ReservationService;
import pereberge.sumproject.utils.ServiceFactory;

public class TimetableActivity extends ListActivity {

    private String nom;
    private ReservationService reservationService;
    private Switch swit;

    Calendar calendar = Calendar.getInstance();
    Date today = calendar.getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    String todayDate = df.format(today.getTime());
    //FALTA SUMAR UN DIA
    Date tomorrow = calendar.getTime();
    String tomorrowDate = df.format(today);

    List<Reservation> r = new ArrayList<>();

    static final String[] HORES =
            new String[]{"11:00-12:30", "12:30-14:00", "14:00-15:30", "15:30-17:00",
                    "17:00-18:30", "18:30-20:00", "20:00-21:30", "21:30-23:00"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //reservationService = ServiceFactory.getReservationService(getApplicationContext());
        setContentView(R.layout.activity_timetable);
        initialize();
    }

    private void initialize() {
        Button tomorrow = (Button) findViewById(R.id.dema);
        tomorrow.setBackgroundColor(getResources().getColor(R.color.blueLight));
        tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button today = (Button) findViewById(R.id.avui);
                Button tomorrow = (Button) findViewById(R.id.dema);
                today.setBackgroundColor(getResources().getColor(R.color.blueLight));
                tomorrow.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tomorrow.setTypeface(tomorrow.getTypeface(), Typeface.BOLD);
                today.setTypeface(today.getTypeface(),Typeface.NORMAL);
            }
        });
        Button today = (Button) findViewById(R.id.avui);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button tomorrow = (Button) findViewById(R.id.dema);
                Button today = (Button) v.findViewById(R.id.avui);
                tomorrow.setBackgroundColor(getResources().getColor(R.color.blueLight));
                today.setTypeface(tomorrow.getTypeface(), Typeface.BOLD);
                today.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tomorrow.setTypeface(today.getTypeface(),Typeface.NORMAL);
            }
        });
        //r = reservationService.getReserves(todayDate);
        setListAdapter(new TimetableAdapter(this, HORES, r));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String hora = l.getItemAtPosition(position).toString();
        for (Reservation a : r) {
            if (a.getHora().equals(hora)) {
                Toast.makeText(this, "Pista Ocupada", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //Obtenir nom reserva
        Intent intent = new Intent(TimetableActivity.this, ReservationActivity.class);
        intent.putExtra("hora", hora);
        intent.putExtra("dia", today);
        startActivity(intent);
    }
}
