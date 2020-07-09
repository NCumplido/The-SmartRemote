package nich.project.thesmartremote;

/*
Parts of code from:

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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CompassCalibrateActivity extends AppCompatActivity implements SensorEventListener {

    private int m_compassValues;

    private Button m_btnClear,
           m_btnDone,
           m_btnRefreshList;

    private SensorManager m_sensorManager;
    private Sensor m_compassSensor;

    private ImageButton m_imgBtnAdd;

    TextView txtDeviceId, txtDeviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_calibrate);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
// Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        setupView();

        setupSensors();

        setupListeners();

        loadList();

    }

    /////////////////////////////////////////////////////  VIEWS /////////////////////////////////////////////////////
    private void setupView() {

        m_btnClear = findViewById(R.id.btn_clear);
        m_btnDone = findViewById(R.id.btn_done);

        //TODO: Replace with select from list
        m_imgBtnAdd = findViewById(R.id.img_btn_add);

        m_btnRefreshList = findViewById(R.id.btn_refresh);

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
                Intent addDevice = new Intent(getApplicationContext(),DeviceDetail.class);
                addDevice.putExtra("device_Id",0);
                startActivity(addDevice);
            }
        });

        m_btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CompassCalibrateActivity.this, "Could be useful", Toast.LENGTH_SHORT).show();

            }
        });

        m_btnRefreshList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadList();

            }
        });

    }

    /////////////////////////////////////////////////////     LOAD LIST       /////////////////////////////////////////////////////

    public void loadList() {

        DeviceRepo repo = new DeviceRepo(getApplicationContext());

        ArrayList<HashMap<String, String>> deviceList =  repo.getStudentList();
        if(deviceList.size()!=0) {
            ListView lv = findViewById(R.id.lst_device_bearing);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    txtDeviceId = view.findViewById(R.id.txt_device_id);
                    String studentId = txtDeviceId.getText().toString();
                    txtDeviceName = view.findViewById(R.id.txt_device_name);
                    String deviceName = txtDeviceName.getText().toString();
                    Intent objIndent = new Intent(getApplicationContext(),DeviceDetail.class);
                    objIndent.putExtra("device_Id", Integer.parseInt( studentId));
                    objIndent.putExtra("device_name", deviceName);
                    startActivity(objIndent);
                }
            });
            ListAdapter adapter = new SimpleAdapter( CompassCalibrateActivity.this,
                    deviceList, R.layout.view_device_entry, new String[] { "id","name"}, new int[] {R.id.txt_device_id, R.id.txt_device_name});
            lv.setAdapter(adapter);
        }else{
            Toast.makeText(getApplicationContext(),"No devices!",Toast.LENGTH_SHORT).show();
        }

    }

    ///////////////////////////////////////////////////// BEARING SAVE DIALOG /////////////////////////////////////////////////////
/*
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
 */

    ///////////////////////////////////////////////////// SENSOR EVENTS /////////////////////////////////////////////////////
    @Override
    public void onSensorChanged(SensorEvent event) {        m_compassValues = (int) event.values[0];    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadList();
    }
}