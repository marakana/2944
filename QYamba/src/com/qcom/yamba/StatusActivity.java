package com.qcom.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StatusActivity extends Activity {
	private Button buttonUpdate;
	private EditText textStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);

		buttonUpdate = (Button) findViewById(R.id.button_update);
		textStatus = (EditText) findViewById(R.id.text_status);

		buttonUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String status = textStatus.getText().toString(); 
				Log.d("Yamba", "button clicked: " + status);
			}

		});
	}

}
