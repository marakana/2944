package com.qcom.yamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// --- Action Bar Stuff ---

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_status_update:
			startActivity(new Intent(this, StatusActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			return true;
		case R.id.item_refresh:
			startService(new Intent("com.qcom.yamba.action.REFRESH"));
			return true;
		case R.id.item_purge:
			int records = getContentResolver().delete(
					StatusContract.CONTENT_URI, "1", null);
			Toast.makeText(this, "Deleted records: " + records,
					Toast.LENGTH_LONG).show();
			return true;
		default:
			return false;
		}
	}
}
