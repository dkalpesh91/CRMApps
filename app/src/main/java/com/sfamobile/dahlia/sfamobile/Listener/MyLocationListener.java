package com.sfamobile.dahlia.sfamobile.Listener;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Admin on 02-09-2016.
 */
public class MyLocationListener implements LocationListener {
	Activity mActivity = null;

	public MyLocationListener(Activity activity) {

		mActivity = activity;
	}

	public void onLocationChanged(Location location) {
		String message = String.format("New Location \n Longitude: %1$s \n Latitude: %2$s", location.getLongitude(),
				location.getLatitude()

		);
		Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();

	}

	public void onStatusChanged(String s, int i, Bundle b) {
		Toast.makeText(mActivity, "Provider status changed", Toast.LENGTH_LONG).show();
	}

	public void onProviderDisabled(String s) {
		Toast.makeText(mActivity, "Provider disabled by the user. GPS turned off", Toast.LENGTH_LONG).show();
	}

	public void onProviderEnabled(String s) {
		Toast.makeText(mActivity, "Provider enabled by the user. GPS turned on", Toast.LENGTH_LONG).show();
	}
}
