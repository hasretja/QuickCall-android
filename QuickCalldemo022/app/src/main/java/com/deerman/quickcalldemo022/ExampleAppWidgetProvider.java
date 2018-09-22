package com.deerman.quickcalldemo022;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import static com.deerman.quickcalldemo022.ExampleAppWidgetConfig.KEY_BUTTON_TEXT;
import static com.deerman.quickcalldemo022.ExampleAppWidgetConfig.SHARED_PRES;

public class ExampleAppWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds)
        {
            Intent ıntent=new Intent(Intent.ACTION_DIAL);
            ıntent.setData(Uri.parse("tel:"+KEY_BUTTON_TEXT));
            PendingIntent pendingIntent=PendingIntent.getActivities(context,0, new Intent[]{ıntent},0);

            SharedPreferences prefs=context.getSharedPreferences(SHARED_PRES,Context.MODE_PRIVATE);
            String buttonText=prefs.getString(KEY_BUTTON_TEXT+appWidgetId,  "Press Me");

            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.example_widget);
            views.setOnClickPendingIntent(R.id.example_widget_button,pendingIntent);
            views.setCharSequence(R.id.example_widget_button,"setText",buttonText);
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }
}