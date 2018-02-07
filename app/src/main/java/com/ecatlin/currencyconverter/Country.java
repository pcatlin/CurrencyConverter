package com.ecatlin.currencyconverter;

/**
 * Created by paul on 13/03/2017.
 */

class Country {
    private String Name;
    private String Code;
    private String CurrencyName;
    private String CurrencyCode;


    Country(String name, String code, String currencyName, String currencyCode) {
        Name = name;
        Code = code;
        CurrencyName = currencyName;
        CurrencyCode = currencyCode;
    }
}
