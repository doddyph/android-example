package com.example.earthquakereader;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class DetailsActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			String location = bundle.getString("location", "");
			String latitude = bundle.getString("latitude", "0");
			String longitude = bundle.getString("longitude", "0");
			String magnitude = bundle.getString("magnitude", "0");
			String depth = bundle.getString("depth", "0");
			Log.v(getClass().getName(), "Location: "+location+" ["+latitude+", "+longitude+" "+
					magnitude+" SR - "+depth+" km");
			
			LatLng position = new LatLng(
					Double.parseDouble(latitude), 
					Double.parseDouble(longitude));
			
			GoogleMap map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			
			if (map != null) {
				map.addMarker(new MarkerOptions()
					.position(position)
					.title(location)
					.snippet("Magnitude: "+magnitude+" Depth: "+depth+" km"));
				
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 5));
			}
		}
		
	}
}
