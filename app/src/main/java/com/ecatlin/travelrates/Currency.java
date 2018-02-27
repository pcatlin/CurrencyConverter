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

    public Currency(String currency, double rate) {
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

        switch(mCurrencyCode){
            case "USD": mCurrencyNameId = R.string.USD;
                mFlagImageId = R.drawable.usd;
                break;
            case "JPY": mCurrencyNameId = R.string.JPY;
                mFlagImageId = R.drawable.jpy;
                break;
            case "BGN": mCurrencyNameId = R.string.BGN;
                mFlagImageId = R.drawable.bgn;
                break;
            case "CZK": mCurrencyNameId = R.string.CZK;
                mFlagImageId = R.drawable.czk;
                break;
            case "DKK": mCurrencyNameId = R.string.DKK;
                mFlagImageId = R.drawable.dkk;
                break;
            case "GBP": mCurrencyNameId = R.string.GBP;
                mFlagImageId = R.drawable.gbp;
                break;
            case "EUR": mCurrencyNameId = R.string.EUR;
                mFlagImageId = R.drawable.eur;
                break;
            case "HUF": mCurrencyNameId = R.string.HUF;
                mFlagImageId = R.drawable.huf;
                break;
            case "PLN": mCurrencyNameId = R.string.PLN;
                mFlagImageId = R.drawable.pln;
                break;
            case "RON": mCurrencyNameId = R.string.RON;
                mFlagImageId = R.drawable.ron;
                break;
            case "SEK": mCurrencyNameId = R.string.SEK;
                mFlagImageId = R.drawable.sek;
                break;
            case "CHF": mCurrencyNameId = R.string.CHF;
                mFlagImageId = R.drawable.chf;
                break;
            case "ISK": mCurrencyNameId = R.string.ISK;
                mFlagImageId = R.drawable.isk;
                break;
            case "NOK": mCurrencyNameId = R.string.NOK;
                mFlagImageId = R.drawable.nok;
                break;
            case "HRK": mCurrencyNameId = R.string.HRK;
                mFlagImageId = R.drawable.hrk;
                break;
            case "RUB": mCurrencyNameId = R.string.RUB;
                mFlagImageId = R.drawable.rub;
                break;
            case "TRY": mCurrencyNameId = R.string.TRY;
                mFlagImageId = R.drawable.flag_try;
                break;
            case "AUD": mCurrencyNameId = R.string.AUD;
                mFlagImageId = R.drawable.aud;
                break;
            case "BRL": mCurrencyNameId = R.string.BRL;
                mFlagImageId = R.drawable.brl;
                break;
            case "CAD": mCurrencyNameId = R.string.CAD;
                mFlagImageId = R.drawable.cad;
                break;
            case "CNY": mCurrencyNameId = R.string.CNY;
                mFlagImageId = R.drawable.cny;
                break;
            case "HKD": mCurrencyNameId = R.string.HKD;
                mFlagImageId = R.drawable.hkd;
                break;
            case "IDR": mCurrencyNameId = R.string.IDR;
                mFlagImageId = R.drawable.idr;
                break;
            case "ILS": mCurrencyNameId = R.string.ILS;
                mFlagImageId = R.drawable.ils;
                break;
            case "INR": mCurrencyNameId = R.string.INR;
                mFlagImageId = R.drawable.inr;
                break;
            case "KRW": mCurrencyNameId = R.string.KRW;
                mFlagImageId = R.drawable.krw;
                break;
            case "MXN": mCurrencyNameId = R.string.MXN;
                mFlagImageId = R.drawable.mxn;
                break;
            case "MYR": mCurrencyNameId = R.string.MYR;
                mFlagImageId = R.drawable.myr;
                break;
            case "NZD": mCurrencyNameId = R.string.NZD;
                mFlagImageId = R.drawable.nzd;
                break;
            case "PHP": mCurrencyNameId = R.string.PHP;
                mFlagImageId = R.drawable.php;
                break;
            case "SGD": mCurrencyNameId = R.string.SGD;
                mFlagImageId = R.drawable.sgd;
                break;
            case "THB": mCurrencyNameId = R.string.THB;
                mFlagImageId = R.drawable.thb;
                break;
            case "ZAR": mCurrencyNameId = R.string.ZAR;
                mFlagImageId = R.drawable.zar;
                break;
            case "?": mCurrencyNameId = R.string.customRateName;
                mFlagImageId = R.drawable.unknownflag;
                break;

        }

    }

}
