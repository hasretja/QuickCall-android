package com.deerman.quickcalldemo022;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;

public class ExampleAppWidgetConfig extends AppCompatActivity {
    public static final String SHARED_PRES ="prefs";
    public static final String KEY_BUTTON_TEXT="keybuttontext";

    private int appWidgetId= AppWidgetManager.INVALID_APPWIDGET_ID;
    private EditText editTextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_app_widget_config);
        Intent configIntent =getIntent();
        Bundle extras=configIntent.getExtras();
        if (extras!=null)
        {
            appWidgetId=extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        Intent resultValue=new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        setResult(RESULT_CANCELED,resultValue);

        if (appWidgetId==AppWidgetManager.INVALID_APPWIDGET_ID)
        {
            finish();
        }
        editTextButton=findViewById(R.id.edit_text_button);
    }

    public void confirmConfiguration(View v)
    {
        AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(this);

        Intent ıntent=new Intent(Intent.ACTION_DIAL);
        ıntent.setData(Uri.parse("tel:"+editTextButton.getText().toString()));
        PendingIntent pendingIntent=PendingIntent.getActivities(this,0, new Intent[]{ıntent},0);
        String buttonText=editTextButton.getText().toString();
        RemoteViews view = new RemoteViews(this.getPackageName(), R.layout.example_widget);
        view.setOnClickPendingIntent(R.id.example_widget_button,pendingIntent);
        view.setCharSequence(R.id.example_widget_button,"setText",buttonText);

        appWidgetManager.updateAppWidget(appWidgetId,view);

        SharedPreferences prefs=getSharedPreferences(SHARED_PRES,MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(KEY_BUTTON_TEXT+appWidgetId,buttonText);
        editor.apply();

        Intent resultValue=new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        setResult(RESULT_OK,resultValue);
        finish();
}
}
