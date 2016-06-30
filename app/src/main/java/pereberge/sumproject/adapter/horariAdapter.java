package pereberge.sumproject.adapter;

/**
 * Created by pere on 6/28/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pereberge.sumproject.R;
import pereberge.sumproject.domain.Reserva;

public class horariAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final List<Reserva> r;

    public horariAdapter(Context context, String[] values, List<Reserva> r) {
        super(context, R.layout.horari_item_list, values);
        this.context = context;
        this.values = values;
        this.r = r;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.horari_item_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.hora);
        textView.setText(values[position]);

        for(Reserva f:r){
            if(values[position].equals(f.getHora())){
                TextView nom = (TextView) rowView.findViewById(R.id.nomReserva);
                nom.setText(f.getPersonaReserva());
            }
        }
        // Change icon based on name
        String s = values[position];

        System.out.println(s);
        return rowView;
    }


}
