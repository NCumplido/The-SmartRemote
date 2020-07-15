package nich.project.thesmartremote;

/*
Some code from:

Dr. Tom Owen: CSC306
Dr. Deepak Sahoo: CSCM79
Swansea University

 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.GREEN;
import static android.view.WindowManager.LayoutParams;

//https://developer.android.com/reference/android/net/wifi/WifiManager
//https://developer.android.com/training/connect-devices-wirelessly/wifi-direct Create P2P connections with Wi-Fi Direct

//https://developers.google.com/nearby/connections/overview
//https://developer.android.com/training/connect-devices-wirelessly

public class MainActivity extends AppCompatActivity implements SensorEventListener { //This new comment of mine

    private ImageView   m_imgGesturePerformed,
                        m_imgLocations,
                        m_imgDevices,
                        m_imgGestures;
//                        

    private SensorManager m_sensorManager;

    private Sensor m_sensorAccelerometer;
    private Sensor m_sensorProximity;

    private String m_SensorsNotProvideByDevice;

    private boolean m_isgestureListen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);

        m_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        deviceHasSensors();

        //setupWiFi();

        setupView();

        setupListeners();

    }

    /////////////////////////////////////////////////////// SENSOR CHECK /////////////////////////////////////////////////////

    //Check if device has all sensors
    //Return if required sensor is unavailable, give notification and limit functions/features
    private void deviceHasSensors() {

        List<Sensor> m_deviceSensorsList = m_sensorManager.getSensorList(Sensor.TYPE_ALL);
        ArrayList<Sensor> m_requiredSensorsList = new ArrayList<>();

        m_sensorAccelerometer = m_sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        /*
        Sensor.TYPE_LINEAR_ACCELERATION
Software Sensor
Measure acceleration force applied to device in three axes
excluding the force of gravity
         */

        //TYPE_ORIENTATION has been deprecated
        Sensor m_sensorGyro = m_sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        m_sensorProximity = m_sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        m_requiredSensorsList.add(m_sensorAccelerometer);
        m_requiredSensorsList.add(m_sensorGyro);
        m_requiredSensorsList.add(m_sensorProximity);


        if (!m_deviceSensorsList.contains(m_sensorAccelerometer)) {
            m_SensorsNotProvideByDevice += " Accelerometer";
        }

        if (!m_deviceSensorsList.contains(m_sensorGyro)) {m_SensorsNotProvideByDevice += " Gyroscope";}

        if (!m_deviceSensorsList.contains(m_sensorProximity)) {m_SensorsNotProvideByDevice += " Proximity";}

        if(m_SensorsNotProvideByDevice != null){
            openErrorDialogue(m_SensorsNotProvideByDevice);
        }

    }

    /////////////////////////////////////////////////////// WIFI STUFF ///////////////////////////////////////////////////////



    /////////////////////////////////////////////////////// VIEW STUFF ///////////////////////////////////////////////////////

    private void setupView() {

        TextView m_txtWifiStatus = findViewById(R.id.txt_connect_status);
        m_txtWifiStatus.setTypeface(m_txtWifiStatus.getTypeface(), Typeface.ITALIC);
        m_txtWifiStatus.setTextColor(GREEN);

        m_imgGesturePerformed = findViewById(R.id.img_gesture_performed);

        m_imgLocations = findViewById(R.id.img_locations);

        m_imgDevices = findViewById(R.id.img_devices);

        m_imgGestures = findViewById(R.id.img_gestures);
    }

    /////////////////////////////////////////////////////// LISTENERS ///////////////////////////////////////////////////////

    private void setupListeners() {

        //linear acceleration = acceleration - acceleration due to gravity
        m_sensorAccelerometer = m_sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        m_sensorManager.registerListener(this, m_sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        m_sensorManager.registerListener(this, m_sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);

        m_sensorManager.registerListener(this,
                m_sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

        m_imgGesturePerformed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_isgestureListen == false){
                    m_isgestureListen = true;
                    m_imgGesturePerformed.setImageResource(R.drawable.shitty_go);
                }
                else
                {
                    m_isgestureListen = false;
                }
            }
        });

        m_imgLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manageLocationProfilesIntent = new Intent(getApplicationContext(), ManageLocationProfilesActivity.class);
                startActivity(manageLocationProfilesIntent);
            }
        });

        m_imgLocations.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent CompassCalibrateintent = new Intent(getApplicationContext(), CompassCalibrateActivity.class);
                startActivity(CompassCalibrateintent);
                return false;
            }
        });

        m_imgDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manageDevicesIntent = new Intent(getApplicationContext(), ManageDevicesActivity.class);
                startActivity(manageDevicesIntent);
            }
        });

        m_imgGestures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manageGesturesIntent = new Intent(getApplicationContext(), ManageGesturesActivity.class);
                startActivity(manageGesturesIntent);
            }
        });
    }

    /////////////////////////////////////////////////////// ERROR DIALOGUE /////////////////////////////////////////////////

    private void showErrorDialogue(){
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.error_dialogue, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final TextView txtViewErrorMessage = promptView.findViewById(R.id.txt_error_message);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    /////////////////////////////////////////////////////// DEVICE CHECKS //////////////////////////////////////////////////

    private void openErrorDialogue(String m_SensorsNotProvideByDevice) {

        ErrorDialogue errorDialogue = new ErrorDialogue(m_SensorsNotProvideByDevice);
        errorDialogue.show(getSupportFragmentManager(), "example dialogue");

    }

    ///////////////////////////////////////////////////////  SENSORS    ///////////////////////////////////////////////////

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY && sensorEvent.values[0] > 7){
           // Toast.makeText(getApplicationContext(),"YeaBwoiProxim",Toast.LENGTH_SHORT).show();

//            Window window = this.getWindow(); //to get the window of your activity;
//            window.addFlags(FLAG_TURN_SCREEN_ON);
//            //WindowManager.LayoutParams(FLAG_TURN_SCREEN_ON) //that you desire:            FLAG_TURN_SCREEN_ON
        }

        if (m_isgestureListen == true) {
            synchronized (this) {
                switch (sensorEvent.sensor.getType()) {

                    case Sensor.TYPE_LINEAR_ACCELERATION:

                        float x = sensorEvent.values[0];
                        if (x > 10) {
                            m_imgGesturePerformed.setImageResource(R.drawable.shitty_arrow_right);
                        } else if (x < -10) {
                            m_imgGesturePerformed.setImageResource(R.drawable.shitty_arrow_left);
                        }

                        float z = sensorEvent.values[2];
                        if (z > 10) {
                            m_imgGesturePerformed.setImageResource(R.drawable.shitty_arrow_up);
                        }
                        break;

                    case Sensor.TYPE_ORIENTATION:
                        float m_orientx = sensorEvent.values[0];
                        float m_orienty = sensorEvent.values[1];
                        float m_orientz = sensorEvent.values[2];

                        if (m_orientx > 60 && 90 > m_orientx) {
                            m_imgGesturePerformed.setImageResource(R.drawable.clockwise_rotate_arrow);
                        } else if (m_orientx > 255 && 265 > m_orientx) {
                            m_imgGesturePerformed.setImageResource(R.drawable.anticlockwise_arrow_rotate);
                        }

                }
            }
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//       m_sensorManager.registerListener(this, m_sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//        m_sensorManager.registerListener(this, m_sensorGyro, SensorManager.SENSOR_DELAY_NORMAL);
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//       //m_sensorManager.unregisterListener(this);
//    }

}
