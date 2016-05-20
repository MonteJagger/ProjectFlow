package com.example.flow.projectflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.userEmail);
    }

    public void signInClick(View view) {
        String userEmail = String.valueOf(email.getText());
        String response = "Email: " + userEmail;
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();

    }

    public void signUpClick(View view) {
        Toast.makeText(this, "Signing up", Toast.LENGTH_SHORT).show();
    }
}
