package com.ecatlin.travelrates;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.String.copyValueOf;
import static java.lang.String.valueOf;

/**
 * Cache class manages rates and user prefs/history
 * Created by paul on 19/03/2017.
 */

class Cache {

    private String filename;
    private Date modified;
    private String type;

    Cache(String theType){

        switch(theType){
            case "prefs":
                filename = "Prefs.json";
                modified = null;
                type = theType;
                break;
            case "rates":
                filename = "CurrencyRates.json";
                modified = null;
                type = theType;
                break;
        }

    }



    void write(Context c, String json){

        try {
            FileOutputStream fos = c.openFileOutput(filename, MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
            //Log.d("FileWrite", "JSON written to file");
        }catch(Exception error) {
            Log.e("FileWrite", error.toString());
        }
    }

    long cacheAge(Context c){

        File file = c.getFileStreamPath(filename);

        if(!file.exists()) return -1;

        Date lastModDate = new Date(file.lastModified());
        Date today = new Date();

        long diff = today.getTime() - lastModDate.getTime();

        Log.d("CACHE", "Today: " + today.toString());
        Log.d("CACHE","Cache file modified: " + lastModDate.toString());
        Log.d("CACHE","Cache file age: " + valueOf(diff));

        return diff;
    }

    Boolean cacheOld(Context c){

        File file = c.getFileStreamPath(filename);

        if(!file.exists()) return true; // cache file doesn't exist, so cache is old

        Date lastModDate = new Date(file.lastModified());
        Date now = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(lastModDate);
        int modifiedDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        int modifiedHour = cal.get(Calendar.HOUR);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(now);
        int dayOfYear = cal2.get(Calendar.DAY_OF_YEAR);
        int hour = cal2.get(Calendar.HOUR);


        if(modifiedDayOfYear==dayOfYear){ // today is the same day as the cache date

            // expire cache after 4pm
            if(modifiedHour<17 && hour>16) return true;
            else return false;

        }else return true;

    }

    String read(Context c){

        StringBuilder fileContents = new StringBuilder();
        int READ_BLOCK_SIZE = 100;

        File file = c.getFileStreamPath(filename);
        if(!file.exists()) return "";


        try {
            FileInputStream fis = c.openFileInput(filename);
            InputStreamReader InputRead = new InputStreamReader(fis);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];

            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring= copyValueOf(inputBuffer,0,charRead);
                fileContents.append(readstring);
            }
            InputRead.close();
            //Log.d("FileRead", "File read: " + fileContents);
        }catch(Exception error) {
            Log.e("FileRead", error.toString());
        }

        return fileContents.toString();
    }

}
