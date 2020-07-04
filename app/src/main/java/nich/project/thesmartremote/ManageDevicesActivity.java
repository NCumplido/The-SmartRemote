package nich.project.thesmartremote;

/*
Some code from:

Dr. Tom Owen: CSC306
Swansea University
 */

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ManageDevicesActivity extends AppCompatActivity {

    /* TODO: What do
         have a manage devices activity displaying a list of all connected devices
         or one activity that manages one device at a time??? */

    Button m_btnCalibrate, m_btnEditDetails;
    TextView m_txtDeviceInformation;
    ListView m_listViewDevices;

    ListAdapter m_customDeviceAdapter;

    String m_deviceInformation;

    Bundle m_extras;

    public static final String TAG = "ManageDevicesExtras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_devices);

        m_extras = getIntent().getExtras();
        if (m_extras == null) {
            Log.d(TAG, "No extras");

// POSSIBLE SOLUTION: Keep this, could manage old/unused/offline devices
// This doesn't necessarily have to be here: there are multiple methods of implementing this
            setupAllDevicesList();
            return;
        }

        setupView();
        setupListeners();

    }

    /////////////////////////////////////////////// VIEW ///////////////////////////////////////////////

    private void setupView() {

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        m_btnCalibrate = findViewById(R.id.btn_cal_gestures);

        //TODO Device objects etc.
        m_btnEditDetails = findViewById(R.id.btn_edit_details);

        m_txtDeviceInformation = findViewById(R.id.txt_device_information);
        m_deviceInformation = m_extras.getString("DeviceInformation");
        m_txtDeviceInformation.setText(m_deviceInformation);
    }

    /////////////////////////////////////////////// LISTENERS //////////////////////////////////////////

    private void setupListeners() {

    }

////////////////////////////////////  LISTVIEW SETUP AND POPULATE /////////////////////////////

/*
This method will manage the view and listeners as it will only be called under certain conditions
eg. depending on where this activity will be called from
*/
    private void setupAllDevicesList(){

        m_listViewDevices = findViewById(R.id.lst_devices);

        //Of course, the list would be populated from persistant storage.
        //Possibly use a list of Device Objects?
        //ArrayList<Device> exampleDevices = new ArrayList<Device>();

        ArrayList<String> arrayListExampleDeviceNames = new ArrayList<>();
        arrayListExampleDeviceNames.add("Echo");
        arrayListExampleDeviceNames.add( "Alexa");
        arrayListExampleDeviceNames.add("Bulb");
        arrayListExampleDeviceNames.add( "SmartTV");

        ArrayList<String> arrayListExampleRooms = new ArrayList<>();
        arrayListExampleRooms.add("Bedroom");
        arrayListExampleRooms.add( "Living room");
        arrayListExampleRooms.add("Kitchen");
        arrayListExampleRooms.add( "Bathroom");

//        ListAdapter customDeviceAdapter = new DeviceScrollListAdapter(this, arrayListExampleDeviceNames, arrayListExampleRooms);

        ArrayList<String> exampleDeviceInfo = new ArrayList<>();
        int i = 0;
        while(i<arrayListExampleDeviceNames.size()){
            exampleDeviceInfo.add(arrayListExampleDeviceNames.get(i) + " " + arrayListExampleRooms.get(i) );
            i++;
        }

        m_customDeviceAdapter = new DeviceScrollListAdapter(this, exampleDeviceInfo, true);

       /* Device alexa = new Device("alexa", "Kitchen", 67, 1000);
        Device smartTV = new Device("smartTV", "LivingRoom", 22, 1000);
        Device Bulb = new Device("Bulb", "Bedroom", 127, 1005);
        Device bulb2 = new Device("Bulb2", "MasterBedroom", 359, 999);
        exampleDevices.add(alexa);
        exampleDevices.add(smartTV);
        exampleDevices.add(Bulb);
        exampleDevices.add(bulb2);
        exampleDevices.toArray();
        exampleDevices.toString();
        ListAdapter customDeviceAdapter = new DeviceScrollListAdapter(this, exampleDevices); */

        m_listViewDevices.setAdapter(m_customDeviceAdapter);

    }
}
