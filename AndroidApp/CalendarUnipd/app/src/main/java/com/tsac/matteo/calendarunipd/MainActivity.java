package com.tsac.matteo.calendarunipd;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    ListView lstView = null;
    ProgressBar prbUpdate = null;
    EditText days = null;
    Toast tstError = null;
    ProgressBar prbProgress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstView = (ListView) findViewById(R.id.lstLessons);
        prbUpdate = (ProgressBar) findViewById(R.id.prbUpdate);
        prbUpdate.setVisibility(View.INVISIBLE);
        prbProgress = (ProgressBar) findViewById(R.id.prbProgress);
        prbProgress.setProgress(0);
        days = (EditText) findViewById(R.id.txtDays);
        tstError = new Toast(getApplicationContext());

        Button btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int daysValue = 0;
                boolean ok = false;
                try {
                    daysValue = Integer.parseInt(days.getText().toString());
                    ok = true;
                }catch(Exception e){
                    tstError.setText("Il numero di giorni inserito non è valido!");
                    days.setText("0");
                    tstError.show();
                }
                 if(ok) {
                     if(daysValue < 0 || daysValue > 30){
                         tstError.setText("Il nomero deve essere compreso tra 0 e 30, è stato reimpostato il valore di default");
                         days.setText("0");
                         tstError.show();
                         daysValue = 0;
                     }
                     CalendarRepo repo = new CalendarRepo();
                     repo.context = getApplicationContext();
                     repo.lstView = lstView;
                     repo.prbUpdate = prbUpdate;
                     repo.prbProgress = prbProgress;
                     repo.nextDays = daysValue;

                     repo.execute();
                 }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
