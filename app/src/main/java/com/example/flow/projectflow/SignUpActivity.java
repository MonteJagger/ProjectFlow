package com.example.flow.projectflow;

/**
 * Created by Thomas on 5/25/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class SignUpActivity extends AppCompatActivity {

    ImageButton btnBack;
    EditText etEmail,etPass,etVPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        View vbtnBack = findViewById(R.id.btnBack);
        assert vbtnBack != null;
        btnBack = (ImageButton) vbtnBack;

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

                if (password.equals(verifyPassword))
                {
                    new Register().execute(emailAddress,password);
                }

                Intent nextScreen = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(nextScreen);
                finish();

            }
        });


/*===============================================================================
//   BACK BUTTON
//==============================================================================*/
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(nextScreen);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed () {
        // Write your code here
        Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(nextScreen);
        finish();
        super.onBackPressed();
    }


}