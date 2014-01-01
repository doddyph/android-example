package com.example.earthquakereader.entity;

import android.util.Log;

public class Info {

	private String title;
	private String location;
	private String latitude;
	private String longitude;
	private String magnitude;
	private String depth;
	
	public String getTitle() {
		Log.v(getClass().getName(), "getTitle() title: "+title);
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		Log.v(getClass().getName(), "setTitle() title: "+this.title);
		
	}

	public String getLocation() {
		Log.v(getClass().getName(), "getLocation() location: "+location);
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
		Log.v(getClass().getName(), "setLocation() location: "+this.location);
		
	}

	public String getLatitude() {
		Log.v(getClass().getName(), "getLatitude() latitude: "+latitude);
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
		Log.v(getClass().getName(), "setLatitude() latitude: "+this.latitude);
		
	}

	public String getLongitude() {
		Log.v(getClass().getName(), "getLongitude() longitude: "+longitude);
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
		Log.v(getClass().getName(), "setLongitude() longitude: "+this.longitude);
		
	}
	
	public String getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(String magnitude) {
		this.magnitude = magnitude;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return location + " [" + latitude + "," + longitude+"]"/* + latitude!=null? " ["+latitude:"" + longitude!=null? ","+longitude+"]":""*/;
	}
}
