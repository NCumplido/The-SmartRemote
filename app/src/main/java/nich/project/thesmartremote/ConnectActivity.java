package nich.project.thesmartremote;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConnectActivity extends AppCompatActivity {

    Button btnOnOff, btnDiscover, btnSend;
    ListView lstPeers;
    EditText editTextMessage;
    TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        setupView();

    }

    /////////////////////////////////////////////////////// VIEW STUFF ///////////////////////////////////////////////////////
    public void setupView(){

        btnOnOff = findViewById(R.id.btn_wifi_on_off);
        btnDiscover = findViewById(R.id.btn_discover);
        btnSend = findViewById(R.id.btn_send);
        lstPeers = findViewById(R.id.lst_peers);
        editTextMessage = findViewById(R.id.edit_text_message);
        txtMessage = findViewById(R.id.txt_message);
    }
}

