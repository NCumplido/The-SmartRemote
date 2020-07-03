package nich.project.thesmartremote;

/*
Some code from:

Dr. Tom Owen: CSC306
Dr. Deepak Sahoo: CSCM79
Swansea University

 */

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CompassCalibrateActivity extends AppCompatActivity implements SensorEventListener {

    int m_compassValues;

    Button m_btnClear,
           m_btnDone;

    TextView m_txtSavedList;

    SensorManager m_sensorManager;
    Sensor m_compassSensor;

    ImageButton m_imgBtnAdd;

    SharedPreferences m_sharedPref;
    SharedPreferences.Editor m_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_calibrate);

        // 0 - for private mode
        m_sharedPref = getApplicationContext().getSharedPreferences("MyPref", 0);
        m_editor = m_sharedPref.edit();

        setupView();

        setupSensors();

        setupListeners();

    }

    private void setupView() {

        m_btnClear = findViewById(R.id.btn_clear);
        m_btnDone = findViewById(R.id.btn_done);

        //TODO: Replace with select from list
        m_imgBtnAdd = findViewById(R.id.img_btn_add);

        m_txtSavedList = findViewById(R.id.txt_saved_list);

        //String deviceList = m_sharedPref.getAll(); TODO: Make dynamic for multiple items for list

        String deviceName = m_sharedPref.getString("device_name", null); // getting String
        int device_bearing = m_sharedPref.getInt("compass_bearing", 0); // getting Integer

        m_txtSavedList.setText("Saved devices: \n" + deviceName + " " + device_bearing );
    }

    private void setupSensors() {
        m_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        m_compassSensor = m_sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    }

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
                showInputDialog();
            }
        });

        m_btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplicationContext().getSharedPreferences("MyPref", 0).edit().clear().apply();
            }
        });
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(CompassCalibrateActivity.this);
        View inputDialogueView = layoutInflater.inflate(R.layout.input_dialogue, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompassCalibrateActivity.this);
        alertDialogBuilder.setView(inputDialogueView);

        final EditText deviceNameEditText = inputDialogueView.findViewById(R.id.edit_text_name);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        m_txtSavedList.append("\n" + deviceNameEditText.getText() + " " + m_compassValues + "\n");

                        m_editor.putString("device_name", String.valueOf(deviceNameEditText.getText())); // Storing string
                        m_editor.putInt("compass_bearing", m_compassValues); // Storing integer
                        m_editor.commit(); // commit changes
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
