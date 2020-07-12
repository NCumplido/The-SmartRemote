package nich.project.thesmartremote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddDeviceActivity extends AppCompatActivity {

    Button m_btnSave,
    m_btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        setupView();
        setupListeners();

    }

    //////////////////////////////// SETUP VIEW ////////////////////////////////
    private void setupView() {

        m_btnSave = findViewById(R.id.btn_save);
        m_btnCancel = findViewById(R.id.btn_cancel);

    }

    //////////////////////////////// SETUP LISTENERS ///////////////////////////
    private void setupListeners() {

        m_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addDevice();

            }
        });

        m_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cancelIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cancelIntent);

            }
        });

    }

    //////////////////////////////// ADD DEVICE ////////////////////////////////
    private void addDevice() {

        Intent addIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(addIntent);

    }

}
