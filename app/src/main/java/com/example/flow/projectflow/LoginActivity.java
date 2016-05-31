package com.example.flow.projectflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View vBtnSignIn = findViewById(R.id.signInButton);
        assert vBtnSignIn != null;
        Button btnSignIn = (Button) vBtnSignIn;

        email = (EditText) findViewById(R.id.userEmail);

        View vBtnSignUp = findViewById(R.id.signUpButton);
        assert vBtnSignUp != null;
        Button btnSignUp = (Button) vBtnSignUp;





/*===============================================================================
//  SIGN IN BUTTON
//==============================================================================*/
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String userEmail = String.valueOf(email.getText());
                String response = "Email: " + userEmail;
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                Intent nextScreen = new Intent(getApplicationContext(), MainInterfaceActivty.class);
                startActivity(nextScreen);
                finish();
            }
        });
/*===============================================================================
//  SIGN UP BUTTON
//==============================================================================*/
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Signing up", Toast.LENGTH_SHORT).show();
                Intent nextScreen = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(nextScreen);
                finish();
            }
        });




    }
}
