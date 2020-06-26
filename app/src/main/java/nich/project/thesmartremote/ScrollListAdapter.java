package nich.project.thesmartremote;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class ScrollListAdapter extends ArrayAdapter<String> {

    boolean m_isOnline;

    public ScrollListAdapter(@NonNull Context context, ArrayList<String> sensorNames) {
        super(context, R.layout.sensor_item, sensorNames);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View sensorItemView = inflater.inflate(R.layout.sensor_item, parent,false);

        String sensorStringItem = getItem(position);
        TextView sensorNameTextView = sensorItemView.findViewById(R.id.txt_sensor_name);

        TextView txtIsonline = sensorItemView.findViewById(R.id.txt_is_online);

        String tempString = "Conected";
        SpannableString spanConnected = new SpannableString(tempString);
        spanConnected.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanConnected.length(), 0);

        String tempStringDisconnected = "Disconected";
        SpannableString spanDisconnected = new SpannableString(tempStringDisconnected);
        spanDisconnected.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanDisconnected.length(), 0);

        if(m_isOnline == true){
            txtIsonline.setText(spanConnected);
            txtIsonline.setTextColor(GREEN);
        }
        else {
            txtIsonline.setText(spanDisconnected);
            txtIsonline.setTextColor(RED);
        }

        sensorNameTextView.setText(sensorStringItem);

        return sensorItemView;

    }
}
