package com.futsal.manager.NetworkModule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.futsal.manager.R;

/**
 * Created by stories2 on 2017. 3. 12..
 */

public class CommunicationTester extends Activity {

    CommunicationWithServer communicationWithServer;
    Button btnLogIn;
    EditText etxtUsername, etxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication_tester);

        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        etxtUsername = (EditText) findViewById(R.id.etxtUsername);
        etxtPassword = (EditText) findViewById(R.id.etxtPassword);

        communicationWithServer = new CommunicationWithServer(getApplicationContext());

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicationWithServer.AuthLogin(etxtUsername.getText().toString(), etxtPassword.getText().toString());
            }
        });
    }
}