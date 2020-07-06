package nich.project.thesmartremote;

/*
Some code from:

Dr. Tom Owen: CSC306
Dr. Deepak Sahoo: CSCM79
Swansea University
 */

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CompassCalibrateActivity extends AppCompatActivity implements SensorEventListener {

    private int m_compassValues;

    private Button m_btnClear,
           m_btnDone;

    private SensorManager m_sensorManager;
    private Sensor m_compassSensor;

    private ImageButton m_imgBtnAdd;

    ListAdapter m_adapter;
    ListView m_lstView;
    ArrayList<Device> m_deviceListExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_calibrate);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
// Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        setupDB();

        setupView();

        setupSensors();

        setupListeners();

    }

    private void setupDB() {
        ///////////////////////////////////////////////////// EXAMPLE STUFF WILL REMOVE ONCE SQL HAS BEEN SETUP /////////////////////////////////////////////////////
        Device tv = new Device("Tv", 360);
        Device light = new Device("Light", 180);
        Device smartSwitch = new Device("smartSwitch", 90);
        m_deviceListExample = new ArrayList<>();
        m_deviceListExample.add(tv);
        m_deviceListExample.add(light);
        m_deviceListExample.add(smartSwitch);

        m_adapter = new DeviceBearingListAdapter(this, m_deviceListExample);
    }

    /////////////////////////////////////////////////////  VIEWS /////////////////////////////////////////////////////
    private void setupView() {

        m_btnClear = findViewById(R.id.btn_clear);
        m_btnDone = findViewById(R.id.btn_done);

        //TODO: Replace with select from list
        m_imgBtnAdd = findViewById(R.id.img_btn_add);

        m_lstView = findViewById(R.id.lst_device_bearing);
        m_lstView.setAdapter(m_adapter);

    }

    ///////////////////////////////////////////////////// SETUP SENSORS /////////////////////////////////////////////////////

    private void setupSensors() {
        m_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        m_compassSensor = m_sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    }

    /////////////////////////////////////////////////////  SETUP LISTENERS /////////////////////////////////////////////////////

    private void setupListeners() {
        m_sensorManager.registerListener(this, m_compassSensor, SensorManager.SENSOR_DELAY_NORMAL);

        m_btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });

        m_imgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showInputDialog();
                Toast.makeText(CompassCalibrateActivity.this, "Unused", Toast.LENGTH_SHORT).show();
            }
        });

        m_btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CompassCalibrateActivity.this, "Clear List btn pressed", Toast.LENGTH_SHORT).show();

            }
        });

        m_lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Device singleDeviceItem = m_deviceListExample.get(position);
                String deviceName = singleDeviceItem.getName();
                int deviceBearing = singleDeviceItem.getBearing();
                showBearingSavetDialog(deviceName);
                Toast.makeText(CompassCalibrateActivity.this, deviceName + deviceBearing, Toast.LENGTH_SHORT).show();
                //Toast.makeText(CompassCalibrateActivity.this, "Device name: " + deviceName + "\n Could start new activity on tap", Toast.LENGTH_SHORT).show();
            }
        });

        m_lstView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String tapped = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(CompassCalibrateActivity.this, tapped, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    ///////////////////////////////////////////////////// BEARING SAVE DIALOG /////////////////////////////////////////////////////

    protected void showBearingSavetDialog(final String deviceName) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(CompassCalibrateActivity.this);
        View inputDialogueView = layoutInflater.inflate(R.layout.bearing_save_dialogue, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompassCalibrateActivity.this);
        alertDialogBuilder.setView(inputDialogueView);

        TextView txtDeviceName = inputDialogueView.findViewById(R.id.txt_device_name_save_dialogue);
        txtDeviceName.setText(deviceName);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ////Change bearing here
                        Toast.makeText(CompassCalibrateActivity.this, deviceName + " bearing set to: " + m_compassValues, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create a dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    ///////////////////////////////////////////////////// sENSOR EVENTS /////////////////////////////////////////////////////
    @Override
    public void onSensorChanged(SensorEvent event) {

        m_compassValues = (int) event.values[0];

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}