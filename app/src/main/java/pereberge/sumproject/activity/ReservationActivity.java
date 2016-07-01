package pereberge.sumproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pereberge.sumproject.R;
import pereberge.sumproject.domain.Reservation;
import pereberge.sumproject.services.ReservationService;
import pereberge.sumproject.utils.ServiceFactory;

public class ReservationActivity extends AppCompatActivity {

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
        TextView hour = (TextView) findViewById(R.id.hora);
        editTextName = (EditText) findViewById(R.id.newName);
        Button book = (Button) findViewById(R.id.botoReserva);

        reservationService = ServiceFactory.getReservationService(getApplicationContext());

        String dayExtra = getIntent().getStringExtra("dia");
        String hourExtra = getIntent().getStringExtra("hora");
        assert day != null;
        day.setText(dayExtra);
        assert hour != null;
        hour.setText(hourExtra);

        assert book != null;
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextName.getText().toString();
                if(!name.equals("Nom")) {
                    Reservation r = new Reservation(name, "hora");
                    reservationService.save(r);
                    Intent intent = new Intent(ReservationActivity.this, TimetableActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}