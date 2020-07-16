package nich.project.thesmartremote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LocationProfileDetail extends AppCompatActivity implements View.OnClickListener{

    Button m_btnSave,
            m_btnDelete,
            m_btnClose;
    EditText m_editTextName;
    private int _LocationProfile_Id=0;
    private String m_locationProfileName;

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

        m_editTextName.setText(locationProfileDBItem.name);

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
}