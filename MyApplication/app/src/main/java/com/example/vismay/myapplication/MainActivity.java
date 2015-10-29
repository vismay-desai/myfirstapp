package com.example.vismay.myapplication;


import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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



public class MainActivity extends AppCompatActivity {

    private final String fileName = "myfile.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        final EditText o_name = (EditText) findViewById(R.id.editText);
        final EditText o_phno = (EditText) findViewById(R.id.editText1);
        final EditText o_mailid = (EditText) findViewById(R.id.editText2);


       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_name,s_phno,s_mailid;
                Intent SecondIntent = new Intent(MainActivity.this, AdditionalDetail.class);
                startActivity(SecondIntent);
                s_name = o_name.getText().toString();
                s_phno = o_phno.getText().toString();
                s_mailid = o_mailid.getText().toString();
                if(Debugclass.Logdisplay==1)
                {
                    Log.w("EditText",s_name);
                    Log.w("EditText",s_phno);
                    Log.w("EditText", s_mailid);
                }

                if (Environment.MEDIA_MOUNTED.equals(Environment
                        .getExternalStorageState())) {

                    File outFile = new File(
                            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            fileName);
                    try {

                        BufferedOutputStream os = new BufferedOutputStream(
                                new FileOutputStream(outFile));
                        try {
                             os.write(s_name.getBytes());
                             os.write("\n".getBytes());
                             os.write(s_phno.getBytes());
                             os.write("\n".getBytes());
                             os.write(s_mailid.getBytes());
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
