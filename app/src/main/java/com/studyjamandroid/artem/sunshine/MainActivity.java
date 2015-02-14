package com.studyjamandroid.artem.sunshine;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.action_view_map:
                Uri geolocation = getGeolocation();
                showMap(geolocation);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    Example: "geo:0,0?q=1600+Amphitheatre+Parkway%2C+CA"
//    Note: All strings passed in the geo URI must be encoded. For example, the string 1st & Pike, Seattle should become 1st%20%26%20Pike%2C%20Seattle. Spaces in the string can be encoded with %20 or replaced with the plus sign (+).
    private Uri getGeolocation() {
        String prefLocationKey = getString(R.string.pref_location_key);
        String city = PreferenceManager.getDefaultSharedPreferences(this).getString(prefLocationKey, "Kiev");
        String baseUri = "geo:0,0?q=" + city;
        Uri geolocation = null;
        try {
            geolocation = Uri.parse(baseUri);
        } catch (RuntimeException e) {
            Log.w(TAG, "Bad Uri");
        }
        return geolocation;
    }

    private void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation)
                .setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Google Maps app is not installed", Toast.LENGTH_SHORT).show();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            final Context context = getActivity();
            final ArrayAdapter<Weather> adapter = new ArrayAdapter<>(context, R.layout.list_item_forecast, R.id.listItemForecastTextView);

            String prefLocationKey = getString(R.string.pref_location_key);
            String city = PreferenceManager.getDefaultSharedPreferences(context).getString(prefLocationKey, "Kiev");
            String prefTemprUnitsKey = getString(R.string.pref_tempr_units_key);
            String unitsValue = PreferenceManager.getDefaultSharedPreferences(context).getString(prefTemprUnitsKey, "metric");
            String units = (unitsValue.equals("C")) ? "metric" : "imperial";
            fetchWeatherForecast(adapter, city, units);

            ListView listViewForecast = (ListView) rootView.findViewById(R.id.listViewForecast);
            listViewForecast.setAdapter(adapter);

            listViewForecast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String text = adapter.getItem(position).toString();
                    Intent intent = new Intent(context, DetailedActivity.class);
                    intent.putExtra("text", text);
                    startActivity(intent);
                }
            });

            return rootView;
        }

        private void fetchWeatherForecast(ArrayAdapter<Weather> adapter, String city, String units) {
            URL url = buildUrl(city, units);
            new GetWeatherTask(adapter).execute(url);
        }

        private URL buildUrl(String city, String units) {
            String forecastBaseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?";
            String queryParam = "q";
            String formatParam = "mode";
            String unitsParam = "units";
            String daysParam = "cnt";
            String format = "json";
//            String units = "metric";
            String numDays = "7";

            Uri builtUri = Uri.parse(forecastBaseUrl).buildUpon()
                    .appendQueryParameter(queryParam, city)
                    .appendQueryParameter(formatParam, format)
                    .appendQueryParameter(unitsParam, units)
                    .appendQueryParameter(daysParam, numDays)
                    .build();

            URL url = null;
            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                Log.w("buildUrl()", "Wrong URL", e);
            }
            return url;
        }
    }
}
