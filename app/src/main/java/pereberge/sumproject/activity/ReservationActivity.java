package pereberge.sumproject.activity;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
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

@SuppressWarnings("all")
public class ReservationActivity extends AppCompatActivity {

    public static final String INTENT_RESERVATION = "DATE";
    public static final String EMPTY_SEARCH = "No hi ha resultats.";
    public static SearchView search;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        reservationService = ServiceFactory.getReservationService(getApplicationContext());
        this.items = reservationService.getPartnerNames();

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) findViewById(R.id.newName);
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
        search.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = search.getSuggestionsAdapter().getCursor();
                cursor.moveToPosition(position);
                String val = cursor.getString(cursor.getColumnIndex("text"));
                if (val != EMPTY_SEARCH) search.setQuery(val, true);
                return false;
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
                if (name.isEmpty() || !items.contains(name))
                    Toast.makeText(ReservationActivity.this, "Nom de soci buit o incorrecte", Toast.LENGTH_SHORT).show();
                else if (reservationService.existsReservationSameDay(name, date))
                    Toast.makeText(ReservationActivity.this, "El soci ja ha reservat pista el mateix dia", Toast.LENGTH_SHORT).show();
                else {
                    Reservation reservation = new Reservation(name, date);
                    reservationService.save(reservation);
                    Toast.makeText(ReservationActivity.this, "Reserva realitzada", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        search.onActionViewExpanded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reservation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            if (cursor.getCount() == 0) {
                temp[0] = items.size();
                temp[1] = EMPTY_SEARCH;
                cursor.addRow(temp);
            }
            search.setSuggestionsAdapter(new SearchAdapter(this, cursor, items));
        }
    }

    public static String cleanString(final String text) {
        String newText = Normalizer.normalize(text, Normalizer.Form.NFD);
        return newText.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

}