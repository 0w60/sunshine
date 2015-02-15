package com.studyjamandroid.artem.sunshine;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.detailedActivity, new ShareFragment())
                    .commit();
        }
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

        private ShareActionProvider shareActionProvider;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            setHasOptionsMenu(true);
            return inflater.inflate(R.layout.fragment_share, container, false);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);

            MenuItem menu_item_share = menu.findItem(R.id.menu_item_share);
            shareActionProvider = (ShareActionProvider) menu_item_share.getActionProvider();
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_item_share:
                    Intent intent = getShareIntent();
                    doShare(intent);
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

        private Intent getShareIntent() {
            String textToShare = getActivity().getIntent().getStringExtra("text") + " #SunshineApp";
            String mimeType = "text/plain";
            return new Intent(Intent.ACTION_SEND)
                    .setType(mimeType)
                    .putExtra("text", textToShare)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        }

        private void doShare(Intent shareIntent) {
            shareActionProvider.setShareIntent(shareIntent);
        }
    }
}
