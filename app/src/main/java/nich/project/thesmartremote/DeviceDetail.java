package nich.project.thesmartremote;

/*
Parts of code from:
Dr. Tom Owen: CSC306    TVYM
Swansea University
*/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeviceDetail extends AppCompatActivity implements View.OnClickListener{

    Button m_btnSave ,
            m_btnDelete,
            m_btnClose;
    EditText m_editTextName;
    EditText m_editTextBearing;
    private int _Device_Id=0;

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

                Toast.makeText(this,"New Student Insert",Toast.LENGTH_SHORT).show();
            }else{

                repo.update(deviceDBItem);
                Toast.makeText(this,"Student Record updated",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btn_delete)){
            DeviceRepo repo = new DeviceRepo(this);
            repo.delete(_Device_Id);
            Toast.makeText(this, "Student Record Deleted", Toast.LENGTH_SHORT);
            finish();
        }else if (view== findViewById(R.id.btn_close)){
            finish();
        }


    }

}
