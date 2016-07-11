package pereberge.sumproject.adapter;

import android.content.Context;
import android.util.AndroidRuntimeException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import pereberge.sumproject.R;
import pereberge.sumproject.activity.TimetableActivity;
import pereberge.sumproject.domain.Reservation;
import pereberge.sumproject.utils.DateUtils;

public class TimetableAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final List<Reservation> reservations;
    private final Boolean today;

    public TimetableAdapter(Context context, String[] values, List<Reservation> reservations, Boolean today) {
        super(context, R.layout.horari_item_list, values);
        this.context = context;
        this.values = values;
        this.reservations = reservations;
        this.today = today;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.horari_item_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.hour);
        textView.setText(values[position]);

        Integer hour = DateUtils.timeZoneCodes.get(position).first;
        Integer minute = DateUtils.timeZoneCodes.get(position).second;

        LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.timetable_layout);
        TextView nom = (TextView) rowView.findViewById(R.id.nameReservation);

        for (Reservation reservation : reservations){
            if (DateUtils.isSameHour(reservation.getDate(), hour, minute)) {
                nom.setText(reservation.getPerson());
                layout.setBackgroundColor(context.getResources().getColor(R.color.dividerColor));
                rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Pista Ocupada", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        if(today){
            if(DateUtils.isPassed(DateUtils.createDateOfToday(hour, minute))){
                layout.setBackgroundColor(context.getResources().getColor(R.color.dividerColor));
                rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Ja no es pot reservar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        return rowView;
    }

}
