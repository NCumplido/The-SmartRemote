package nich.project.thesmartremote;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ConnectActivity extends AppCompatActivity {

    public static final String TAG = "NSD";

    ////////// VIEW VARIABLES //////////
    Button btnOnOff, btnDiscover, btnConnect, btnRegister;
    ListView lstPeers;
    EditText editTextMessage;
    TextView txtMessage, txtConnectionStatus;

    ////////// WIFI VARIABLES //////////
    WifiManager wifiManager;
    Boolean isWifiEnabled;

    private static final String SERVICE_TYPE = "_localdash._tcp";
    private static final String TRAILING_DOT = ".";

    private ServerSocket serverSocket;
    private int localPort;

    private NsdManager.RegistrationListener registrationListener;
    private NsdManager.DiscoveryListener discoveryListener;
    private NsdManager nsdManager;
    private String serviceName;
    private NsdServiceInfo mService;
    private NsdHelper nsdHelper;

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

        initializeRegistrationListener();
        initializeServerSocket();
        nsdManager = (NsdManager) getSystemService(NSD_SERVICE);
        setupNSDHelper();
    }

    /////////////////////////////////////////////////////// VIEW SETUP ///////////////////////////////////////////////////////
    public void setupView(){

        btnOnOff = findViewById(R.id.btn_wifi_on_off);
        btnRegister = findViewById(R.id.btn_register);
        btnDiscover = findViewById(R.id.btn_discover);
        btnConnect = findViewById(R.id.btn_connect);
        lstPeers = findViewById(R.id.lst_peers);
        txtConnectionStatus = findViewById(R.id.txt_connection_status);

        btnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { toggleWifi(); }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discover();
            }
        });
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
    }

    public void register() {
        registerService(localPort);
    }

    public void discover() {
        if (discoveryListener == null) initializeDiscoveryListener();

        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void connect() {

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

    public void registerService(int port) {
        // Create the NsdServiceInfo object, and populate it.
        NsdServiceInfo serviceInfo = new NsdServiceInfo();

        // The name is subject to change based on conflicts
        // with other services advertised on the same network.
        serviceInfo.setServiceName("SmartRemote");
        serviceInfo.setServiceType("_remote._tcp");
        serviceInfo.setPort(port);

    }

    public void initializeServerSocket() {
        // Initialize a server socket on the next available port.
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Store the chosen port.
        localPort = serverSocket.getLocalPort();

    }

    public void setupNSDHelper(){

        }

    public void initializeRegistrationListener() {
        registrationListener = new NsdManager.RegistrationListener() {

            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                // Save the service name. Android may have changed it in order to
                // resolve a conflict, so update the name you initially requested
                // with the name Android actually used.
                serviceName = NsdServiceInfo.getServiceName();
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Registration failed! Put debugging code here to determine why.
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                // Service has been unregistered. This only happens when you call
                // NsdManager.unregisterService() and pass in this listener.
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Unregistration failed. Put debugging code here to determine why.
            }
        };
    }

    public void initializeDiscoveryListener() {

        // Instantiate a new DiscoveryListener
        discoveryListener = new NsdManager.DiscoveryListener() {

            // Called as soon as service discovery begins.
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                // A service was found! Do something with it.
                Log.d(TAG, "Service discovery success" + service);
                if (!service.getServiceType().equals(SERVICE_TYPE)) {
                    // Service type is the string containing the protocol and
                    // transport layer for this service.
                    Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals(serviceName)) {
                    // The name of the service tells the user what they'd be
                    // connecting to. It could be "Bob's Chat App".
                    Log.d(TAG, "Same machine: " + serviceName);
                } else if (service.getServiceName().contains("SmartRemote")){
                    nsdManager.resolveService(service, new NsdManager.ResolveListener() {
                        @Override
                        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                            // Called when the resolve fails. Use the error code to debug.
                            Log.e(TAG, "Resolve failed: " + errorCode);
                        }

                        @Override
                        public void onServiceResolved(NsdServiceInfo serviceInfo) {
                            Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

                            if (serviceInfo.getServiceName().equals(serviceName)) {
                                Log.d(TAG, "Same IP.");
                                return;
                            }
                            mService = serviceInfo;
                            int port = mService.getPort();
                            InetAddress host = mService.getHost();
                        }
                    });
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                // When the network service is no longer available.
                // Internal bookkeeping code goes here.
                Log.e(TAG, "service lost: " + service);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }
        };
    }

    @Override
    protected void onPause() {
        if (nsdHelper != null) {
            nsdHelper.tearDown();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nsdHelper != null) {
            nsdHelper.registerService(connection.getLocalPort());
            nsdHelper.discoverServices();
        }
    }

    @Override
    protected void onDestroy() {
        nsdHelper.tearDown();
        connection.tearDown();
        super.onDestroy();
    }

    // NsdHelper's tearDown method
    public void tearDown() {
        nsdManager.unregisterService(registrationListener);
        nsdManager.stopServiceDiscovery(discoveryListener);
    }

}

