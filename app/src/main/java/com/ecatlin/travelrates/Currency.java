package com.ecatlin.travelrates;

/**
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
