package nich.project.thesmartremote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileDetailActivity extends AppCompatActivity {

    Button m_btnSave,
            m_btnDelete,
            m_btnClose;
    EditText m_editTextName;
    TextView m_txtDevices;
    private int _Profile_Id=0,
            m_compassValues;
    private String m_profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
    }
}