package sam.pillpal;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;

import static android.app.PendingIntent.getActivity;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent notificationIntent = new Intent(context, CalendarActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bundle b = intent.getExtras();
        String title = "";
        String text = "";
        if(b != null)
            title = b.getString("title");
            text = b.getString("text");

        CalendarActivity.showNotification(title, text, context, (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
    }
}