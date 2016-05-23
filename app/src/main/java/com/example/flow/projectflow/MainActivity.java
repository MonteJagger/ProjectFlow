package com.example.flow.projectflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends AppCompatActivity {

    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.userEmail);
        Button btnSignIn = (Button) findViewById(R.id.signInButton);
        Button btnSignUp = (Button) findViewById(R.id.signUpButton);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = String.valueOf(email.getText());
                String response = "Email: " + userEmail;
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                Intent nextScreen = new Intent(getApplicationContext(), MainScreen.class);
                startActivity(nextScreen);
                //finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Signing up", Toast.LENGTH_SHORT).show();
                Intent nextScreen = new Intent(getApplicationContext(), MainScreen.class);
                startActivity(nextScreen);
                //finish();
            }
        });



    }




}
