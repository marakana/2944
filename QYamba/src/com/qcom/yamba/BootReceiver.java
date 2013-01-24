package com.qcom.yamba;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	private static final int INTERVAL = 180000; // millis

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("BootReceiver", "onReceive");

		PendingIntent operation = PendingIntent.getService(context, 0,
				new Intent("com.qcom.yamba.action.REFRESH"), PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC,
				System.currentTimeMillis(), INTERVAL, operation);
	}

}
