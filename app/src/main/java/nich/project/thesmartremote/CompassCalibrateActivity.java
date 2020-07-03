package nich.project.thesmartremote;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CompassCalibrateActivity extends AppCompatActivity implements SensorEventListener {

    int m_compassValues;

    Button m_btnSave,
           m_btnDone;

    TextView m_txtSavedList;

    SensorManager m_sensorManager;
    Sensor m_compassSensor;

    ImageButton m_imgBtnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_calibrate);

        setupView();

        setupSensors();

        setupListeners();

    }

    private void setupView() {

        m_btnSave = findViewById(R.id.btn_save);
        m_btnDone = findViewById(R.id.btn_done);

        //TODO: Replace with select from list
        m_imgBtnAdd = findViewById(R.id.img_btn_add);

        m_txtSavedList = findViewById(R.id.txt_saved_list);
    }

    private void setupSensors() {
        m_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        m_compassSensor = m_sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    }

    private void setupListeners() {

        m_btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });

        m_sensorManager.registerListener(this, m_compassSensor, SensorManager.SENSOR_DELAY_NORMAL);

        m_imgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(CompassCalibrateActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialogue, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompassCalibrateActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editTextName = (EditText) promptView.findViewById(R.id.edit_text_name);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        m_txtSavedList.append("\n" + editTextName.getText() + " " + m_compassValues + "\n");
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        m_compassValues = (int) event.values[0];

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
