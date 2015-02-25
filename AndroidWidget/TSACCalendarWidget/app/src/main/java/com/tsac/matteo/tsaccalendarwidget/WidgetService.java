package com.tsac.matteo.tsaccalendarwidget;

/**
 * Created by Matteo on 24/02/2015.
 */
import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Matteo on 24/02/2015.
 */
public class WidgetService extends RemoteViewsService
{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        return (new WidgetRemoteViewsFactory(this.getApplicationContext(), intent));
    }

}