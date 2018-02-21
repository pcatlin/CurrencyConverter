package com.ecatlin.travelrates;

import java.util.Locale;

/**
 * Currency code and rate class definition
 *
 * Created by paul on 19/03/2017.
 */
class Currency{
    private String mCurrencyCode;
    private double mRate;
    private String mCurrencyName;
    private Integer mFlagImageId;

    // TODO add country name(s)

    Currency(String currency, double rate) {
        mCurrencyCode = currency;
        mRate = rate;
    }

    double getRate() {
        return mRate;
    }

    String getStringRate() {

        if(mRate == (long) mRate)
            return String.format(Locale.UK,"%d",(long)mRate);
        else
            return String.format("%s",mRate);
    }

    String getCurrencyCode() {
        return mCurrencyCode;
    }

    public Integer getFlag(){
        return mFlagImageId;
    }

    public String getCurrencyName(){
        return mCurrencyName;
    }

    public void addFlagAndName(){
        // search images and text for this currency and add them to the local variables



    }

}
