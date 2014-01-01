package com.example.earthquakereader;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MainActivity extends Activity {
	
	private EarthquakeListFragment earthquakeListFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		earthquakeListFragment = new EarthquakeListFragment();
		getFragmentManager().beginTransaction().replace(android.R.id.content, earthquakeListFragment).commit();
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			boolean success = bundle.getBoolean("success", false);
			Log.i("BroadcastReceiver.onReceive()", "success: "+success);
			
			if (success) {
				earthquakeListFragment.refresh();
			}
		}
	};
	
	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, new IntentFilter("Earthquake"));
	};
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}
}
