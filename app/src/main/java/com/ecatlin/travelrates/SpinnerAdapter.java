package com.ecatlin.travelrates;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Deals with custom spinner
 * Created by paul on 21/02/2018.
 */

public class SpinnerAdapter extends ArrayAdapter<Currency> {

    private int groupid;
    private ArrayList<Currency> list;
    LayoutInflater inflater;

    public SpinnerAdapter(Activity context, int groupid, int id, ArrayList<Currency> list){
        super(context,id,list);
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View itemView=inflater.inflate(groupid,parent,false);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.flag);
        imageView.setImageResource(list.get(position).getFlag());
        TextView code=(TextView)itemView.findViewById(R.id.currencycode);
        code.setText(list.get(position).getCurrencyCode());
        TextView name=(TextView)itemView.findViewById(R.id.currencyname);
        name.setText(list.get(position).getCurrencyNameId());
        TextView rate=(TextView)itemView.findViewById(R.id.rateText);
//        rate.setText(list.get(position).getStringRate());
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getView(position,convertView,parent);
    }
}
