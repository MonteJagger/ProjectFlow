package com.example.flow.projectflow;

/**
 * Created by Thomas on 5/29/2016.
 */
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Register extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground (String...arg0){
            String emailAddress = arg0[0];
            String password = arg0[1];

            String link;
            String data;
            BufferedReader bufferedReader;
            String result;

            try {
                data = "?email=" + URLEncoder.encode(emailAddress, "UTF-8");
                data += "&pass=" + URLEncoder.encode(password, "UTF-8");

                link = "http://flow.site88.net/php.php" + data;
                //link = "http://testandroid.netai.net/signup.php" + data;
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute (String result){
            String jsonStr = result;

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String query_result = jsonObj.getString("query_result");
                    Toast.makeText(ApplicationContextProvider.getContext(), query_result, Toast.LENGTH_LONG).show();
                    if (query_result.equals("SUCCESS")) {
                        Toast.makeText(ApplicationContextProvider.getContext(), "Data inserted successfully. Signup successfull.", Toast.LENGTH_SHORT).show();

                    } else if (query_result.equals("FAILURE")) {
                        Toast.makeText(ApplicationContextProvider.getContext(), "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ApplicationContextProvider.getContext(), "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ApplicationContextProvider.getContext(), "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(ApplicationContextProvider.getContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }

}