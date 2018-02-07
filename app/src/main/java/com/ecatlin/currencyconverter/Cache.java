package com.ecatlin.currencyconverter;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.String.*;

/**
 * Created by paul on 19/03/2017.
 */

class Cache {
    static void writeRatesToFile(Context c, String ratesJSON){

        String FILENAME = "CurrencyRates.json";

        try {
            FileOutputStream fos = c.openFileOutput(FILENAME, MODE_PRIVATE);
            fos.write(ratesJSON.getBytes());
            fos.close();
            Log.d("FileWrite", "JSON written to file");
        }catch(Exception error) {
            Log.e("FileWrite", error.toString());
        }
    }

    static long cacheAge(Context c){

        String FILENAME = "CurrencyRates.json";
        File file = c.getFileStreamPath(FILENAME);

        if(!file.exists()) return -1;

        Date lastModDate = new Date(file.lastModified());
        Date today = new Date();
        long diff = today.getTime() - lastModDate.getTime();
        Log.d("cacheAge","Cache file modified: " + lastModDate.toString());
        Log.d("cacheAge","Cache file age: " + valueOf(diff));

        return diff;
    }

    static String readRatesFromFile(Context c){

        String FILENAME = "CurrencyRates.json";
        String fileContents = "";
        int READ_BLOCK_SIZE = 100;

        File file = c.getFileStreamPath(FILENAME);
        if(!file.exists()) return "";


        try {
            FileInputStream fis = c.openFileInput(FILENAME);
            InputStreamReader InputRead = new InputStreamReader(fis);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];

            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring= copyValueOf(inputBuffer,0,charRead);
                fileContents += readstring;
            }
            InputRead.close();
            Log.d("FileRead", "File read: " + fileContents);
        }catch(Exception error) {
            Log.e("FileRead", error.toString());
        }

        return fileContents;
    }

}
