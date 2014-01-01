package com.example.earthquakereader.adapter;

import java.util.List;

import com.example.earthquakereader.R;
import com.example.earthquakereader.entity.Info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InfoAdapter extends BaseAdapter {

	private Context context;
	private List<Info> infoList;
	
	public InfoAdapter(Context context) {
		this.context = context;
	}
	
	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}
	
	@Override
	public int getCount() {
		return infoList.size();
	}

	@Override
	public Object getItem(int location) {
		return infoList.get(location);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int i, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.list_row_item, null, false);
			
			holder = new ViewHolder();
			holder.magnitudeView = (TextView) convertView.findViewById(R.id.text_magnitude);
			holder.titleView = (TextView) convertView.findViewById(R.id.text_title);
			holder.locationView = (TextView) convertView.findViewById(R.id.text_location);
			
			convertView.setTag(holder);
		}
		
		holder = (ViewHolder) convertView.getTag();
		holder.magnitudeView.setText(infoList.get(i).getMagnitude());
		holder.titleView.setText(infoList.get(i).getTitle());
		holder.locationView.setText(infoList.get(i).getLocation());
		
		return convertView;
	}
	
	private class ViewHolder {
		TextView magnitudeView;
		TextView titleView;
		TextView locationView;
	}

}
