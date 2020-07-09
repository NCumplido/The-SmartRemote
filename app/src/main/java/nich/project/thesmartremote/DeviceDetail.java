package nich.project.thesmartremote;

/*
Parts of code from:
Dr. Tom Owen: CSC306    TVYM
Swansea University
*/

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeviceDetail extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    Button m_btnSave ,
            m_btnDelete,
            m_btnClose;
    EditText m_editTextName;
    EditText m_editTextBearing;
    private int _Device_Id=0,
            m_compassValues;
    private String m_deviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);

        m_btnSave = findViewById(R.id.btn_save);
        m_btnDelete = findViewById(R.id.btn_delete);
        m_btnClose = findViewById(R.id.btn_close);

        m_editTextName = findViewById(R.id.edit_text_name);
        m_editTextBearing = findViewById(R.id.edit_text_bearing);

        m_btnSave.setOnClickListener(this);
        m_btnDelete.setOnClickListener(this);
        m_btnClose.setOnClickListener(this);

        _Device_Id =0;
        Intent intent = getIntent();
        _Device_Id =intent.getIntExtra("device_Id", 0);
        m_deviceName = intent.getStringExtra("device_name");
        DeviceRepo repo = new DeviceRepo(this);
        DeviceDBItem deviceDBItem = new DeviceDBItem();
        deviceDBItem = repo.getDeviceById(_Device_Id);

        m_editTextBearing.setText(String.valueOf(deviceDBItem.bearing));
        m_editTextName.setText(deviceDBItem.name);
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btn_save)){
            DeviceRepo repo = new DeviceRepo(this);
            DeviceDBItem deviceDBItem = new DeviceDBItem();
            deviceDBItem.bearing = Integer.parseInt(m_editTextBearing.getText().toString());
            deviceDBItem.name = m_editTextName.getText().toString();
            deviceDBItem.device_ID = _Device_Id;

            if (_Device_Id == 0){
                _Device_Id = repo.insert(deviceDBItem);

                Toast.makeText(this,"New device insert",Toast.LENGTH_SHORT).show();
            }else{

                repo.update(deviceDBItem);
                Toast.makeText(this,"Device record updated",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btn_delete)){
            DeviceRepo repo = new DeviceRepo(this);
            repo.delete(_Device_Id);
            Toast.makeText(this, "Device record deleted", Toast.LENGTH_SHORT);
            finish();
        }else if (view== findViewById(R.id.btn_close)){
            finish();
        }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        m_compassValues = (int) event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
