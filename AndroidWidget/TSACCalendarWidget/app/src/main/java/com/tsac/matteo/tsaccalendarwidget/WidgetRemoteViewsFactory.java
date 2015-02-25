package com.tsac.matteo.tsaccalendarwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matteo on 24/02/2015.
 */
public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context = null;
    private int appWidgetId;

    private ArrayList<CalendarElement> widgetList = new ArrayList<CalendarElement>();

    public WidgetRemoteViewsFactory(Context applicationContext, Intent intent) {

        this.context = applicationContext;
        widgetList = intent.getParcelableArrayListExtra("ListItem");

    }

    private void updateWidgetListView()
    {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        widgetList.clear();
    }

    @Override
    public int getCount() {
        return widgetList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(context.getPackageName(),
                R.layout.listviewitem);

        row.setTextViewText(R.id.txtLezione, widgetList.get(position).getDescription());
        row.setTextViewText(R.id.txtStanza, widgetList.get(position).getRoom());
        row.setTextViewText(R.id.txtDocente, widgetList.get(position).getProf());
        row.setTextViewText(R.id.txtOrario, widgetList.get(position).getStart().format("hh:mm") + " - " + widgetList.get(position).getEnd().format("hh:mm") );

        return (row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
