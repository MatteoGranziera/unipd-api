package com.tsac.matteo.tsaccalendarwidget;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.format.Time;
import android.util.Log;
import android.widget.ListView;
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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Matteo on 23/02/2015.
 */
public class CalendarRepo extends AsyncTask<String, Void, CalendarElement[]> {
    String url = "http://tomokitest.netii.net/API/unipd/calendar.php?action=gettsacday";
    ListView lst;
    // JSON tag names
    private static final String TAG_ROOM = "room";
    private static final String TAG_STARTHOUR = "starth";
    private static final String TAG_STARTMIN = "startm";
    private static final String TAG_ENDHOUR = "endh";
    private static final String TAG_ENDMIN = "endm";
    private static final String TAG_DESCRIPTION = "descrizione";
    private static final String TAG_PROF = "docente";

    // contacts JSONArray
    JSONArray arrays = null;

    BufferedReader in = null;
    String data = null;

    TextView txtView;

    @Override
    protected void onPreExecute() {
        // Set the variable txtView here, after setContentView on the dialog
        // has been called! use dialog.findViewById().

    }

    public String SendRequest(int day, int month, int year){
        CalendarElement elem = null;

        BufferedReader in = null;
        String data = null;

        try
        {
            HttpClient client = new DefaultHttpClient();
            URI website = new URI(url);
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

    protected CalendarElement[] doInBackground(String... urls) {
        Time now = new Time();
        now.setToNow();
        CalendarElement el = new CalendarElement();
        el.day = now;
        return ParseData(SendRequest(now.monthDay, now.month, now.year), el);
    }

    /** The system calls this to perform work in the UI thread and delivers
     * the result from doInBackground() */
    protected void onPostExecute(String result) {
        lst = (ListView) findViewById(R.id.listView);
    }

    private CalendarElement[] ParseData(String data, CalendarElement def){
        CalendarElement[] array = null;

        if (data != null) {
            try {
                JSONArray ar = new JSONArray(data);
                array = new CalendarElement[ar.length()];
                for(int i = 0; i< ar.length(); i++) {
                    CalendarElement el = new CalendarElement();
                    el.day = def.day;

                    JSONObject c = ar.getJSONObject(i);
                    el.room = c.getString(TAG_ROOM);

                    Time ti = new Time();
                    ti.hour = c.getInt(TAG_STARTHOUR);
                    ti.minute = c.getInt(TAG_STARTMIN);
                    el.start = ti;

                    Time te = new Time();
                    te.hour = c.getInt(TAG_ENDHOUR);
                    te.minute = c.getInt(TAG_ENDMIN);
                    el.end = te;

                    el.description = c.getString(TAG_DESCRIPTION);
                    el.prof = c.getString(TAG_PROF);

                    array[i] = el;
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
