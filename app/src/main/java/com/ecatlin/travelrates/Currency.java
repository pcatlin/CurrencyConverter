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
    private Integer mCurrencyNameId;
    private Integer mFlagImageId;

    // TODO add country name(s)

    Currency(String currency, double rate) {
        mCurrencyCode = currency;
        mRate = rate;

        addFlagAndName();
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

    public Integer getCurrencyNameId(){
        return mCurrencyNameId;
    }

    private void addFlagAndName(){
        // search images and text for this currency and add them to the local variables

        mFlagImageId = R.drawable.us;

        switch(mCurrencyCode){
            case "USD": mCurrencyNameId = R.string.USD;
                        mFlagImageId = R.drawable.us;
                        break;
            case "JPY": mCurrencyNameId = R.string.JPY;
                break;
            case "BGN": mCurrencyNameId = R.string.BGN;
                break;
            case "CZK": mCurrencyNameId = R.string.CZK;
                break;
            case "DKK": mCurrencyNameId = R.string.DKK;
                break;
            case "GBP": mCurrencyNameId = R.string.GBP;
                break;
            case "EUR": mCurrencyNameId = R.string.EUR;
                break;
            case "HUF": mCurrencyNameId = R.string.HUF;
                break;
            case "PLN": mCurrencyNameId = R.string.PLN;
                break;
            case "RON": mCurrencyNameId = R.string.RON;
                break;
            case "SEK": mCurrencyNameId = R.string.SEK;
                break;
            case "CHF": mCurrencyNameId = R.string.CHF;
                break;
            case "ISK": mCurrencyNameId = R.string.ISK;
                break;
            case "NOK": mCurrencyNameId = R.string.NOK;
                break;
            case "HRK": mCurrencyNameId = R.string.HRK;
                break;
            case "RUB": mCurrencyNameId = R.string.RUB;
                break;
            case "TRY": mCurrencyNameId = R.string.TRY;
                break;
            case "AUD": mCurrencyNameId = R.string.AUD;
                break;
            case "BRL": mCurrencyNameId = R.string.BRL;
                break;
            case "CAD": mCurrencyNameId = R.string.CAD;
                break;
            case "CNY": mCurrencyNameId = R.string.CNY;
                break;
            case "HKD": mCurrencyNameId = R.string.HKD;
                break;
            case "IDR": mCurrencyNameId = R.string.IDR;
                break;
            case "ILS": mCurrencyNameId = R.string.ILS;
                break;
            case "INR": mCurrencyNameId = R.string.INR;
                break;
            case "KRW": mCurrencyNameId = R.string.KRW;
                break;
            case "MXN": mCurrencyNameId = R.string.MXN;
                break;
            case "MYR": mCurrencyNameId = R.string.MYR;
                break;
            case "NZD": mCurrencyNameId = R.string.NZD;
                break;
            case "PHP": mCurrencyNameId = R.string.PHP;
                break;
            case "SGD": mCurrencyNameId = R.string.SGD;
                break;
            case "THB": mCurrencyNameId = R.string.THB;
                break;
            case "ZAR": mCurrencyNameId = R.string.ZAR;
                break;

        }

    }

}
