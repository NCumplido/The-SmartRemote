package nich.project.thesmartremote;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ConnectActivity extends AppCompatActivity {

    ////////// VIEW VARIABLES //////////
    Button btnOnOff, btnDiscover, btnSend;
    ListView lstPeers;
    EditText editTextMessage;
    TextView txtMessage, txtConnectionStatus;

    ////////// WIFI VARIABLES //////////
    WifiManager wifiManager;
    Boolean isWifiEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);


        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        setupView();
        setupWifi();

    }

    /////////////////////////////////////////////////////// VIEW SETUP ///////////////////////////////////////////////////////
    public void setupView(){

        btnOnOff = findViewById(R.id.btn_wifi_on_off);
        btnDiscover = findViewById(R.id.btn_discover);
        btnSend = findViewById(R.id.btn_send);
        lstPeers = findViewById(R.id.lst_peers);
        editTextMessage = findViewById(R.id.edit_text_message);
        txtMessage = findViewById(R.id.txt_message);
        txtConnectionStatus = findViewById(R.id.txt_connection_status);

        btnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { toggleWifi(); }
        });
    }

    /////////////////////////////////////////////////////// WIFI SETUP //////////////////////////////////////////////////////
    private void setupWifi() {

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        isWifiEnabled = wifiManager.isWifiEnabled();

        if(isWifiEnabled==true) {
            txtConnectionStatus.setText("Connected");
        } else{
            txtConnectionStatus.setText("Disconnected");
        }

        isWifiEnabled();
    }

    public void toggleWifi(){
        wifiManager.setWifiEnabled(!wifiManager.isWifiEnabled());

        isWifiEnabled();
    }

    public void isWifiEnabled(){
        isWifiEnabled = wifiManager.isWifiEnabled();

        if(isWifiEnabled==true) {
            txtConnectionStatus.setText("Wifi off");
        } else{
            txtConnectionStatus.setText("Wifi on");
        }
    }

}

