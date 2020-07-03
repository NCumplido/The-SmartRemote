package nich.project.thesmartremote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.GREEN;
import static android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;

//https://developer.android.com/reference/android/net/wifi/WifiManager
//https://developer.android.com/training/connect-devices-wirelessly/wifi-direct Create P2P connections with Wi-Fi Direct

//https://developers.google.com/nearby/connections/overview
//https://developer.android.com/training/connect-devices-wirelessly

public class MainActivity extends AppCompatActivity implements SensorEventListener { //This new comment of mine

    private TextView m_txtWifiStatus;

    private ImageView m_imgGesturePerformed,
                        m_imgCalibrate;

    private SensorManager m_sensorManager;
    private Sensor m_sensorAccel,
            m_sensorGyro,
            m_sensorProxim;

    private List<Sensor> m_deviceSensorsList;
    private ArrayList<Sensor> m_requiredSensorsList;

    private float m_ZFlickThreshold = 10, m_XFlickThreshold = 10, m_YFlickThreshold = 10;
    private float m_orientx,
                    m_orienty,
                    m_orientz,
                    m_previousProxValue;

    private String m_SensorsNotProvideByDevice;

    private boolean m_isgestureListen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        deviceHasSensors();

        setupView();

        setupListeners();

    }

    /////////////////////////////////////////////////////// SENSOR CHECK /////////////////////////////////////////////////////

    //Check if device has all sensors
    //Return if required sensor is unavailable, give notification and limit functions/features
    private void deviceHasSensors() {

        m_deviceSensorsList = m_sensorManager.getSensorList(Sensor.TYPE_ALL);
        m_requiredSensorsList = new ArrayList<>();

        m_sensorAccel = m_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        m_sensorGyro = m_sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        m_sensorProxim = m_sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        m_requiredSensorsList.add(m_sensorAccel);
        m_requiredSensorsList.add(m_sensorGyro);
        m_requiredSensorsList.add(m_sensorProxim);


        if (m_deviceSensorsList.contains(m_sensorAccel)) {
            //Toast.makeText(getApplicationContext(),"m_sensorAccel",Toast.LENGTH_SHORT).show();
        }
        else
        {m_SensorsNotProvideByDevice += " Accelerometer";}

        if (m_deviceSensorsList.contains(m_sensorGyro)) {
            //Toast.makeText(getApplicationContext(),"m_sensorGyro",Toast.LENGTH_SHORT).show();
        }
        else
        {m_SensorsNotProvideByDevice += " Gyroscope";}

        if (m_deviceSensorsList.contains(m_sensorProxim)) {
            //Toast.makeText(getApplicationContext(),"m_sensorGyro",Toast.LENGTH_SHORT).show();
        }
        else
        {m_SensorsNotProvideByDevice += " Proximity";}

        if(m_SensorsNotProvideByDevice != null){
            openErrorDialogue(m_SensorsNotProvideByDevice);
        }

    }

    /////////////////////////////////////////////////////// WIFI STUFF ///////////////////////////////////////////////////////



    /////////////////////////////////////////////////////// VIEW STUFF ///////////////////////////////////////////////////////

    private void setupView() {

        m_txtWifiStatus = findViewById(R.id.txt_connect_status);
        m_txtWifiStatus.setTypeface(m_txtWifiStatus.getTypeface(), Typeface.ITALIC);
        m_txtWifiStatus.setTextColor(GREEN);

        m_imgGesturePerformed = findViewById(R.id.img_gesture_performed);

        m_imgCalibrate = findViewById(R.id.img_calibrate);

    }

    /////////////////////////////////////////////////////// LISTENERS ///////////////////////////////////////////////////////

    private void setupListeners() {

        //linear acceleration = acceleration - acceleration due to gravity
        m_sensorAccel = m_sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        m_sensorManager.registerListener(this, m_sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
        m_sensorManager.registerListener(this, m_sensorProxim, SensorManager.SENSOR_DELAY_NORMAL);

        m_sensorManager.registerListener(this,
                m_sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                m_sensorManager.SENSOR_DELAY_GAME);

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

        m_imgCalibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CompassCalibrateintent = new Intent(getApplicationContext(), CompassCalibrateActivity.class);
                startActivity(CompassCalibrateintent);
            }
        });
    }

    /////////////////////////////////////////////////////// DEVICE CHECKS //////////////////////////////////////////////////

    private void openErrorDialogue(String m_SensorsNotProvideByDevice) {

        ErrorDialogue errorDialogue = new ErrorDialogue(m_SensorsNotProvideByDevice);
        errorDialogue.show(getSupportFragmentManager(), "example dialogue");

    }

    /////////////////////////////////////////////////////// SENSORS     ///////////////////////////////////////////////////

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
                        if (x > 15) {
                            m_imgGesturePerformed.setImageResource(R.drawable.shitty_arrow_right);
                        } else if (x < -15) {
                            m_imgGesturePerformed.setImageResource(R.drawable.shitty_arrow_left);
                        }

                        float z = sensorEvent.values[2];
                        if (z > 15) {
                            m_imgGesturePerformed.setImageResource(R.drawable.shitty_arrow_up);
                        }
                        break;

                    case Sensor.TYPE_ORIENTATION:
                        m_orientx = sensorEvent.values[0];
                        m_orienty = sensorEvent.values[1];
                        m_orientz = sensorEvent.values[2];

                        if (m_orientx > 60 && 90 > m_orientx) {
                            m_imgGesturePerformed.setImageResource(R.drawable.clockwise_rotate_arrow);
                        } else if (m_orientx > 240 && 260 > m_orientx) {
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
//       m_sensorManager.registerListener(this, m_sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
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
