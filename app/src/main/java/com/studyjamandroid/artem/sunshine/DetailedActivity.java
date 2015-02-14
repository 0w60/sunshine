package com.studyjamandroid.artem.sunshine;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;


public class DetailedActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        String text = getIntent().getStringExtra("text");
        TextView detailedTxtView = (TextView) findViewById(R.id.detailedTxtView);
        detailedTxtView.setText(text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public static class ShareFragment extends Fragment {

        ShareActionProvider shareActionProvider = new ShareActionProvider(getActivity());



        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }
}
