package nich.project.thesmartremote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CompassCalibrateActivity extends AppCompatActivity implements SensorEventListener {

    Button m_btnSave,
           m_btnDone;

    ImageButton m_imgBtnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_calibrate);

        setupView();

        setupListeners();

    }

    private void setupView() {

        m_btnSave = findViewById(R.id.btn_save);
        m_btnDone = findViewById(R.id.btn_done);

        m_imgBtnAdd = findViewById(R.id.img_btn_add);
    }

    private void setupListeners() {

        m_btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
