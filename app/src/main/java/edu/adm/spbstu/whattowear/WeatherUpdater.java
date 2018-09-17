package edu.adm.spbstu.whattowear;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherUpdater extends AsyncTask<String, Integer, String> {

    private static final String API_BASE_PATH = "https://api.darksky.net/forecast/";
    private static final String API_KEY = "a51089b1b0c72c20811d13bd44f4af3d";

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private JSONObject data;

    private String temperature;
    private String precip;
    private String cityCoordinate;

    public String getTemperature() {
        return temperature;
    }

    public String getPrecip() {
        return precip;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(API_BASE_PATH + API_KEY + "/" + cityCoordinate);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder json = new StringBuilder(1024);

            String tmp;
            while((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            data = new JSONObject(json.toString());
            return "Weather was updated";
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    void updateWeather(Context context, String city) {
        this.context = context;
        cityCoordinate = CityCoordinate.map.get(city);
        try {
            execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            JSONObject currently = data.getJSONObject("currently");
            String temp = currently.getString("temperature");
            int tempInCelsium = (int) ((Double.valueOf(temp) - 32) / 2.0 * 1.1);
            temperature = tempInCelsium + "°C";

            precip = currently.getString("summary");
        } catch (JSONException e) {
            System.out.println("json decoding error");
        }
    }
}