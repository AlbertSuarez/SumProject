package pereberge.sumproject.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import pereberge.sumproject.R;
import pereberge.sumproject.adapter.TimetableAdapter;
import pereberge.sumproject.domain.Reservation;
import pereberge.sumproject.services.ReservationService;
import pereberge.sumproject.utils.DateUtils;
import pereberge.sumproject.utils.ServiceFactory;

public class TimetableActivity extends ListActivity {

    private Button today;
    private Button tomorrow;
    private Boolean todaySelected = true;

    public static final String INTENT_RETURN = "IntentReturn";
    private static final String[] timeZones =
            new String[]{"11:00-12:30", "12:30-14:00", "14:00-15:30", "15:30-17:00",
                    "17:00-18:30", "18:30-20:00", "20:00-21:30", "21:30-23:00"};

    private ReservationService service;
    private List<Reservation> reservationsOfDaySelected;
    private LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        inflater = LayoutInflater.from(getApplicationContext());
        service = ServiceFactory.getReservationService(getApplicationContext());
        initialize();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        updateView();
        final String reservationId = data.getStringExtra(INTENT_RETURN);
        Snackbar.make(findViewById(R.id.timetable_activity_layout), R.string.reservation_completed, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        service.delete(reservationId);
                        updateView();
                        Snackbar.make(findViewById(R.id.timetable_activity_layout), R.string.undo_reservation_done, Snackbar.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void initialize() {
        today = (Button) findViewById(R.id.today);
        tomorrow = (Button) findViewById(R.id.tomorrow);

        ImageButton info = (ImageButton) findViewById(R.id.about);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimetableActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        tomorrow.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                today.setTypeface(today.getTypeface(), Typeface.NORMAL);
                tomorrow.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                tomorrow.setTypeface(tomorrow.getTypeface(), Typeface.BOLD);
                reservationsOfDaySelected = service.getReservationsByTomorrow();
                todaySelected = false;
                setAdapter();
            }
        });
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomorrow.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tomorrow.setTypeface(today.getTypeface(), Typeface.NORMAL);
                today.setTypeface(tomorrow.getTypeface(), Typeface.BOLD);
                today.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                reservationsOfDaySelected = service.getReservationsByToday();
                todaySelected = true;
                setAdapter();
            }
        });
        reservationsOfDaySelected = service.getReservationsByToday();
        setAdapter();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final TextView name = (TextView) v.findViewById(R.id.nameReservation);
        final EditText password = (EditText) inflater.inflate(getResources().getLayout(R.layout.password), null);
        if (!name.getText().toString().isEmpty()) {
            new AlertDialog.Builder(TimetableActivity.this)
                .setTitle(R.string.reservation_cancelation_verb)
                .setMessage(R.string.dialog_text)
                .setView(password)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (service.deleteSpecificReservation(name.getText().toString(), todaySelected, password.getText().toString())) {
                            updateView();
                            Snackbar.make(findViewById(R.id.timetable_activity_layout), R.string.reservation_cancelation, Snackbar.LENGTH_SHORT).show();
                        }
                        else {
                            Snackbar.make(findViewById(R.id.timetable_activity_layout), R.string.wrong_password, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
        }
        else {
            Pair<Integer, Integer> pair = DateUtils.timeZoneCodes.get(position);
            Date date;
            if (todaySelected) date = DateUtils.createDateOfToday(pair.first, pair.second);
            else date = DateUtils.createDateOfTomorrow(pair.first, pair.second);

            Intent intent = new Intent(TimetableActivity.this, ReservationActivity.class);
            intent.putExtra(ReservationActivity.INTENT_RESERVATION, date.getTime());
            startActivityForResult(intent, 0);
        }
    }

    private void updateView() {
        if (todaySelected) reservationsOfDaySelected = service.getReservationsByToday();
        else reservationsOfDaySelected = service.getReservationsByTomorrow();
        setAdapter();
    }

    private void setAdapter() {
        setListAdapter(new TimetableAdapter(this, timeZones, reservationsOfDaySelected, todaySelected));
    }

}
