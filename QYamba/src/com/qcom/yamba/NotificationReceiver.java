package com.qcom.yamba;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {

	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("NotificationReceiver", "onReceive");

		PendingIntent operation = PendingIntent.getActivity(context, 0,
				new Intent(context, MainActivity.class),
				PendingIntent.FLAG_UPDATE_CURRENT);

		Notification notification = new Notification.Builder(context)
				.setContentIntent(operation).setContentTitle("New Tweet!")
				.setContentText("You got new tweets")
				.setSmallIcon(android.R.drawable.ic_dialog_email).build();
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(47, notification);
	}
}
