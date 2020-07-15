package nich.project.thesmartremote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

///////////////////////////////////////////////////// WAS SUPPOSED TO BE A SCROLL LIST BUT SCRLlISTS DON'T HAVE .SETaDAPTER SO A NORMAL LIST IS USED INSTEAD

class DeviceBearingListAdapter extends ArrayAdapter<Device> {

    public DeviceBearingListAdapter(@NonNull Context context, ArrayList<Device> arrayDevices) {
        super(context, R.layout.device_item, arrayDevices);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View customRow = layoutInflater.inflate(R.layout.device_item, parent, false);

        Device singleListItem = getItem(position);
        String deviceName = singleListItem.getName();
        int deviceBearing = singleListItem.getBearing();

        TextView txtDeviceName = customRow.findViewById(R.id.txt_name);
        txtDeviceName.setText(deviceName + "\n" + deviceBearing);

        //TextView txtDeviceBearing = customRow.findViewById(R.id.txt_bearing_val);
        //txtDeviceBearing.setText("" + deviceBearing);

        //ImageView imgView = customRow.findViewById(R.id.img_device);
        //imgView.setImageResource(R.drawable.shitty_device_icon);

        return customRow;
    }
}
