package com.example.vismay.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class otherDetails extends AppCompatActivity implements LocationListener {

    private static int REQUEST_CAMERA =100;
    private final String fileName = "myfile.txt";
    String mCurrentPhotoPath;
    Button picbutton;
    Button geobutton;
    Button nxtbutton;
    Button earlynxtbutton;
    int camcount=0;
    Intent camintent;
    LocationManager locationManager;
    String longitude="0",latitude="0";
    Location mylocation;

    public void onLocationChanged(Location tmplocation) {
        if (Debugclass.Logdisplay==1) {
            Log.w("Latitude:", String.valueOf(mylocation.getLatitude()));
            Log.w("Longitude:", String.valueOf(mylocation.getLongitude()));
        }
        mylocation = tmplocation;
        longitude = String.valueOf(mylocation.getLongitude());
        latitude = String.valueOf(mylocation.getLatitude());

    }

    public void onProviderDisabled(String provider) {
        if (Debugclass.Logdisplay==1) {
            Log.d("Latitude", "disable");
        }
        Context context = getApplicationContext();
        CharSequence text = "Please enable GPS to get location";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onProviderEnabled(String provider) {
        if (Debugclass.Logdisplay==1) {
            Log.d("Latitude", "enable");
        }
        Context context = getApplicationContext();
        CharSequence text = "Thank you for providing GPS access";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_details);

        picbutton = (Button) findViewById(R.id.button2);
        geobutton = (Button) findViewById(R.id.button3);
        nxtbutton = (Button) findViewById(R.id.button4);
        earlynxtbutton = (Button) findViewById(R.id.button5);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        if(Debugclass.Logdisplay==1) {
            Log.w("location : ", "geting location");
        }
        if (locationManager != null) {
            mylocation = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(Debugclass.Logdisplay==1) {
                Log.w("location : ", "location not null");
            }
            if (mylocation != null) {
                Log.w("mylocation : ", "mylocation not null");
                latitude = String.valueOf(mylocation.getLatitude());
                longitude = String.valueOf(mylocation.getLongitude());
            }
        }

        picbutton.setVisibility(View.VISIBLE);
        geobutton.setVisibility(View.GONE);
        nxtbutton.setVisibility(View.GONE);
        earlynxtbutton.setVisibility(View.GONE);
        picbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.w("IO_exception_jpg_file:", ex);
                }

                if (photoFile != null) {
                    camintent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                    if (Debugclass.Logdisplay == 1) {
                        Log.w("intent_event:", "pic take event called");
                    }
                    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                    if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, 10);
                        } else {
                            startActivityForResult(camintent, REQUEST_CAMERA);
                        }

                    } else {
                        startActivityForResult(camintent, REQUEST_CAMERA);
                    }


                }

            }
        });


        geobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(Debugclass.Logdisplay==1) {
                Log.w("onclickevent", "geo button event called");
                Log.w("Latitude:", latitude);
                Log.w("Longitude:", longitude);
            }
            picbutton.setVisibility(View.GONE);
            geobutton.setVisibility(View.GONE);
            nxtbutton.setVisibility(View.VISIBLE);




            if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())) {

                File outFile = new File(
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        fileName);
                try {
                    BufferedOutputStream os = new BufferedOutputStream(
                            new FileOutputStream(outFile, true));

                    try {
                         os.write("Lat/long : ".getBytes());
                         os.write("\n".getBytes());
                         os.write(latitude.getBytes());
                         os.write("\n".getBytes());
                         os.write(longitude.getBytes());
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

        earlynxtbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geobutton.setVisibility(View.VISIBLE);
                picbutton.setVisibility(View.GONE);
                earlynxtbutton.setVisibility(View.GONE);

            }
        });

        nxtbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Debugclass.Logdisplay==1) {
                    Log.w("onclickevent", "finsh event called");
                }
                String[] filelist = getExternalFilesDir(Environment.DIRECTORY_PICTURES).list();
                File outFile = new File(
                        getExternalFilesDir(Environment.MEDIA_UNKNOWN),
                        "mydata.db");
                String len;
                try {
                    BufferedOutputStream os = new BufferedOutputStream(
                            new FileOutputStream(outFile));

                    try {
                        for (int i=0; i<filelist.length ; i++)
                        {
                            File inFile = new File(
                                    getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                    filelist[i]);
                            len = String.valueOf(inFile.length());
                            if(Debugclass.Logdisplay==1) {
                                Log.w("file : ", filelist[i]);
                            }
                            os.write(filelist[i].getBytes());
                            os.write("/--/".getBytes());
                            os.write(len.getBytes());
                            os.write("\n".getBytes());
                        }
                        os.write("\\ end of header \\".getBytes());
                        os.write("\n".getBytes());
                        for (int i=0; i<filelist.length ; i++)
                        {
                            File inFile = new File(
                                    getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                    filelist[i]);
                            BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFile));
                            byte[] buffer = new byte[1024];
                            int read;
                            while ((read = in.read(buffer)) != -1) {
                                os.write(buffer, 0, read);
                            }
                            inFile.delete();

                        }
                        os.flush();
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

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);

            }
        });




    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }

    private File createImageFile() throws IOException {
        // Create an image file name
        // String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        // String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                "mypic",  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                picbutton.setText(R.string.takemultpic);
                earlynxtbutton.setVisibility(View.VISIBLE);
                camcount++;
                if (camcount>9){
                    geobutton.setVisibility(View.VISIBLE);
                    picbutton.setVisibility(View.GONE);
                    earlynxtbutton.setVisibility(View.GONE);
                }
                if(Debugclass.Logdisplay==1) {
                    Log.w("onclickevent", "pic button event return successful");
                }

            } else if (resultCode == RESULT_CANCELED) {
                Context context = getApplicationContext();
                CharSequence text = "Please take one photo";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Some error occure, please try again";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode==10)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(camintent, REQUEST_CAMERA);
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
        getMenuInflater().inflate(R.menu.menu_other_details, menu);
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
