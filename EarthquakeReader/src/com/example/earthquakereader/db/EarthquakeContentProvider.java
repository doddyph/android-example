package com.example.earthquakereader.db;

import com.example.earthquakereader.entity.Info;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class EarthquakeContentProvider extends ContentProvider {

	private static final String AUTHORITY = "com.example.earthquakereader";

	public static final String INFO_TABLE = "info";

	private static final int INFO_LIST = 1;
	private static final int INFO_DETAIL = 2;

	private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		// content://com.example.erathquakereader/info
		uriMatcher.addURI(AUTHORITY, INFO_TABLE, INFO_LIST);

		// content://com.example.erathquakereader/info/1
		uriMatcher.addURI(AUTHORITY, INFO_TABLE + "/#", INFO_DETAIL);
	}

	DBHelper dbHelper;

	public static Uri INFO_URI = Uri.parse("content://" + AUTHORITY + "/" + INFO_TABLE);

	@Override
	public boolean onCreate() {
		dbHelper = new DBHelper(getContext());
		return true;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case INFO_LIST:
			db.insert(INFO_TABLE, null, values);

			break;

		default:
			break;
		}
		return null;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case INFO_LIST:
			db.beginTransaction();
			for (int i = 0; i < values.length; i++) {
				db.insert(INFO_TABLE, null, values[i]);
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			return values.length;

		default:
			break;
		}
		return 0;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
		String param = null;
		String[] args = null;
		switch (uriMatcher.match(uri)) {
		case INFO_LIST:
			sqLiteQueryBuilder.setTables(INFO_TABLE);
			break;
		case INFO_DETAIL:
			param = "id = ?";
			String id = uri.getLastPathSegment();
			args = new String[]{ id };
			sqLiteQueryBuilder.setTables(INFO_TABLE);
			break;

		default:
			break;
		}
		return sqLiteQueryBuilder.query(db, null, param,
				args, null, null, null);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static ContentValues generateInfo(Info info) {
		ContentValues values = new ContentValues();
		values.put("title", info.getTitle());
		values.put("location", info.getLocation());
		values.put("latitude", info.getLatitude());
		values.put("longitude", info.getLongitude());
		values.put("magnitude", info.getMagnitude());
		values.put("depth", info.getDepth());
		return values;
	}

}
