package com.example.darsheemachhar.wifi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

public class FileReader extends AppCompatActivity {

    TextView tv;
    EditText ed1;
    String file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_reader);
        ed1=(EditText)findViewById(R.id.editText);
        tv=(TextView)findViewById(R.id.textView2);
    }

    public void onClickLoad(View v){
        file=ed1.getText().toString();
        try {
            FileInputStream fin = openFileInput(file);
            int c;
            String temp="";
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            tv.setText(temp);
            Toast.makeText(getBaseContext(),"file read",Toast.LENGTH_SHORT).show();
        }
        catch(IOException e){
            Log.e("ReadWriteFile", "Unable to read said file.");
        }
    }
}
