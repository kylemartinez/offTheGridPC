package com.ironcoder.participantcenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ironcoder.participantcenter.API.PCLoginAPI;


public class PCLogin extends Activity {

    private static final String TAG = "OFFTHEGRID";

    private static final String CONS_ID_KEY = "cons_id";
    private static final String TOKEN_KEY = "token";
    private static final String USER_NAME_KEY = "username";

    private Button loginButton;
    private EditText pcUsername;
    private EditText pcPassword;

    private PCLoginAPI pcLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pclogin);

        pcUsername = (EditText)findViewById(R.id.username);
        pcPassword = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                login();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pclogin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void login(){
        Log.d(TAG, "Attempting to login to PC2");

        String username = pcUsername.getText().toString();
        String password = pcPassword.getText().toString();

//        if (username.equals("") || password.equals("")){
//            Log.d(TAG, "Missing Username or Password");
//            return;
//        }
//
//        pcLogin = new PCLoginAPI(username, password);
//
//        try {
//            pcLogin.login();
//        } catch (Exception e) {
//            Log.d(TAG, "Login Error: " + e.toString());
//            return;
//        }

        Intent intent = new Intent(this, FriendsMapActivity.class);
//        intent.putExtra(CONS_ID_KEY, pcLogin.consID);
//        intent.putExtra(TOKEN_KEY, pcLogin.token);
//        intent.putExtra(USER_NAME_KEY, username);
        startActivity(intent);
    }
}
