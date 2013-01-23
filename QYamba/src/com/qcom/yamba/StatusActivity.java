package com.qcom.yamba;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;

public class StatusActivity extends Activity {
	private int defaultTextColor;
	private EditText textStatus;
	private TextView textCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);

		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		textStatus = (EditText) findViewById(R.id.text_status);
		textCount = (TextView) findViewById(R.id.text_count);
		defaultTextColor = textCount.getTextColors().getDefaultColor();

		textStatus.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				int count = 140 - s.length();
				textCount.setText(Integer.toString(count));

				if (count < 50) {
					textCount.setTextColor(Color.RED);
				} else {
					textCount.setTextColor(defaultTextColor);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

		});
	}

	public void onButtonUpdate(View view) {
		final String status = textStatus.getText().toString();

		new UpdateStatusTask().execute(status);

		Log.d("Yamba", "button clicked: " + status);
	}

	class UpdateStatusTask extends AsyncTask<String, Void, String> {
		ProgressDialog dialog;

		// Runs on UI thread, before background job gets started
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(StatusActivity.this, "Posting",
					"Please wait...");
		}

		// Executes on a worker thread
		@Override
		protected String doInBackground(String... params) {
			String ret;
			try {
				YambaClient client = new YambaClient("student", "password");
				client.updateStatus(params[0]);
				ret = "Successfully posted";
			} catch (Exception e) {
				Log.e("Yamba", "Failed to post", e);
				ret = "Failed";
			}
			return ret;
		}

		// Runs on UI thread, when done.
		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG)
					.show();
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		// Debug.startMethodTracing("YambaStatusAcitivity.trace");
	}

	@Override
	protected void onStop() {
		super.onStop();
		// Debug.stopMethodTracing();
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent goHomeIntent = new Intent(this, MainActivity.class);
			goHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(
					Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(goHomeIntent);
			return true;
		default:
			return false;
		}
	}

}
