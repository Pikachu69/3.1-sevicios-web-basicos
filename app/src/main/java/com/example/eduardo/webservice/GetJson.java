package com.example.eduardo.webservice;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by eduardo on 22/03/18.
 */

class GetJson extends AsyncTask<Void,Void,Void> {
    String data ="";
    String [] ciudad = {"Tepic", "Mexico"};
    double c = 273.15;
    String [] dataParsed = new String[ciudad.length];

    @Override
    protected Void doInBackground(Void... voids) {
        for(int i = 0; i<ciudad.length; i++) {
            data="";
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+ciudad[i]+",mx&APPID=2deab61146db8b272ed004d2c1a40935");
                HttpURLConnection httpURLConnection = (HttpURLConnection)
                        url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new
                        InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
            /*JSONArray JA = new JSONArray(data);
            for(int i =0 ;i <JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                singleParsed = "Ciudad:" + JO.get("name") + "\n"+
                        "Clima:" + JO.get("description") + "\n"+
                        "Temperatura:" + JO.get("temp") + "\n"+
                        "Pais:" + JO.get("country") + "\n";
                dataParsed = dataParsed + singleParsed +"\n" ;
            }*/
                JSONObject JO = new JSONObject(data);
                dataParsed [i]= "Pais: " + JO.getJSONObject("sys").get("country") + "\n" +
                        "Ciudad: " + JO.get("name") + "\n" +
                        "Temperatura: " + ((double) JO.getJSONObject("main").get("temp") - c) + "°C" + "\n" +
                        "Clima: " + JO.getJSONArray("weather").getJSONObject(0).get("description");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.txtTepic.setText(this.dataParsed[0]);
        MainActivity.txtMX.setText(this.dataParsed[1]);
    }
}
