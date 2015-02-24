package com.tsac.matteo.tsaccalendarwidget;

/**
 * Created by Matteo on 23/02/2015.
 */
import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Matteo on 23/02/2015.
 */
public class CalendarElement {
    Time day;
    String description;
    String prof;
    Time start;
    Time end;
    String room;

    public CalendarElement(){

    }

    public CalendarElement(String description, String prof, String room, Time start, Time end, Time day){
        this.description = description;
        this.prof = prof;
        this.room = room;
        this.start = start;
        this.end = end;
        this.day = day;
    }

    public CalendarElement(String description, String prof, String room, int startm, int starth, int endm, int endh){
        SimpleDateFormat dtf = new SimpleDateFormat("K:m");
        this.description = description;
        this.prof = prof;
        this.room = room;

        Time t = new Time();
        t.setToNow();
        t.set(0, startm, starth, t.MONTH_DAY, t.MONTH, t.YEAR);
        this.start = t;

        t = new Time();
        t.setToNow();
        t.set(0, endm, endh, t.MONTH_DAY, t.MONTH, t.YEAR);
        this.end = t;
    }
}

