package com.example.darsheemachhar.wifi;

import android.content.Context;
        import android.content.Intent;
        import android.media.MediaScannerConnection;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.net.wifi.WifiInfo;
        import android.net.wifi.WifiManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.text.format.Formatter;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.os.Handler;
        import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.io.OutputStreamWriter;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;

public class DisplayInfo extends AppCompatActivity {
    String info;
    Button backButton;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);
        flag=0;
        backButton= findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DisplayInfo.this, MainActivity.class));
            }
        });

        final Context context=this;
        final Handler handler=new Handler();
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                getWifiInfo(context);
                saveFile();
                Log.d("Handlers", "Called on main thread");
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 60000);
            }
        };
// Start the initial runnable task by posting through the handler
        handler.post(runnableCode);
    }

    public void getWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager)
                getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
        // Context context = getApplicationContext();

        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (String.valueOf(wifiInfo.getSupplicantState()).equals("COMPLETED")) {
                //Toast.makeText(this, wifiInfo.getSSID()+"", Toast.LENGTH_SHORT).show();

                String ssid = "SSID: " + wifiInfo.getSSID();

                int rssi = wifiInfo.getRssi();
                String rssidval = "RSSID: " + rssi + " dBm";

                String bssid = "BSSID: " + wifiInfo.getBSSID();

                int ip = wifiInfo.getIpAddress();
                String ipaddress = "Ip Address: " + Formatter.formatIpAddress(ip);

                int linkspeed = wifiInfo.getLinkSpeed();
                String linksp = "Link Speed: " + linkspeed+ "Mbps";

                String signal_strength;
                rssi = 2 * (rssi + 100);
                signal_strength = "Signal Strength: " + rssi;

                info = ssid + "\n" + rssidval + "\n" + signal_strength + "\n" + bssid + "\n" + ipaddress + "\n" + linksp;

                ((TextView) findViewById(R.id.Txt1)).setText(ssid);
                ((TextView) findViewById(R.id.Txt2)).setText(rssidval);
                ((TextView) findViewById(R.id.Txt3)).setText(signal_strength);
                ((TextView) findViewById(R.id.Txt4)).setText(ipaddress);
                ((TextView) findViewById(R.id.Txt5)).setText(linksp);

            } //if ends
        }

    } //getWifiInfo ends

    private void saveFile()
    {
        File file = new File(getExternalFilesDir(null), "timestamp.txt");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        String currentTime = simpleDateFormat.format(c.getTime());
        try {
            // Creates a file in the primary external storage space of the current application.
            // If the file does not exists, it is created.
            File testFile = new File(this.getExternalFilesDir(null), "wifi-info.txt");
            if (!testFile.exists())
                testFile.createNewFile();
            // Adds a line to the file
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testFile, true
                    /*append*/));
            bufferedWriter.write(currentTime + "\n" + info + "\n \n");
            bufferedWriter.close();
            Toast.makeText(getBaseContext(),"file saved",Toast.LENGTH_SHORT).show();
            MediaScannerConnection.scanFile(this,
                    new String[]{testFile.toString()},
                    null,
                    null);
            //startActivity(new Intent(DisplayInfo.this, FileReader.class));
        } catch (IOException e) {
            Log.e("ReadWriteFile", "Unable to write to the TestFile.txt file.");
        }
    }



}
