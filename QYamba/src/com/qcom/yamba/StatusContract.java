package com.qcom.yamba;

import android.net.Uri;
import android.provider.BaseColumns;

public class StatusContract {
	private StatusContract() {
	}

	public static final String AUTHORITY = "com.qcom.yamba.provider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/status");

	public static final String DB_NAME = "timeline.db";
	public static final int DB_VERSION = 2;
	public static final String TABLE = "timeline";
	public static final String SORT_ORDER = Columns.CREATED_AT + " desc";

	public static final class Columns implements BaseColumns {
		private Columns() {
		};

		public static final String CREATED_AT = "yamba_created_at";
		public static final String USER = "yamba_user";
		public static final String TEXT = "yamba_text";
	}

}
