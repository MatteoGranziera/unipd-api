package com.tsac.matteo.tsaccalendarwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ListView;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link TsacCalendarConfigureActivity TsacCalendarConfigureActivity}
 */
public class TsacCalendar extends AppWidgetProvider {

    ArrayList<CalendarElement> list = new ArrayList<CalendarElement>();
    private static final String UPDATE_CLICKED    = "UpdateButtonClick";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.tsac_calendar);

        rv.setOnClickPendingIntent(R.id.btnUpdate, getPendingSelfIntent(context, UPDATE_CLICKED));

        /*Intent intent = new Intent(context, WidgetService.class);
        intent.putParcelableArrayListExtra("ListItem", list);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        rv.setRemoteAdapter(R.id.listView, intent);*/
        for(int i = 0; i < N; i++){
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }

    }

    @Override
    public  void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            TsacCalendarConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = TsacCalendarConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.tsac_calendar);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        if (UPDATE_CLICKED.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.tsac_calendar);
            watchWidget = new ComponentName(context, TsacCalendar.class);

            CalendarRepo repo = new CalendarRepo();
            repo.context = context;
            repo.listItem = list;
            repo.execute();
            //remoteViews.setTextViewText(R.id, "TESTING");

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

}


