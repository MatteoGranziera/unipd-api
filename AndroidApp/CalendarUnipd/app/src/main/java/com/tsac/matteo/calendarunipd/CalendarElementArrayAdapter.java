package com.tsac.matteo.calendarunipd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matteo on 25/02/2015.
 */
public class CalendarElementArrayAdapter extends ArrayAdapter<CalendarElement> {

    public CalendarElementArrayAdapter(Context context, ArrayList<CalendarElement> resource) {
        super(context, 0, resource);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CalendarElement elem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
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
        tvHour.setText(elem.getStart().format("hh:mm") + " - " + elem.getEnd().format("hh:mm"));
        // Return the completed view to render on screen
        return convertView;
    }
}
