package com.qcom.yamba;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LoaderCallbacks<Cursor> {
	private static final String[] FROM = { StatusContract.Columns.USER,
			StatusContract.Columns.TEXT };
	private static final int[] TO = { android.R.id.text1, android.R.id.text2 };
	private ListView timeline;
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, null, FROM, TO,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		timeline = (ListView) findViewById(R.id.timeline);
		timeline.setAdapter(adapter);

		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	// --- Loader Manager Stuff ---
	private static final int LOADER_ID = 47;
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (id != LOADER_ID)
			return null;
		return new CursorLoader(this, StatusContract.CONTENT_URI, null, null,
				null, StatusContract.SORT_ORDER);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
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
