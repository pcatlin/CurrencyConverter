package com.ecatlin.travelrates;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by paul on 11/03/2017.
 */

class CurrencyRates {

    private String mConvertTo;
    private Date mDateUpdated;
    List<String> mCurrencyCodes;
    ArrayList<Currency> mCurrencies;


    CurrencyRates(String base, Date date){
        mConvertTo = base;
        mDateUpdated = date;
        mCurrencies = new ArrayList<Currency>();
        mCurrencyCodes = new ArrayList<String>();
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

    String getDateUpdated() {
        SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        return df.format(mDateUpdated);
    }

    void Empty(){
        mCurrencies.clear();
        mDateUpdated = null;
    }


}

