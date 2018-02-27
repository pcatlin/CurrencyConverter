package com.ecatlin.travelrates;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Deals with custom spinner
 * Created by paul on 21/02/2018.
 */

public class SpinnerAdapter extends ArrayAdapter<Currency> {

    private int groupid;
    public CurrencyRates rates;
    LayoutInflater inflater;

    public SpinnerAdapter(Activity context, int groupid, int id, CurrencyRates rates){
        super(context,id,rates.mCurrencies);
        this.rates=rates;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View itemView=inflater.inflate(groupid,parent,false);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.flag);
        imageView.setImageResource(rates.mCurrencies.get(position).getFlag());
        TextView code=(TextView)itemView.findViewById(R.id.currencycode);
        code.setText(rates.mCurrencies.get(position).getCurrencyCode());
        TextView name=(TextView)itemView.findViewById(R.id.currencyname);
        name.setText(rates.mCurrencies.get(position).getCurrencyNameId());
//        TextView rate=(TextView)itemView.findViewById(R.id.rateText);
//        rate.setText(list.get(position).getStringRate());
        return itemView;
    }

    public Currency getCurrency(int position){
        return rates.mCurrencies.get(position);
    }

    @Override
    public int getPosition(@Nullable Currency item) {
        return super.getPosition(item);
    }


    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getView(position,convertView,parent);
    }

    public void addCustomRate(double customRate) {
        rates.Add(new Currency(getContext().getString(R.string.customRateCode), customRate));  // custom rate
        notifyDataSetChanged();
    }

    public void updateRates(List<Currency> newRates){
        this.clear();
        this.addAll(newRates);
        notifyDataSetChanged();
    }


}
