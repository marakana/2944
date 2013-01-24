package com.qcom.yamba;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;

public class StatusFragment extends Fragment {
	private int defaultTextColor;
	private EditText textStatus;
	private TextView textCount;
	private Button updateButton;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().setContentView(R.layout.fragment_status);

		textStatus = (EditText) getActivity().findViewById(R.id.text_status);
		textCount = (TextView) getActivity().findViewById(R.id.text_count);
		defaultTextColor = textCount.getTextColors().getDefaultColor();
		
		updateButton = (Button)getActivity().findViewById(R.id.button_update);
		updateButton.setOnClickListener( new OnClickListener() {
			public void onClick(View view) {
				final String status = textStatus.getText().toString();

				new UpdateStatusTask().execute(status);

				Log.d("Yamba", "button clicked: " + status);
			}

		});

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

	class UpdateStatusTask extends AsyncTask<String, Void, String> {
		ProgressDialog dialog;

		// Runs on UI thread, before background job gets started
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), "Posting",
					"Please wait...");
		}

		// Executes on a worker thread
		@Override
		protected String doInBackground(String... params) {
			String ret;
			try {
				YambaClient client = new YambaClient("student", "password");
				client.postStatus(params[0]);
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
			Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
		}

	}
}
