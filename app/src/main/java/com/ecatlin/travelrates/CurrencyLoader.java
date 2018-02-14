package com.ecatlin.travelrates;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import static com.ecatlin.travelrates.Cache.writeRatesToFile;

/**
 * Created by paul on 19/03/2017.
 */

class CurrencyLoader extends AsyncTaskLoader<CurrencyRates> {

    private String mURL;
    private Context mContext;

    public CurrencyLoader(Context context, String url) {
        super(context);
        mContext = context;
        mURL = url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    public CurrencyRates loadInBackground() {
        if(mURL == null){
            return null;
        }

        return fetchRates(mURL);

    }

    private CurrencyRates fetchRates(String requestURL){

        // Create URL object
        URL url = createUrl(requestURL);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("HTTP", "Problem making the HTTP request.", e);
        }


        CurrencyRates cr = parseJSONrates(jsonResponse);
        if(cr != null) writeRatesToFile(mContext, jsonResponse);

        return cr;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("HTTP", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("HTTP", "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
            Log.d("HTTP","HTTP connection made OK");
        }
        return jsonResponse;
    }


    static CurrencyRates parseJSONrates(String ratesJSON){

        CurrencyRates cr;
        /*
        http://api.fixer.io/latest?base=GBP
        {
            "base": "GBP",
            "date": "2017-03-10",
            "rates": {
                "AUD": 1.6155,
                "CAD": 1.6415,
                "EUR": 1.1461
                }
        }
        */
        try {

            JSONObject root =  new JSONObject(ratesJSON);
            String base = root.getString("base");
            String dateString = root.getString("date");
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
            Date date;
            try {
                date = fmt.parse(dateString);
            }
            catch(ParseException pe) {
                Log.e("parseJSON", "Problem parsing the JSON date");
                date = new Date();
            }

            cr = new CurrencyRates(base, date);

            JSONObject rates = root.getJSONObject("rates");
            Iterator<String> keysIterator = rates.keys();
            while (keysIterator.hasNext())
            {
                String currencyCode = keysIterator.next();
                double rate = rates.getDouble(currencyCode);

                if(!currencyCode.equals("") && rate!=0) cr.Add(new Currency(currencyCode, rate));
            }

            cr.Add(new Currency("?", 1));  // custom rate
            Log.d("parseJSON", "Parsed JSON dated: " + cr.getDateUpdated());
            return cr;

        } catch (JSONException e) {
            Log.e("parseJSON", "Problem parsing the JSON results", e);
            return null;
        }

    }



    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        Log.d("NETREAD","Read from net" + output.toString());
        return output.toString();
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
            Log.d("URL", "Created URL: " + url.toString());
        } catch (MalformedURLException e) {
            Log.e("URL", "Problem building the URL ", e);
        }
        return url;
    }
}
