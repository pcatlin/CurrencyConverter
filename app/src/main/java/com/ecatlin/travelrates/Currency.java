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

    public void setRate(double rate) {
        this.mRate = rate;
    }

    String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrency(String currency) {
        this.mCurrencyCode = currency;
    }
}
