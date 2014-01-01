package com.example.earthquakereader.service;

import java.util.ArrayList;
import java.util.List;

import com.example.earthquakereader.db.EarthquakeContentProvider;
import com.example.earthquakereader.entity.Info;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class EarthquakeService extends IntentService {
	
	private static final String SERVER_URL = "http://earthquake-report.com/feeds/recent-eq?json";

	public EarthquakeService() {
		super("Name Intent");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		new LoadDataAsyncTask().execute();
	}

	private class LoadDataAsyncTask extends AsyncTask<Void, Void, List<Info>> {

		@Override
		protected List<Info> doInBackground(Void... params) {
			String result = HttpRequest.get(SERVER_URL).body();
			Log.i("EarthquakeService.doInBackground()", result);
			
			Gson gson = new Gson();
			Info[] infos = gson.fromJson(result, Info[].class);
			List<Info> infoList = new ArrayList<Info>();
			
			for (int i = 0; i < infos.length; i++) {
				infoList.add(infos[i]);
			}
			
			return infoList;
		}
		
		@Override
		protected void onPostExecute(List<Info> result) {
			boolean success = false;
			
			if (result != null) {
				ContentValues[] contentValues = new ContentValues[result.size()];
				int i = 0;
				
				for (Info info : result) {
					contentValues[i] = EarthquakeContentProvider.generateInfo(info);
					i++;
				}
				
				getContentResolver().bulkInsert(EarthquakeContentProvider.INFO_URI, contentValues);
				success = true;
			}
			
			Log.i("EarthquakeService.onPostExecute()", "success: "+success);
			sendBroadcast(new Intent("Earthquake").putExtra("success", success));
		}
	}
}
