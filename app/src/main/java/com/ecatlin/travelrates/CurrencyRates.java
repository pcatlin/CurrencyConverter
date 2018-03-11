package com.ecatlin.travelrates;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Currency Rates class - stores currencies and their rates
 * Adds from json to the array
 * Created by paul on 11/03/2017.
 */

class CurrencyRates {

    private String mConvertTo;
    private Date mDateUpdated;
    List<String> mCurrencyCodes;
    ArrayList<Currency> mCurrencies;
    private String mJson;


    CurrencyRates(){
        mConvertTo = "";
        mDateUpdated = null;
        mCurrencies = new ArrayList<>();
        mCurrencyCodes = new ArrayList<>();
    }

    void Add(Currency C){
        mCurrencies.add(C);
        mCurrencyCodes.add(C.getCurrencyCode());
    }

    String getConvertTo() {
        return mConvertTo;
    }

    void setConvertTo(String mConvertTo) {
        this.mConvertTo = mConvertTo;
    }

    Currency findCurrencyFromCode(String code){
        for (Currency c: mCurrencies) {
            if(code.equals(c.getCurrencyCode())) return c;
        }
        return null;
    }
    
    void setCustomRate(double rate){
        mCurrencies.remove(mCurrencies.size() - 1); // remove last item (previous custom rate)
        mCurrencies.add(new Currency("?",rate));
    }

    String getCustomRateString(){
        Currency custom = mCurrencies.get(mCurrencies.size() - 1);
        if(custom.getCurrencyCode().equals("?")) return custom.getStringRate();
        else return "";
    }

    Double getCustomRate(){
        Currency custom = mCurrencies.get(mCurrencies.size() - 1);
        if(custom.getCurrencyCode().equals("?")) return custom.getRate();
        else return 1.0;
    }

    String getDateUpdated() {
        if(mDateUpdated==null) return null;
        else{
            SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
            return df.format(mDateUpdated);
        }
    }

    void Empty(){
        mCurrencyCodes.clear();
        mCurrencies.clear();
        mDateUpdated = null;
    }

    String getJSON(){
        return mJson;
    }

    Boolean parseJSONrates(String ratesJSON){

        mJson = ratesJSON;

        /*
        http://api.fixer.io/latest?base=GBP
        {
            "base": "GBP",
            "date": "2017-03-10",
            "rates": {
                "AUD": 1.6155,
                "CAD": 1.6415,...
                }
        }
        */
        try {

            JSONObject root =  new JSONObject(ratesJSON);
            String base = root.getString("base");
            String dateString = root.getString("date");
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
            Date date;
            try {
                date = fmt.parse(dateString);
            }
            catch(ParseException pe) {
                Log.e("parseJSON", "Problem parsing the JSON date");
                date = new Date();
            }

            this.Empty();
            mConvertTo = base;
            mDateUpdated = date;


            JSONObject rates = root.getJSONObject("rates");
            Iterator<String> keysIterator = rates.keys();
            while (keysIterator.hasNext())
            {
                String currencyCode = keysIterator.next();
                double rate = rates.getDouble(currencyCode);

                if(!currencyCode.equals("") && rate!=0){
                    this.Add(new Currency(currencyCode, rate));
                    // match currency
                }
            }

            //Log.d("parseJSON", "Parsed JSON dated: " + cr.getDateUpdated());
            return true;


        } catch (JSONException e) {
            Log.e("parseJSON", "Problem parsing the JSON results", e);
            return false;
        }

    }

}

