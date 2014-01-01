package com.example.earthquakereader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final int VERSION = 2;
	private static final String name = "earthquake.db";
	
	public DBHelper(Context context) {
		super(context, name, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE info ( _id INTEGER AUTO INCREMENT PRIMARY KEY, " +
				" title TEXT," +
				" location TEXT," +
				" latitude TEXT," +
				" longitude TEXT," +
				" magnitude TEXT," +
				" depth TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion == 1) {
			//
		}
		
	}

}
