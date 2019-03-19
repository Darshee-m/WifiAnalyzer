package com.example.darsheemachhar.wifi;

import android.net.wifi.WifiInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            }

    public void onInfoClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   DisplayInfo.class);

                startActivity(myIntent);


    }
}




