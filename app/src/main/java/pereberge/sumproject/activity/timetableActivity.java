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
import pereberge.sumproject.Repository.ReservaRepository;
import pereberge.sumproject.adapter.horariAdapter;
import pereberge.sumproject.domain.Horari;
import pereberge.sumproject.domain.Reserva;
import pereberge.sumproject.services.ReservaService;

public class timetableActivity extends ListActivity {

        private String nom;
        private Context context = getApplicationContext();

        Calendar today = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String data = df.format(today.getTime());

        List<Reserva> r = new ArrayList<>();


        static final String[] HORES =
                new String[] { "11:00-12:30", "12:30-14:00", "14:00-15:30", "15:30-17:00", "17:00-18:30", "18:30-20:00", "20:00-21:30","21:30-23:00"};

        Horari horari = new Horari(data, HORES);

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            ReservaRepository reserves = new ReservaRepository(context);
            ReservaService reservaService = new ReservaService(reserves);

            setContentView(R.layout.activity_timetable);



            r = reservaService.getReserves(data);
            setListAdapter(new horariAdapter(this, HORES, r));
            this.context = getApplicationContext();

            initialize();
        }

    private void initialize() {

    }

    @Override
        protected void onListItemClick(ListView l, View v, int position, long id) {
            String hora = l.getSelectedItem().toString();
            for(Reserva a: r){
                if(a.getHora().equals(hora)){
                    Toast.makeText(this, "Pista Ocupada", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            //Obtenir nom reserva
            Intent intent = new Intent(timetableActivity.this, ReservaActivity.class);
            intent.putExtra("hora",hora);
            intent.putExtra("dia",today);
            startActivity(intent);
        }


//no fa res
    private void modifica(View v, int position) {
        horari.reservaPista(position, nom, data);
        TextView t = (TextView) v.findViewById(R.id.nomReserva);
        t.setText(nom);
        LinearLayout item = (LinearLayout) v.findViewById(R.id.item);
        item.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        item.refreshDrawableState();
    }

}
