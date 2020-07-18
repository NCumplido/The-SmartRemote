package nich.project.thesmartremote;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class LocationProfileDetail extends AppCompatActivity implements SensorEventListener, View.OnClickListener{

    Button m_btnSave,
            m_btnDelete,
            m_btnClose;
    EditText m_editTextName;
    private int _LocationProfile_Id=0,
            _Pivot_Id=0,
            m_compassValues;

    private String m_locationProfileName;

    private SensorManager m_sensorManager;
    private Sensor m_compassSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_profile_detail);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        m_btnSave = findViewById(R.id.btn_save_location_profile);
        m_btnDelete = findViewById(R.id.btn_delete);
        m_btnClose = findViewById(R.id.btn_close);

        m_editTextName = findViewById(R.id.edit_txt_location_profile_name);

        m_btnSave.setOnClickListener(this);
        m_btnDelete.setOnClickListener(this);
        m_btnClose.setOnClickListener(this);

        _LocationProfile_Id =0;
        Intent intent = getIntent();
        _LocationProfile_Id =intent.getIntExtra("locationProfile_Id", 0);
        m_locationProfileName = intent.getStringExtra("locationProfile_name");
        LocationProfileRepo repo = new LocationProfileRepo(this);
        LocationProfileDBItem locationProfileDBItem = new LocationProfileDBItem();
        locationProfileDBItem = repo.getLocationProfileById(_LocationProfile_Id);

        _Pivot_Id =0;
        PivotRepo pivotRepo = new PivotRepo(this);
        PivotDeviceProfileDBItem pivotDeviceProfileDBItem = new PivotDeviceProfileDBItem();
        pivotDeviceProfileDBItem = pivotRepo.getPivotById(_Pivot_Id);

        m_editTextName.setText(locationProfileDBItem.name);

        setupSensors();
        loadList();

    }

    ///////////////////////////////////////////////////// SETUP SENSORS /////////////////////////////////////////////////////

    private void setupSensors() {
        m_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        m_compassSensor = m_sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        m_sensorManager.registerListener(this, m_compassSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btn_save_location_profile)){
            LocationProfileRepo repo = new LocationProfileRepo(this);
            LocationProfileDBItem locationProfileDBItem = new LocationProfileDBItem();
            locationProfileDBItem.name = m_editTextName.getText().toString();
            locationProfileDBItem.locationProfile_ID = _LocationProfile_Id;

            if (_LocationProfile_Id == 0){
                _LocationProfile_Id = repo.insert(locationProfileDBItem);

                Toast.makeText(this,"New LocationProfile insert",Toast.LENGTH_SHORT).show();
            }else{

                repo.update(locationProfileDBItem);
                Toast.makeText(this,"LocationProfile record updated",Toast.LENGTH_SHORT).show();
            }

            finish();

        }else if (view== findViewById(R.id.btn_delete)){
            LocationProfileRepo repo = new LocationProfileRepo(this);
            repo.delete(_LocationProfile_Id);
            Toast.makeText(this, "LocationProfile record deleted", Toast.LENGTH_SHORT).show();
            finish();
        }else if (view== findViewById(R.id.btn_close)){
            finish();
        }

    }

    private void loadList() {

        DeviceRepo repo = new DeviceRepo(getApplicationContext());

        ArrayList<HashMap<String, String>> deviceList =  repo.getDeviceList();
        if(deviceList.size()!=0) {
            ListView lv = findViewById(R.id.lst_devices_in_profile_detail);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    //txtDeviceId = view.findViewById(R.id.txt_device_id);
                    //String deviceId = txtDeviceId.getText().toString();
                    //txtDeviceName = view.findViewById(R.id.txt_device_name);
                    //String deviceName = txtDeviceName.getText().toString();
                    Intent objIndent = new Intent(getApplicationContext(),DeviceDetail.class);
                    //objIndent.putExtra("device_Id", Integer.parseInt( studentId));
                    //objIndent.putExtra("device_name", deviceName);
                    startActivity(objIndent);
                    //Save device bearing here
                    saveBearing();
                }
            });
            ListAdapter adapter = new SimpleAdapter( LocationProfileDetail.this,
                    deviceList, R.layout.view_device_entry, new String[] { "id","name"}, new int[] {R.id.txt_device_id, R.id.txt_device_name});
            lv.setAdapter(adapter);
        }else{
            Toast.makeText(getApplicationContext(),"No devices!",Toast.LENGTH_SHORT).show();
        }

    }

    public void saveBearing(){
        PivotRepo repo = new PivotRepo(this);
        PivotDeviceProfileDBItem pivotDeviceProfileDBItem = new PivotDeviceProfileDBItem();
        //pivotDeviceProfileDBItem.pivot_ID = pivot;
        //pivotDeviceProfileDBItem.device_ID = m_editTextName.getText().toString();
        pivotDeviceProfileDBItem.deviceName = m_editTextName.getText().toString();

        //pivotDeviceProfileDBItem.profile_ID = m_editTextName.getText().toString();
        pivotDeviceProfileDBItem.profileName = m_editTextName.getText().toString();

        if (_LocationProfile_Id == 0){
            _LocationProfile_Id = repo.insert(pivotDeviceProfileDBItem);

            Toast.makeText(this,"New pivotDeviceProfile insert",Toast.LENGTH_SHORT).show();
        }else{

            repo.update(pivotDeviceProfileDBItem);
            Toast.makeText(this,"PivotDeviceProfile record updated",Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        m_compassValues = (int) event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadList();
    }
}