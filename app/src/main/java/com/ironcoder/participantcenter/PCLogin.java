package com.ironcoder.participantcenter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class PCLogin extends Activity {

    private Button loginButton;
    private EditText pcUsername;
    private EditText pcPassword;

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

    }
}
