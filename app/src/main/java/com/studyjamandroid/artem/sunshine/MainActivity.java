package com.studyjamandroid.artem.sunshine;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends Activity {

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            ArrayAdapter<Weather> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast, R.id.listItemForecastTextView);
            fetchWeatherForecast(adapter);
            ListView listViewForecast = (ListView) rootView.findViewById(R.id.listViewForecast);
            listViewForecast.setAdapter(adapter);

            return rootView;
        }

        private void fetchWeatherForecast(ArrayAdapter<Weather> adapter) {
            URL url = buildUrl();
            new GetWeatherTask(adapter).execute(url);
        }

        private URL buildUrl() {
            String forecastBaseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?";
            String queryParam = "q";
            String formatParam = "mode";
            String unitsParam = "units";
            String daysParam = "cnt";
            String postCode = "Kiev";
            String format = "json";
            String units = "metric";
            int numDays = 7;

            Uri builtUri = Uri.parse(forecastBaseUrl).buildUpon()
                    .appendQueryParameter(queryParam, postCode)
                    .appendQueryParameter(formatParam, format)
                    .appendQueryParameter(unitsParam, units)
                    .appendQueryParameter(daysParam, Integer.toString(numDays))
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
