package com.mishorsapps.android.mishoweather;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MMERS on 06/08/2017.
 */

public class DownloadTask  extends AsyncTask<String,Void,String> {

    private static final String TAG = "Hey";

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param urls The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected String doInBackground(String... urls) {
        String result = "";

        URL url = null;

        HttpURLConnection urlConnection = null;

        try {

            url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream is = urlConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(is);

            int data = reader.read();

            while(data != -1){

                char current = (char) data;

                result += current;

                data = reader.read();


           }//while

        } catch (Exception e) {

            e.printStackTrace();

        }//catch

        return result;
    }//method

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);

        try {

            JSONObject jsonObject = new JSONObject(result);

            JSONObject weatherData = jsonObject.optJSONObject("main");

            double temperature = (double)Double.parseDouble(weatherData.optString("temp"));

            int tempInt = (int) (temperature - 273.15);

            String city = jsonObject.optString("name");

            MainActivity.t.setText(String.valueOf(tempInt) + "\n" + city);



            Log.d(TAG,"here");

        } catch (Exception e) {

            e.printStackTrace();

            Log.d(TAG,"Nothere");

        }//catch


    }//method
}//class
