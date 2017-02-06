package com.apps.abercrombiefitch.tasks;

import android.os.AsyncTask;

import com.apps.abercrombiefitch.model.AFSiteData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractRetriveAFTask extends AsyncTask<String, String, List<AFSiteData>> {

    private HttpURLConnection urlConnection;
    private BufferedReader reader;

    @Override
    protected List<AFSiteData> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            int statusCode = urlConnection.getResponseCode();

            // 200 represents HTTP OK
            if (statusCode == 200) {
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                String finalJson = response.toString();

                JSONArray parentArray = new JSONArray(finalJson);
                List<AFSiteData> afSiteDataList = new ArrayList<>();

                for (int i = 0; i < parentArray.length(); i++) {
                    //Gson Library to parse response
                    Gson gson = new Gson();
                    JSONObject post = parentArray.optJSONObject(i);
                    AFSiteData item = gson.fromJson(post.toString(), AFSiteData.class);
                    afSiteDataList.add(item);
                }
                return afSiteDataList;
            } else {
                //"Failed to fetch data!";
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<AFSiteData> result) {
        PopulateData(result);
    }

    protected abstract void PopulateData(List<AFSiteData> result);
}
