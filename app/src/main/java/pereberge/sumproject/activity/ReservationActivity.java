package pereberge.sumproject.activity;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import pereberge.sumproject.R;
import pereberge.sumproject.adapter.SearchAdapter;
import pereberge.sumproject.domain.Reservation;
import pereberge.sumproject.services.ReservationService;
import pereberge.sumproject.utils.DateUtils;
import pereberge.sumproject.utils.SecurityUtils;
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
        toolbar.setTitle(R.string.reservation);
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
        Button book = (Button) findViewById(R.id.reservation_button);

        final EditText firstPassword = (EditText) findViewById(R.id.password_1);
        final EditText secondPassword = (EditText) findViewById(R.id.password_2);
        assert firstPassword != null;
        assert secondPassword != null;

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
                String firstPsw = SecurityUtils.convertPassMd5(firstPassword.getText().toString());
                String secondPsw = SecurityUtils.convertPassMd5(secondPassword.getText().toString());
                if (name.isEmpty() || !items.contains(name))
                    Snackbar.make(findViewById(R.id.reservation_layout), R.string.wrong_partner, Snackbar.LENGTH_SHORT).show();
                else if (reservationService.existsReservationSameDay(name, date))
                    Snackbar.make(findViewById(R.id.reservation_layout), R.string.busy_timetable, Snackbar.LENGTH_SHORT).show();
                else if (!reservationService.canPlay(name))
                    Snackbar.make(findViewById(R.id.reservation_layout), R.string.no_play_last_reservation, Snackbar.LENGTH_SHORT).show();
                else if (firstPassword.getText().toString().isEmpty() || secondPassword.getText().toString().isEmpty())
                    Snackbar.make(findViewById(R.id.reservation_layout), R.string.empty_password, Snackbar.LENGTH_SHORT).show();
                else if (!firstPsw.equals(secondPsw))
                    Snackbar.make(findViewById(R.id.reservation_layout), R.string.different_password, Snackbar.LENGTH_SHORT).show();
                else {
                    Reservation reservation = new Reservation(name, date, firstPsw);
                    reservationService.save(reservation);
                    Intent intent = new Intent(ReservationActivity.this, TimetableActivity.class);
                    intent.putExtra(TimetableActivity.INTENT_RETURN, reservation.getId());
                    setResult(RESULT_OK, intent);
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