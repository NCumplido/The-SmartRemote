package nich.project.thesmartremote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageDevicesActivity extends AppCompatActivity {

    private Button m_btnDone;

    private ImageButton m_imgBtnAdd;

    TextView txtDeviceId, 
            txtDeviceName,
            txtDeviceLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_devices);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        setupView();

        setupListeners();

        loadList();
    }

    private void setupView() {

        m_btnDone = findViewById(R.id.btn_done);

        //TODO: Replace with select from list
        m_imgBtnAdd = findViewById(R.id.img_btn_add_device);

    }

    private void setupListeners() {

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

    }

    private void loadList() {

        DeviceRepo repo = new DeviceRepo(getApplicationContext());

        ArrayList<HashMap<String, String>> deviceList =  repo.getDeviceList();
        if(deviceList.size()!=0) {
            ListView lv = findViewById(R.id.lst_devices);
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
            ListAdapter adapter = new SimpleAdapter( ManageDevicesActivity.this,
                    deviceList, R.layout.view_device_entry, new String[] { "id","name"}, new int[] {R.id.txt_device_id, R.id.txt_device_name});
            lv.setAdapter(adapter);
        }else{
            Toast.makeText(getApplicationContext(),"No devices!",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadList();
    }

}