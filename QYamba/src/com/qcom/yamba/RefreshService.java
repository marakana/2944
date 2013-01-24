package com.qcom.yamba;

import java.util.List;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class RefreshService extends IntentService {
	private static final String TAG = "RefreshService";
	private static final int MAX_POSTS = 20;

	public RefreshService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
	}

	@Override
	public void onHandleIntent(Intent intent) {
		Log.d(TAG, "onStart");
		boolean hasNewStatuses = false;

		try {
			YambaClient client = new YambaClient("student", "password");
			List<Status> timeline = client.getTimeline(MAX_POSTS);
			ContentValues values = new ContentValues();
			for (Status status : timeline) {
				values.put(StatusContract.Columns._ID, status.getId());
				values.put(StatusContract.Columns.USER, status.getUser());
				values.put(StatusContract.Columns.TEXT, status.getMessage());
				values.put(StatusContract.Columns.CREATED_AT, status
						.getCreatedAt().getTime());
				if (getContentResolver().insert(StatusContract.CONTENT_URI,
						values) != null) {
					hasNewStatuses = true;
				}

				Log.d(TAG,
						String.format("%s: %s", status.getUser(),
								status.getMessage()));
			}
		} catch (YambaClientException e) {
			Log.e(TAG, "Failed to fetch timeline", e);
			e.printStackTrace();
		}
		
		if(hasNewStatuses) {
			sendBroadcast( new Intent("com.qcom.yamba.action.NEW_STATUS") );
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

}
