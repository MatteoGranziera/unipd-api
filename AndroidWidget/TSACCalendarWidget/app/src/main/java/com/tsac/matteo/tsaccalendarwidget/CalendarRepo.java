package com.tsac.matteo.tsaccalendarwidget;

import android.os.AsyncTask;
import android.text.format.Time;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Matteo on 23/02/2015.
 */
public class CalendarRepo extends AsyncTask<String, Void, String> {
    String url = "http://tomokitest.netii.net/API/unipd/test.php";

    BufferedReader in = null;
    String data = null;
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

    protected String doInBackground(String... urls) {
        Time now = new Time();
        now.setToNow();
        return SendRequest(now.monthDay, now.month, now.year);
    }

    /** The system calls this to perform work in the UI thread and delivers
     * the result from doInBackground() */
    protected void onPostExecute(String result) {

    }

   /* private CalendarElement ParseData(String data){
        CalendarElement el;
    };*/
}
