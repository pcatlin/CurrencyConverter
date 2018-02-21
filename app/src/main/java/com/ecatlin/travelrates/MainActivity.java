package com.ecatlin.travelrates;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.DecimalFormat;





public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<CurrencyRates>{

    private AdView mAdView;
    private CurrencyRates cr;
    private int chosenRateIndex;
    private Currency chosenCurrency;
    private String homeCurrency = "GBP";
    //private ArrayList<Country> Countries = new ArrayList<>();
    //private Country userNetworkCountry, userSIMCountry;
    private Cache userCache, ratesCache;



    // TODO settings dialog to change home currency

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        //LoaderManager loaderManager = getLoaderManager();

        ratesCache = new Cache("rates");
        userCache = new Cache("prefs");

        //populateCountries();
        //inHomeCountry(this);

        getCurrencyRates();

        // TODO remember last used currency

        chosenCurrency=cr.mCurrencies.get(0);

        // TODO move spinner loading to function
        // populate the spinner/dropdown box with currencies
        Spinner spinner = (Spinner) findViewById(R.id.convertTo);

        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_row, R.id.currencycode, cr.mCurrencies);

        // Specify the layout to use when the list of choices appears
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                chosenRateIndex = position; // when currency is changed store the index position

                // TODO when custom rate is chosen prompt for rate and set the rate in the variable

                chosenCurrency=cr.mCurrencies.get(chosenRateIndex);

                // set rate textview
                TextView rate = (TextView)findViewById(R.id.rateText);
                rate.setText(getString(R.string.rate, chosenCurrency.getStringRate()));

                //Log.d("SPINNER", "ChosenCurrency->getcode:" + chosenCurrency.getCurrencyCode() + " chosenCurrency->getRate:" + chosenCurrency.getRate() + "\n" + "ChosenRateIndex: " + chosenRateIndex);

                updateNumbers();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final EditText editCustomHome = (EditText) findViewById(R.id.editCustomHome);
        final EditText editCustomAway = (EditText) findViewById(R.id.editCustomAway);
        final TextView customAway = (TextView) findViewById(R.id.customAway);
        final TextView customHome = (TextView) findViewById(R.id.customhome);
        final DecimalFormat precision = new DecimalFormat("0.00");

        editCustomHome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0) {
                    double conversion = Double.parseDouble(s.toString());
                    conversion *= chosenCurrency.getRate();
                    customAway.setText(getString(R.string.currencyvalue, precision.format(conversion), chosenCurrency.getCurrencyCode()));
                }else customAway.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {  }
        });

        editCustomAway.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0) {
                    double conversion = Double.parseDouble(s.toString());
                    conversion /= chosenCurrency.getRate();
                    customHome.setText(getString(R.string.currencyvalue, precision.format(conversion), homeCurrency));
                }else customHome.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {  }
        });

        updateNumbers();

        mAdView = (AdView)findViewById(R.id.adView);
        View mDivider = findViewById(R.id.dividerline);

        if (BuildConfig.FLAVOR.equals("ads")) {

            /*
            admob app id        ca-app-pub-9612116433207542~2023116000
            admob ad unit id    ca-app-pub-9612116433207542/1858151398
            test banner id      ca-app-pub-3940256099942544/6300978111
            */

            MobileAds.initialize(this, "ca-app-pub-9612116433207542~2023116000");

            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("B016E2BD0ED7D45E64EF6901DAA24B31")
                    .build();
            mAdView.loadAd(adRequest);

        }else{
            mAdView.setVisibility(View.GONE);
            mDivider.setVisibility(View.GONE);
        }


    }

    @Override
    public Loader<CurrencyRates> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new CurrencyLoader(this, getString(R.string.url) + homeCurrency);
    }

    @Override
    public void onLoadFinished(Loader<CurrencyRates> loader, CurrencyRates downloadedRates) {

        if (downloadedRates != null) {
            cr = downloadedRates;

            ratesCache.write(this, downloadedRates.getJSON());

            TextView date = (TextView) findViewById(R.id.updatedText);
            date.setText(downloadedRates.getDateUpdated());
        }
    }

    @Override
    public void onLoaderReset(Loader<CurrencyRates> loader) {
        // Loader reset, so we can clear out our existing data.
        cr.Empty();
    }




    private void getCurrencyRates(){

        String tempJSON = "{\"base\":\"GBP\",\"date\":\"2017-03-10\",\"rates\":{\"AUD\":1.6155,\"BGN\":2.2416,\"BRL\":3.8618,\"CAD\":1.6415,\"CHF\":1.2313,\"CNY\":8.4053,\"CZK\":30.97,\"DKK\":8.5193,\"HKD\":9.4403,\"HRK\":8.5032,\"HUF\":357.9,\"IDR\":16259.0,\"ILS\":4.4798,\"INR\":80.999,\"JPY\":140.31,\"KRW\":1405.5,\"MXN\":24.032,\"MYR\":5.4133,\"NOK\":10.476,\"NZD\":1.7591,\"PHP\":61.067,\"PLN\":4.9581,\"RON\":5.2138,\"RUB\":71.858,\"SEK\":10.977,\"SGD\":1.7239,\"THB\":43.044,\"TRY\":4.5617,\"USD\":1.2156,\"ZAR\":16.124,\"EUR\":1.1461}}";

        // read cache file
        String rates = ratesCache.read(this);

        long fileAge = ratesCache.cacheAge(this);

        if(rates.equals("")){
            // no file
            // use old hardcoded data for now
            rates = tempJSON;

            // and start loading new data from the web
            getLoaderManager().initLoader(0, null, this);

            Log.d("getRates","No cache file found. Using ancient rates and getting new rates from net.");

        }else if(fileAge>4000000) { // 8640000
            // old file in cache over 1 day old
            // start loading new data from the web
            getLoaderManager().initLoader(0, null, this);
            Log.d("getRates","Old cache file found. Age: " + fileAge + " Using its rates and getting new rates from net.");

        }else {
            Log.d("getRates","Recent cache file found. Cache age: " + fileAge + " Using its rates only.");
        }

        cr = new CurrencyRates();
        cr.parseJSONrates(rates);

        TextView date = (TextView) findViewById(R.id.updatedText);
        date.setText(cr.getDateUpdated());

    }


    private void updateNumbers() {

        EditText CustomHomeEdit = (EditText) findViewById(R.id.editCustomHome);
        CustomHomeEdit.setHint(getString(R.string.custom, homeCurrency));
        CustomHomeEdit.setText("");

        EditText CustomAwayEdit = (EditText) findViewById(R.id.editCustomAway);
        CustomAwayEdit.setHint(getString(R.string.custom, chosenCurrency.getCurrencyCode()));
        CustomAwayEdit.setText("");

        DecimalFormat precision = new DecimalFormat("0.00");

        TextView home1 = (TextView) findViewById(R.id.home1);
        TextView home2 = (TextView) findViewById(R.id.home2);
        TextView home3 = (TextView) findViewById(R.id.home3);
        TextView home4 = (TextView) findViewById(R.id.home4);
        TextView home5 = (TextView) findViewById(R.id.home5);
        TextView home11 = (TextView) findViewById(R.id.home11);
        TextView home12 = (TextView) findViewById(R.id.home12);
        TextView home13 = (TextView) findViewById(R.id.home13);
        TextView home14 = (TextView) findViewById(R.id.home14);
        TextView home15 = (TextView) findViewById(R.id.home15);

        TextView away1 = (TextView) findViewById(R.id.away1);
        TextView away2 = (TextView) findViewById(R.id.away2);
        TextView away3 = (TextView) findViewById(R.id.away3);
        TextView away4 = (TextView) findViewById(R.id.away4);
        TextView away5 = (TextView) findViewById(R.id.away5);
        TextView away11 = (TextView) findViewById(R.id.away11);
        TextView away12 = (TextView) findViewById(R.id.away12);
        TextView away13 = (TextView) findViewById(R.id.away13);
        TextView away14 = (TextView) findViewById(R.id.away14);
        TextView away15 = (TextView) findViewById(R.id.away15);

        home1.setTag(1);
        home2.setTag(5);
        home3.setTag(20);
        home4.setTag(35);
        home5.setTag(50);

        if(chosenCurrency.getRate()<3) {
            away11.setTag(1);
            away12.setTag(5);
            away13.setTag(20);
            away14.setTag(35);
            away15.setTag(50);
        }else if(chosenCurrency.getRate()<6) {
            away11.setTag(5);
            away12.setTag(25);
            away13.setTag(100);
            away14.setTag(150);
            away15.setTag(250);
        }else if(chosenCurrency.getRate()<9) {
            away11.setTag(10);
            away12.setTag(50);
            away13.setTag(150);
            away14.setTag(300);
            away15.setTag(500);
        }else if(chosenCurrency.getRate()<13) {
            away11.setTag(10);
            away12.setTag(75);
            away13.setTag(250);
            away14.setTag(400);
            away15.setTag(1000);
        }else if(chosenCurrency.getRate()<30) {
            away11.setTag(30);
            away12.setTag(200);
            away13.setTag(500);
            away14.setTag(750);
            away15.setTag(1500);
        }else if(chosenCurrency.getRate()<50) {
            away11.setTag(50);
            away12.setTag(500);
            away13.setTag(1000);
            away14.setTag(2000);
            away15.setTag(5000);
        }else if(chosenCurrency.getRate()<100) {
            away11.setTag(75);
            away12.setTag(1000);
            away13.setTag(2000);
            away14.setTag(4000);
            away15.setTag(10000);
        }else if(chosenCurrency.getRate()<160) {
            away11.setTag(100);
            away12.setTag(1500);
            away13.setTag(3000);
            away14.setTag(5000);
            away15.setTag(10000);
        }else if(chosenCurrency.getRate()<350) {
            away11.setTag(300);
            away12.setTag(1000);
            away13.setTag(5000);
            away14.setTag(10000);
            away15.setTag(25000);
        }else if(chosenCurrency.getRate()<1600) {
            away11.setTag(1000);
            away12.setTag(5000);
            away13.setTag(20000);
            away14.setTag(50000);
            away15.setTag(100000);
        }else{
            away11.setTag(20000);
            away12.setTag(100000);
            away13.setTag(300000);
            away14.setTag(700000);
            away15.setTag(1000000);
        }

        home1.setText(getString(R.string.currencyvalue, home1.getTag(), homeCurrency));
        home2.setText(getString(R.string.currencyvalue, home2.getTag(), homeCurrency));
        home3.setText(getString(R.string.currencyvalue, home3.getTag(), homeCurrency));
        home4.setText(getString(R.string.currencyvalue, home4.getTag(), homeCurrency));
        home5.setText(getString(R.string.currencyvalue, home5.getTag(), homeCurrency));
        home11.setText(getString(R.string.currencyvalue, precision.format((int)away11.getTag() / chosenCurrency.getRate()), homeCurrency));
        home12.setText(getString(R.string.currencyvalue, precision.format((int)away12.getTag() / chosenCurrency.getRate()), homeCurrency));
        home13.setText(getString(R.string.currencyvalue, precision.format((int)away13.getTag() / chosenCurrency.getRate()), homeCurrency));
        home14.setText(getString(R.string.currencyvalue, precision.format((int)away14.getTag() / chosenCurrency.getRate()), homeCurrency));
        home15.setText(getString(R.string.currencyvalue, precision.format((int)away15.getTag() / chosenCurrency.getRate()), homeCurrency));

        away1.setText(getString(R.string.currencyvalue, precision.format((int)home1.getTag() * chosenCurrency.getRate()), chosenCurrency.getCurrencyCode()));
        away2.setText(getString(R.string.currencyvalue, precision.format((int)home2.getTag() * chosenCurrency.getRate()), chosenCurrency.getCurrencyCode()));
        away3.setText(getString(R.string.currencyvalue, precision.format((int)home3.getTag() * chosenCurrency.getRate()), chosenCurrency.getCurrencyCode()));
        away4.setText(getString(R.string.currencyvalue, precision.format((int)home4.getTag() * chosenCurrency.getRate()), chosenCurrency.getCurrencyCode()));
        away5.setText(getString(R.string.currencyvalue, precision.format((int)home5.getTag() * chosenCurrency.getRate()), chosenCurrency.getCurrencyCode()));
        away11.setText(getString(R.string.currencyvalue, away11.getTag(), chosenCurrency.getCurrencyCode()));
        away12.setText(getString(R.string.currencyvalue, away12.getTag(), chosenCurrency.getCurrencyCode()));
        away13.setText(getString(R.string.currencyvalue, away13.getTag(), chosenCurrency.getCurrencyCode()));
        away14.setText(getString(R.string.currencyvalue, away14.getTag(), chosenCurrency.getCurrencyCode()));
        away15.setText(getString(R.string.currencyvalue, away15.getTag(), chosenCurrency.getCurrencyCode()));
    }

    /*
    private void populateCountries(){
        String csv = getString(R.string.countryMappingCSV);
        String[] line = csv.split("\n");
        for(int i=1; i<line.length; i++){  // skip headers row[0]
            String[] RowData = line[i].split(",");
            Countries.add(new Country(RowData[0],RowData[1],RowData[2],RowData[3]));
        }
    }
    */


    /**
     * Get ISO 3166-1 alpha-2 country code for this device (or null if not available)
     * @param context Context reference to get the TelephonyManager instance from
     */
    /*
    private Boolean inHomeCountry(Context context) {
        try {

            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String simCountry = tm.getSimCountryIso();

            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available

                simCountry = simCountry.toUpperCase(Locale.UK);
                userSIMCountry = new Country(simCountry);
                Log.d("PLACE", "SIM country: " + simCountry);

            }

            if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)

                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available

                    networkCountry = networkCountry.toUpperCase(Locale.UK);
                    userNetworkCountry = new Country(networkCountry);
                    Log.d("PLACE", "Network country: " + networkCountry);

                    return (userSIMCountry==userNetworkCountry);

                }
            }
        }
        catch (Exception e) { }
        return null;

    }
    */

}