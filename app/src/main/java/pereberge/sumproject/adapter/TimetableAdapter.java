package pereberge.sumproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pereberge.sumproject.R;
import pereberge.sumproject.domain.Reservation;
import pereberge.sumproject.utils.DateUtils;

public class TimetableAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final List<Reservation> reservations;

    public TimetableAdapter(Context context, String[] values, List<Reservation> reservations) {
        super(context, R.layout.horari_item_list, values);
        this.context = context;
        this.values = values;
        this.reservations = reservations;
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

        for (Reservation reservation : reservations){
            TextView nom = (TextView) rowView.findViewById(R.id.nameReservation);
            if (DateUtils.isSameHour(reservation.getDate(), hour, minute)) {
                nom.setText(reservation.getPerson());
            }
        }

        return rowView;
    }


}
