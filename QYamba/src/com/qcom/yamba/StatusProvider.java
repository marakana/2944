package com.qcom.yamba;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class StatusProvider extends ContentProvider {
	private DbHelper dbHelper;

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return (dbHelper != null) ? true : false;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id = db.insertWithOnConflict(StatusContract.TABLE, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);

		if (id > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
			return ContentUris.withAppendedId(uri, id);
		} else {
			return null;
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		getContext().getContentResolver().notifyChange(uri, null);
		return db
				.update(StatusContract.TABLE, values, selection, selectionArgs);
	}

	// SQL: delete from status where _id = 47 or user = 'bob'
	// selection: where _id = ? or user = ?
	// selectionArgs: { 47, 'bob' }
	// Hint: use db.delete();
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 getContext().getContentResolver().notifyChange(uri, null);
		 return db.delete(StatusContract.TABLE, selection, selectionArgs);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteDatabase db = dbHelper.getReadableDatabase();

		Cursor cursor = db.query(StatusContract.TABLE, projection, selection,
				selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

}
