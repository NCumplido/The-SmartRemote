package nich.project.thesmartremote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ManageDevicesActivity extends AppCompatActivity {

    private Button m_btnClear,
            m_btnDone;

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

        m_btnClear = findViewById(R.id.btn_clear_device_list);
        m_btnDone = findViewById(R.id.btn_done);

        //TODO: Replace with select from list
        m_imgBtnAdd = findViewById(R.id.img_btn_add_device);

    }

    private void setupListeners() {
    }

    private void loadList() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadList();
    }

}