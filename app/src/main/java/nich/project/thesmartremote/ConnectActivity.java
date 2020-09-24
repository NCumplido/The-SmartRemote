package nich.project.thesmartremote;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import nich.project.thesmartremote.network.ClientScanner;
import nich.project.thesmartremote.network.NetworkUtils;

public class ConnectActivity extends AppCompatActivity {

    private ArrayList<String> reachableIps;

    ////////// VIEW VARIABLES //////////
    Button btnOnOff,
            btnDiscover;
    ListView lstPeers;
    TextView txtConnectionStatus,
                txtResults;

    ////////// WIFI VARIABLES //////////
    WifiManager wifiManager;
    WifiInfo wifiInfo;
    Boolean isWifiEnabled;
    private String ipv4;

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
        isWifiEnabled();

    }

    /////////////////////////////////////////////////////// VIEW SETUP ///////////////////////////////////////////////////////
    public void setupView(){

        txtConnectionStatus = findViewById(R.id.txt_connection_status);

        txtResults = findViewById(R.id.txt_results);

        btnOnOff = findViewById(R.id.btn_wifi_on_off);

        btnDiscover = findViewById(R.id.btn_discover);
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanNetwork();
            }
        });

        lstPeers = findViewById(R.id.lst_peers);
        btnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { toggleWifi(); }
        });
    }

    /////////////////////////////////////////////////////// WIFI SETUP //////////////////////////////////////////////////////
    private void setupWifi() {

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiInfo =  wifiManager.getConnectionInfo();

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
            txtConnectionStatus.setText("Wifi on");
        } else{
            txtConnectionStatus.setText("Wifi off");
        }
    }

    public void scanNetwork(){

        ipv4 = NetworkUtils.getIPV4Address();

        //Todo: only do this if wifi is connected
        ClientScanner clientScannerTask = new ClientScanner(ipv4);
        reachableIps = null;
        try {
            reachableIps = clientScannerTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        txtResults.append("SSID: " + wifiInfo.getSSID());
        txtResults.append("\nBSSID: " + wifiInfo.getBSSID());
        txtResults.append("\nspeed: " + wifiInfo.getLinkSpeed());
        txtResults.append("\ngateway IP: " + NetworkUtils.getIpFromIntSigned(wifiManager.getDhcpInfo().gateway));
        //ipv4 = NetworkUtils.getIPV4Address();
        txtResults.append("\nLocal Ipv4 IP: " + ipv4);
        txtResults.append("\nReachable ips:" + reachableIps.toString());

        //for (String s : reachableIps){ txtResults.append("\n\t" + s); }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter( this,
                R.layout.view_network_device_entry, R.id.txt_ip_address);

        lstPeers.setAdapter(itemsAdapter);

    }

}

