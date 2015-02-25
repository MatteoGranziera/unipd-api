package com.tsac.matteo.tsaccalendarwidget;

/**
 * Created by Matteo on 23/02/2015.
 */
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.text.format.Time;

import java.text.SimpleDateFormat;

/**
 * Created by Matteo on 23/02/2015.
 */
public class CalendarElement implements Parcelable{
    private Time day;
    private String description;
    private String prof;
    private Time start;
    private Time end;
    private String room;



    public CalendarElement(){

    }

    public CalendarElement(Parcel in){
        this.day = new Time();
        this.day.set(in.readLong());
        this.description = in.readString();
        this.prof = in.readString();
        this.room = in.readString();
        this.start = new Time();
        this.start.set(in.readLong());
        this.end = new Time();
        this.end.set(in.readLong());
    }

    public CalendarElement(String description, String prof, String room, Time start, Time end, Time day){
        this.setDescription(description);
        this.setProf(prof);
        this.setRoom(room);
        this.setStart(start);
        this.setEnd(end);
        this.setDay(day);
    }

    public CalendarElement(String description, String prof, String room, int startm, int starth, int endm, int endh){
        SimpleDateFormat dtf = new SimpleDateFormat("K:m");
        this.setDescription(description);
        this.setProf(prof);
        this.setRoom(room);

        Time t = new Time();
        t.setToNow();
        t.set(0, startm, starth, t.MONTH_DAY, t.MONTH, t.YEAR);
        this.setStart(t);

        t = new Time();
        t.setToNow();
        t.set(0, endm, endh, t.MONTH_DAY, t.MONTH, t.YEAR);
        this.setEnd(t);
    }


    public Time getDay() {
        return day;
    }

    public void setDay(Time day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(day.toMillis(true));
        dest.writeString(description);
        dest.writeString(prof);
        dest.writeString(room);
        dest.writeLong(start.toMillis(true));
        dest.writeLong(end.toMillis(true));
    }

    static final Parcelable.Creator<CalendarElement> CREATOR
            = new Parcelable.Creator<CalendarElement>() {

        public CalendarElement createFromParcel(Parcel in) {
            return new CalendarElement(in);
        }

        public CalendarElement[] newArray(int size) {
            return new CalendarElement[size];
        }
    };
}

