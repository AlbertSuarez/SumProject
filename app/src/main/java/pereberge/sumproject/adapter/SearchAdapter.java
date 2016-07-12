package pereberge.sumproject.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.List;

import pereberge.sumproject.R;
import pereberge.sumproject.activity.ReservationActivity;

public class SearchAdapter extends CursorAdapter {

    private List<String> items;
    private TextView text;

    public SearchAdapter(Context context, Cursor cursor, List<String> items) {
        super(context, cursor, false);
        this.items = items;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item, parent, false);
        text = (TextView) view.findViewById(R.id.text);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int index = cursor.getInt(0);
        if (index >= 0 && index < items.size()) text.setText(items.get(index));
        else text.setText(ReservationActivity.EMPTY_SEARCH);
    }
}
