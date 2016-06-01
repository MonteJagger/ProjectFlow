package com.example.flow.projectflow;

/**
 * Created by Thomas on 5/25/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class SignUpActivity extends Activity {

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