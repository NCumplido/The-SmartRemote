package nich.project.thesmartremote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    TextView m_txtSittingLocationProfile,
                m_txtabout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setupView();
        setupListeners();
    }

    //////////////////////////////////////// SETUP VIEW ////////////////////////////////////////
    private void setupView() {

        m_txtSittingLocationProfile = findViewById(R.id.txt_sitting_location_profiles);
        m_txtabout = findViewById(R.id.txt_about);

    }

    //////////////////////////////////////// SETUP LISTENERS ///////////////////////////////////
    private void setupListeners() {

        m_txtSittingLocationProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sittingLocationProfilesIntent = new Intent(getApplicationContext(), SittingLocationProfilesActivity.class);
                startActivity(sittingLocationProfilesIntent);
            }
        });

        m_txtabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(aboutIntent);
            }
        });

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}