package com.example.doancalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Thu> {
    public ListAdapter(Context context, int textViewResourceId){
        super(context, textViewResourceId);
    }
    public ListAdapter(Context context, int resource, List<Thu> items){
        super(context, resource, items);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.tuan,null);
        }
        Thu p = getItem(position);
        if(p!=null){
            TextView thu = (TextView) v.findViewById(R.id.txtThu);
            thu.setText(p.Thu);
            TextView sang = (TextView) v.findViewById(R.id.txtSang);
            sang.setText(p.Sang);

            TextView chieu = (TextView) v.findViewById(R.id.txtChieu);
            chieu.setText(p.Chieu);

            TextView toi = (TextView) v.findViewById(R.id.txtToi);
            toi.setText(p.Toi);

        }
        return v;
    }
}
