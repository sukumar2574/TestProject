package com.apps.abercrombiefitch.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apps.abercrombiefitch.adapter.AFRecyclerAdapter;
import com.apps.abercrombiefitch.model.AFSiteData;
import com.apps.abercrombiefitch.R;
import com.apps.abercrombiefitch.tasks.AbstractRetriveAFTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;


public class AFHomeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    final String url = "https://www.abercrombie.com/anf/nativeapp/qa/codetest/codeTest_exploreData.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.af_home_screen);

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Async task call
        new jsonTask().execute(url);

        // Downloading the images
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private class jsonTask extends AbstractRetriveAFTask {
        public void PopulateData(List<AFSiteData> result) {
            AFRecyclerAdapter adapter = new AFRecyclerAdapter(AFHomeActivity.this, result);
            mRecyclerView.setAdapter(adapter);
        }
    }
}
