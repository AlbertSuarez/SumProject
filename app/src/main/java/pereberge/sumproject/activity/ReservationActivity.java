package pereberge.sumproject.activity;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.Date;
import java.util.List;

import pereberge.sumproject.R;
import pereberge.sumproject.adapter.SearchAdapter;
import pereberge.sumproject.domain.Reservation;
import pereberge.sumproject.services.ReservationService;
import pereberge.sumproject.utils.DateUtils;
import pereberge.sumproject.utils.ServiceFactory;

public class ReservationActivity extends AppCompatActivity {

    public static final String INTENT_RESERVATION = "DATE";

    private String name;
    private ReservationService reservationService;

    private List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle("Reserva");
        setSupportActionBar(toolbar);

        reservationService = ServiceFactory.getReservationService(getApplicationContext());
        this.items = reservationService.getPartnerNames();

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView search = (SearchView) findViewById(R.id.newName);
        assert search != null;
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                loadHistory(query);
                return true;
            }
        });

        TextView day = (TextView) findViewById(R.id.day);
        TextView hour = (TextView) findViewById(R.id.timeZone);
        Button book = (Button) findViewById(R.id.botoReserva);

        final Date date = DateUtils.millisToDate(getIntent().getLongExtra(INTENT_RESERVATION, 0));

        assert day != null;
        day.setText(DateUtils.dayToString(date));
        assert hour != null;
        hour.setText(DateUtils.hourToString(date));

        assert book != null;
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = search.getQuery().toString();
                if (!name.isEmpty()) {
                    Reservation reservation = new Reservation(name, date);
                    reservationService.save(reservation);
                    Toast.makeText(ReservationActivity.this, "Reserva realitzada", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void loadHistory(String query) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            String[] columns = new String[] { "_id", "text" };
            Object[] temp = new Object[] { 0, "default" };

            MatrixCursor cursor = new MatrixCursor(columns);

            for (int i = 0; i < items.size(); i++) {
                if (cleanString(items.get(i).toUpperCase()).contains(cleanString(query.toUpperCase()))) {
                    temp[0] = i;
                    temp[1] = items.get(i);
                    cursor.addRow(temp);
                }
            }
            final SearchView search = (SearchView) findViewById(R.id.newName);
            assert search != null;
            search.setSuggestionsAdapter(new SearchAdapter(this, cursor, items));
        }
    }

    public static String cleanString(String text) {
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return text;
    }

}