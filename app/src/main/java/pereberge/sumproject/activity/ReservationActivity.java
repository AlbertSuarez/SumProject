package pereberge.sumproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import pereberge.sumproject.R;
import pereberge.sumproject.domain.Reservation;
import pereberge.sumproject.services.ReservationService;
import pereberge.sumproject.utils.DateUtils;
import pereberge.sumproject.utils.ServiceFactory;

public class ReservationActivity extends AppCompatActivity {

    public static final String INTENT_RESERVATION = "DATE";

    private EditText editTextName;
    private String name;
    private ReservationService reservationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle("Reserva");
        setSupportActionBar(toolbar);

        TextView day = (TextView) findViewById(R.id.day);
        TextView hour = (TextView) findViewById(R.id.hour);
        editTextName = (EditText) findViewById(R.id.newName);
        Button book = (Button) findViewById(R.id.botoReserva);

        reservationService = ServiceFactory.getReservationService(getApplicationContext());

        final Date date = DateUtils.millisToDate(getIntent().getLongExtra(INTENT_RESERVATION, 0));

        assert day != null;
        day.setText(DateUtils.dayToString(date));
        //assert hour != null;
        //hour.setText(DateUtils.hourToString(date));

        assert book != null;
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextName.getText().toString();
                if (!name.isEmpty()) {
                    Reservation reservation = new Reservation(name, date);
                    reservationService.save(reservation);
                    Toast.makeText(ReservationActivity.this, "Reserva realitzada", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}