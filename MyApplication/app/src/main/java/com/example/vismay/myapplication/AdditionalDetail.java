package com.example.vismay.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class AdditionalDetail extends AppCompatActivity {

    private final String fileName = "myfile.txt";
    Intent SecondIntent;
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
                String s_field1, s_field2, s_field3,s_field4;
                SecondIntent = new Intent(AdditionalDetail.this, otherDetails.class);
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion >= android.os.Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
                    }
                    else
                    {
                        startActivity(SecondIntent);
                    }

                } else{
                    startActivity(SecondIntent);
                }


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
                             os.write("\n".getBytes());
                             os.write(s_field2.getBytes());
                             os.write("\n".getBytes());
                             os.write(s_field3.getBytes());
                             os.write("\n".getBytes());
                             os.write(s_field4.getBytes());
                             os.write("\n".getBytes());
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
        });
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode==10)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(SecondIntent);
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Permission denied : go back";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
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
