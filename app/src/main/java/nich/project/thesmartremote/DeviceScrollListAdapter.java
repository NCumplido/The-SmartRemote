package nich.project.thesmartremote;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

class DeviceScrollListAdapter extends ArrayAdapter<String> {

    boolean m_isConnected;

    public DeviceScrollListAdapter(@NonNull Context context, String[] deviceNames, String[] deviceLocations) {
        super(context, R.layout.device_item, deviceNames); //TODO Need to add deviceLocations[] etc somehow, maybe through Device objects


    }

    public DeviceScrollListAdapter(@NonNull Context context, ArrayList<String> exampleDeviceInfo, boolean isConnected) {
        super(context, R.layout.device_item, exampleDeviceInfo); //TODO Need to add deviceLocations[] etc somehow, maybe through Device objects

        m_isConnected = isConnected;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater deviceItemInflater = LayoutInflater.from(getContext());
        View customDeviceView = deviceItemInflater.inflate(R.layout.device_item, parent, false);

        String singleDeviceName = getItem(position);

        TextView txtDeviceName = customDeviceView.findViewById(R.id.txt_name);
        TextView txtDeviceRoom = customDeviceView.findViewById(R.id.txt_room);
        ImageView imgDevicePic = customDeviceView.findViewById(R.id.img_device);
        TextView txtIsonline = customDeviceView.findViewById(R.id.txt_is_online);

        txtDeviceName.setText(singleDeviceName);
        //TODO: txtDeviceRoom.setText();
        imgDevicePic.setImageResource(R.drawable.ic_launcher_foreground); //TODO: Use array

        String tempString = "Conected";
        SpannableString spanConnected = new SpannableString(tempString);
        spanConnected.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanConnected.length(), 0);

        String tempStringDisconnected = "Disconected";
        SpannableString spanDisconnected = new SpannableString(tempStringDisconnected);
        spanDisconnected.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanDisconnected.length(), 0);

        if(m_isConnected == true){
            txtIsonline.setText(spanConnected);
            txtIsonline.setTextColor(GREEN);
        }
        else {
            txtIsonline.setText(spanDisconnected);
            txtIsonline.setTextColor(RED);
        }

        return customDeviceView;
    }
}
