package com.tsac.matteo.calendarunipd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Matteo on 25/02/2015.
 */
public class CalendarElementArrayAdapter extends ArrayAdapter<CalendarElement> {

    public boolean separator = false;
    public CalendarElementArrayAdapter(Context context, ArrayList<CalendarElement> resource) {
        super(context, 0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CalendarElement elem = getItem(position);
        convertView = null;

        // Check if an existing view is being reused, otherwise inflate the view
        if(elem.separator == false) {
            if (convertView == null) {

                convertView = LayoutInflater.from(getContext()).inflate(R.layout.lessonlistitem, parent, false);
            }

            // Lookup view for data population
            TextView tvLesson = (TextView) convertView.findViewById(R.id.txtLezione);
            TextView tvProf = (TextView) convertView.findViewById(R.id.txtDocente);
            TextView tvRoom = (TextView) convertView.findViewById(R.id.txtStanza);
            TextView tvHour = (TextView) convertView.findViewById(R.id.txtOrario);
            // Populate the data into the template view using the data object
            tvLesson.setText(elem.getDescription());
            tvProf.setText(elem.getProf());
            tvRoom.setText(elem.getRoom());
            tvHour.setText(elem.getStart().hour + ":" + elem.getStart().minute +
                    " - " + elem.getEnd().hour + ":" + elem.getEnd().minute);
            // Return the completed view to render on screen
            return convertView;
        }
        else {
            View separartorView = LayoutInflater.from(getContext()).inflate(R.layout.separatordateitem, parent, false);
            TextView tvDay = (TextView) separartorView.findViewById(R.id.txtDay);
            tvDay.setText(elem.getDay().monthDay + "/" + elem.getDay().month + "/" + elem.getDay().year);

            return  separartorView;
        }
    }
}
