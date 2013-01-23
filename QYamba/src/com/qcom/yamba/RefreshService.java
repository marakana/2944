package com.qcom.yamba;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class RefreshService extends IntentService {
	private static final String TAG = "RefreshService";
	private static final int MAX_POSTS = 20;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
	}

	@Override
	public void onHandleIntent(Intent intent) {
		Log.d(TAG, "onStart");

		try {
			YambaClient client = new YambaClient("student", "password");
			List<Status> timeline = client.getTimeline(MAX_POSTS);
			for (Status status : timeline) {
				Log.d(TAG,
						String.format("%s: %s", status.getUser(),
								status.getMessage()));
			}
		} catch (YambaClientException e) {
			Log.e(TAG, "Failed to fetch timeline", e);
			e.printStackTrace();
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

}
