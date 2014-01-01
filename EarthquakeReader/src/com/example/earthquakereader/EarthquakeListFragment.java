package com.example.earthquakereader;

import java.util.ArrayList;
import java.util.List;

import com.example.earthquakereader.adapter.InfoAdapter;
import com.example.earthquakereader.db.EarthquakeContentProvider;
import com.example.earthquakereader.entity.Info;
import com.example.earthquakereader.service.EarthquakeService;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class EarthquakeListFragment extends Fragment {
	
	private ListView listView;
	private InfoAdapter infoAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		setHasOptionsMenu(true);
		
		View view = inflater.inflate(R.layout.activity_main, null, false);
		listView = (ListView) view.findViewById(R.id.list_info);
		
		infoAdapter = new InfoAdapter(getActivity());
		infoAdapter.setInfoList(new ArrayList<Info>());
		listView.setAdapter(infoAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				
				Info info = (Info) listView.getItemAtPosition(position);
//				Toast.makeText(getActivity(), "Selected: "+info, Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(getActivity(), DetailsActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				Bundle extras = new Bundle();
				extras.putString("location", info.getLocation());
				extras.putString("latitude", info.getLatitude());
				extras.putString("longitude", info.getLongitude());
				extras.putString("magnitude", info.getMagnitude());
				extras.putString("depth", info.getDepth());
				intent.putExtras(extras);
				
				startActivity(intent);
			}
		});
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		refresh();
	}
	
	public void refresh() {
		new LoadInfoAsyncTask().execute();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.activity_main, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			Intent i = new Intent(getActivity(), EarthquakeService.class);
			getActivity().startService(i);
			break;

		default:
			break;
		}
		
		return true;
	}

	private class LoadInfoAsyncTask extends AsyncTask<Void, Void, List<Info>> {

		@Override
		protected List<Info> doInBackground(Void... params) {
			Cursor cursor = getActivity().getContentResolver().query(
					EarthquakeContentProvider.INFO_URI, 
					null, 
					null, 
					null, 
					null);
			
			List<Info> infoList = new ArrayList<Info>();
			
			while (cursor.moveToNext()) {
				Info info = new Info();
				info.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				info.setLocation(cursor.getString(cursor.getColumnIndex("location")));
				info.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
				info.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
				
				String magnitude = cursor.getString(cursor.getColumnIndex("magnitude"));
				if (magnitude.indexOf('.') == -1) {  
					magnitude += ".0";
				}
				info.setMagnitude(magnitude);
				
				info.setDepth(cursor.getString(cursor.getColumnIndex("depth")));
				infoList.add(info);
			}
			
			return infoList;
		}
		
		@Override
		protected void onPostExecute(List<Info> result) {
			if ((getActivity() != null) && (result != null)) {
				infoAdapter.setInfoList(result);
				infoAdapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), "Loaded", Toast.LENGTH_LONG).show();
			}
		}

	}
	
}
