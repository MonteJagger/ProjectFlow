package com.example.flow.projectflow;

/**
 * Created by Thomas on 5/25/2016.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class SignUpActivity extends AppCompatActivity {

    EditText etEmail,etPass,etVPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>FLOW - Sign Up</font>"));

        ActionBar actionBack = getSupportActionBar();
        actionBack.setDisplayHomeAsUpEnabled(true);

        View vBtnSub = findViewById(R.id.btnSub);
        assert vBtnSub != null;
        Button btnSub = (Button) vBtnSub;

        etEmail = (EditText) findViewById(R.id.edEmail);
        etPass = (EditText) findViewById(R.id.edPass);
        etVPass = (EditText) findViewById(R.id.edVPass);



/*===============================================================================
//   SUBMIT BUTTON
//==============================================================================*/
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String emailAddress = etEmail.getText().toString();
                String password = etPass.getText().toString();
                String verifyPassword = etPass.getText().toString();

                //Validating username and password
                if(etEmail.length() == 0) {
                    etEmail.requestFocus();
                    etEmail.setError("Email cannot be empty.");
                }
//LINH
                else if(etEmail.length() < 4 || etEmail.length() > 30) {
                    etEmail.requestFocus();
                    etEmail.setError("Email must be between 4 and 30 characters");
                }
//------------------------------
                else if (!emailAddress.contains("@") || !emailAddress.contains(".") ) {
                    etEmail.requestFocus();
                    etEmail.setError("Email must contain '@' and '.'");
                }
                else if(etPass.length() == 0) {
                    etPass.requestFocus();
                    etPass.setError("Password cannot be empty.");
                }
//LINH
                else if(etPass.length() < 5 || etPass.length() > 15 ) {
                    etPass.requestFocus();
                    etPass.setError("Password can only be between 5 and 15 characters");
                }

                else if(!etVPass.getText().toString().equals(etPass.getText().toString())) {
                    etVPass.requestFocus();
                    etVPass.setError("Passwords do not match.");
                }
                else {
                    new Register().execute(emailAddress,password);
                }



            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(nextScreen,0);
            return true;
    }

    @Override
    public void onBackPressed () {
        // Write your code here
        Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(nextScreen);
        finish();
        super.onBackPressed();
    }


    public static class Register extends AsyncTask<String,Void,String> {

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
                    data += "&password=" + URLEncoder.encode(password, "UTF-8");

                    link = "http://flowbyvoyu.com/login.php"+ data;
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
                        //Toast.makeText(ApplicationContextProvider.getContext(), query_result, Toast.LENGTH_LONG).show();
                        if (query_result.equals("SUCCESS")) {
                            Toast.makeText(ApplicationContextProvider.getContext(), "Signup successful.", Toast.LENGTH_SHORT).show();
                            Intent nextScreen = new Intent(ApplicationContextProvider.getContext(), LoginActivity.class);
                            nextScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ApplicationContextProvider.getContext().startActivity(nextScreen);
                        } else if (query_result.equals("FAILURE")) {
                            Toast.makeText(ApplicationContextProvider.getContext(), "Email already exists", Toast.LENGTH_SHORT).show();
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
}