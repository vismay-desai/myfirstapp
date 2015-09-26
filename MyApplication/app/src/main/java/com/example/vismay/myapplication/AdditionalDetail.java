package com.example.vismay.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by 522600 on 9/18/2015.
 */
public class AdditionalDetail extends AppCompatActivity {

    private final String fileName = "myfile.txt";
    OutputStream os;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additionaldetail);

        final Button button = (Button) findViewById(R.id.button1);
        final EditText o_field1 = (EditText) findViewById(R.id.editText3);
        final EditText o_field2 = (EditText) findViewById(R.id.editText4);
        final EditText o_field3 = (EditText) findViewById(R.id.editText5);
        final EditText o_field4 = (EditText) findViewById(R.id.editText6);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_field1 = "field1", s_field2 = "field2", s_field3 = "field3",s_field4="field4";
                Intent SecondIntent = new Intent(AdditionalDetail.this, otherDetails.class);
                startActivity(SecondIntent);
                s_field1 = o_field1.getText().toString();
                s_field2 = o_field2.getText().toString();
                s_field3 = o_field3.getText().toString();
                s_field4 = o_field4.getText().toString();
                if(Debugclass.Logdisplay==1)
                {
                    Log.w("EditText",s_field1);
                    Log.w("EditText",s_field2);
                    Log.w("EditText", s_field3);
                    Log.w("EditText",s_field4);
                }

                if(Debugclass.finalversion==1) {
                    if (Environment.MEDIA_MOUNTED.equals(Environment
                            .getExternalStorageState())) {

                        File outFile = new File(
                                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                fileName);
                        try {

                            BufferedOutputStream os = new BufferedOutputStream(
                                    new FileOutputStream(outFile, true));

                            try {
                                os.write(s_field1.getBytes());
                                os.write(s_field2.getBytes());
                                os.write(s_field3.getBytes());
                                os.write(s_field4.getBytes());
                            } catch (IOException e) {
                                Log.w("EditText", "write,error");
                            }
                            try {
                                os.close();
                            } catch (IOException e) {
                                Log.w("vismay", "IOException");
                            }

                        } catch (FileNotFoundException e) {
                            Log.w("EditText", "FileNotFoundException");
                        }

                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_additionaldetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
