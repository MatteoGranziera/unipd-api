package com.tsac.matteo.calendarunipd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Matteo on 23/02/2015.
 */
public class CalendarRepo extends AsyncTask<String, Void, Boolean> {
    Context context;
    private static final String URL = "http://tomokitest.netii.net/API/unipd/calendar.php?action=gettsacday";

    // JSON tag names
    private static final String TAG_ROOM = "room";
    private static final String TAG_STARTHOUR = "starth";
    private static final String TAG_STARTMIN = "startm";
    private static final String TAG_ENDHOUR = "endh";
    private static final String TAG_ENDMIN = "endm";
    private static final String TAG_DESCRIPTION = "descrizione";
    private static final String TAG_PROF = "docente";

    //String rooms
    private static final String ROOM_S = "S";
    private static final String ROOM_B = "B";

    // contacts JSONArray
    JSONArray arrays = null;

    BufferedReader in = null;
    String data = null;
    String room = null;
    ArrayList<CalendarElement> listItem = null;

    ListView lstView = null;
    ProgressBar prbUpdate = null;
    ProgressBar prbProgress = null;

    int nextDays = 0;


    @Override
    protected void onPreExecute() {
        // Set the variable txtView here, after setContentView on the dialog
        // has been called! use dialog.findViewById().
        super.onPreExecute();
        prbUpdate.setVisibility(View.VISIBLE);
        prbProgress.setMax((nextDays + 1) * 2);
        prbProgress.setProgress(0);

    }

    public String SendRequest(int day, int month, int year, String room){
        CalendarElement elem = null;

        BufferedReader in = null;
        String data = null;

        String composed_url = URL;

        composed_url = composed_url.concat("&r=" + room);
        composed_url = composed_url.concat("&d=" + day);
        composed_url = composed_url.concat("&m=" + month);
        composed_url = composed_url.concat("&y=" + year);

        try
        {
            HttpClient client = new DefaultHttpClient();
            URI website = new URI(composed_url);
            HttpGet request = new HttpGet();
            request.setURI(website);
            HttpResponse response = client.execute(request);
            response.getStatusLine().getStatusCode();

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String l = "";
            String nl = System.getProperty("line.separator");
            while ((l = in.readLine()) !=null){
                sb.append(l + nl);
            }
            in.close();
            data = sb.toString();
            return data;
        }
        catch(Exception e){
            return e.getMessage();
        }
        finally{
            if (in != null){
                try{
                    in.close();
                    return data;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    protected Boolean doInBackground(String... urls) {

        Time now = new Time();
        now.setToNow();
        CalendarElement el = new CalendarElement();
        el.setDay(now);
        now.month++;

        listItem = new ArrayList<CalendarElement>();

        ArrayList<CalendarElement> list1, list2;

        list1 = ParseData(SendRequest(now.monthDay, now.month, now.year, ROOM_B), el);
        prbProgress.setProgress(1);
        list2 = ParseData(SendRequest(now.monthDay, now.month, now.year, ROOM_S), el);
        prbProgress.setProgress(2);

        if(!list1.isEmpty() || !list2.isEmpty()) {
            el.separator = true;
            listItem.add(el);
            listItem.addAll(list1);
            listItem.addAll(list2);
        }


        for(int i = 0; i < nextDays; i++){
            now = new Time();
            now.setToNow();
            now.month++;
            now.monthDay += i + 1;
            now.normalize(false);
            el = new CalendarElement();
            el.setDay(now);

            list1 = ParseData(SendRequest(now.monthDay, now.month, now.year, ROOM_B), el);
            prbProgress.setProgress(prbProgress.getProgress() + 1);
            list2 = ParseData(SendRequest(now.monthDay, now.month, now.year, ROOM_S), el);
            prbProgress.setProgress(prbProgress.getProgress() + 1);

            if(!list1.isEmpty() || !list2.isEmpty()) {
                el.separator = true;
                listItem.add(el);
                listItem.addAll(list1);
                listItem.addAll(list2);
            }

        }

        return true;
    }

    /** The system calls this to perform work in the UI thread and delivers
     * the result from doInBackground() */

    @Override
    protected void onPostExecute(Boolean result) {
        CalendarElementArrayAdapter adapter = new CalendarElementArrayAdapter(context, listItem);

        lstView.setAdapter(adapter);
        prbUpdate.setVisibility(View.INVISIBLE);
    }

    private ArrayList<CalendarElement> ParseData(String data, CalendarElement def){
        ArrayList<CalendarElement> array = null;

        if (data != null) {
            try {
                JSONArray ar = new JSONArray(data);
                array = new ArrayList<CalendarElement>();
                for(int i = 0; i< ar.length(); i++) {
                    CalendarElement el = new CalendarElement();
                    el.setDay(def.getDay());

                    JSONObject c = ar.getJSONObject(i);

                    el.setDescription(c.getString(TAG_DESCRIPTION));

                    el.setRoom(c.getString(TAG_ROOM));

                    Time ti = new Time();
                    ti.hour = c.getInt(TAG_STARTHOUR);
                    ti.minute = c.getInt(TAG_STARTMIN);
                    el.setStart(ti);

                    Time te = new Time();
                    te.hour = c.getInt(TAG_ENDHOUR);
                    te.minute = c.getInt(TAG_ENDMIN);
                    el.setEnd(te);

                    el.setProf(c.getString(TAG_PROF));

                    array.add(el);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        return array;
    };
}