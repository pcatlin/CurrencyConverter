package com.ecatlin.travelrates;

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

    String read(Context c){

        String fileContents = "";
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
                fileContents += readstring;
            }
            InputRead.close();
            //Log.d("FileRead", "File read: " + fileContents);
        }catch(Exception error) {
            Log.e("FileRead", error.toString());
        }

        return fileContents;
    }

}
