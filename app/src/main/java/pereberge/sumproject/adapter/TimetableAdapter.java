package pereberge.sumproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import pereberge.sumproject.R;
import pereberge.sumproject.domain.Reservation;

public class TimetableAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final List<Reservation> r;

    public TimetableAdapter(Context context, String[] values, List<Reservation> r) {
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
        TextView state = (TextView) rowView.findViewById(R.id.estat);

        /*for(Reservation f:r){
            TextView nom = (TextView) rowView.findViewById(R.id.nomReserva);
            if(values[position].equals(f.getHora())){
                nom.setText(f.getPersonaReserva());
            }
            else{
                nom.setText("Lliure");
                nom.setTextColor(Integer.parseInt("4fa342", 16));
            }
        }*/


        // Change icon based on name
        String s = values[position];

        System.out.println(s);
        return rowView;
    }


}
