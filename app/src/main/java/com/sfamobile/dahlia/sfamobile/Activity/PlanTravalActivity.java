package com.sfamobile.dahlia.sfamobile.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sfamobile.dahlia.sfamobile.Adapter.SpinnerAdapter;
import com.sfamobile.dahlia.sfamobile.Fragments.WorkaroundMapFragment;
import com.sfamobile.dahlia.sfamobile.JSONParser.HttpConnection;
import com.sfamobile.dahlia.sfamobile.JSONParser.PathJSONParser;
import com.sfamobile.dahlia.sfamobile.Listener.GPSTrackerListener;
import com.sfamobile.dahlia.sfamobile.Listener.MyLocationListener;
import com.sfamobile.dahlia.sfamobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

public class PlanTravalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

	private LatLng START_LOCATIONS = null;
	private LatLng END_LOCATIONS = null;

	private EditText mStartLocationET = null;
	private EditText mEndLocationET = null;
	private EditText mEstimatedAmount = null;

	private RadioGroup mTravalPlanRadioGroup = null;

	private RadioButton mTwoWheelerRBT = null;
	private RadioButton mFourWheelerRBT = null;
	private RadioButton mPublicTravelRBT = null;
	private RadioButton mAutoRickshawRBT = null;
	private RadioButton mRadioCabRBT = null;

	private Button mPublicTravelCaptureImageButton = null;
	private Button mAutoRickshawCaptureImageButton = null;

	private Spinner mPublicTravelSP = null;
	private Spinner mRadioCabTravelSP = null;

	private TextView mDistanceTV = null;
	private TextView mDurationTV = null;

	private AutoCompleteTextView mDistanceATV = null;

	boolean isRefreshMap = false;

	int PLAN_TRAVAL_CHOICE = 0;

	GPSTrackerListener gps;

	// String mDistance = null;
	// String mDuration = null;

	String[] mPublicTravel = { "Select", "Bus", "Train" };
	String[] mRadioCabTravel = { "Select", "OLA", "UBER", "MERU", "ZOOMCAR" };

	GoogleMap map;

	int mDistance;

	String jDistance = "";
	String jDuration = "";
	int distance;
	private ScrollView mScrollView = null;

	TextView mActivityNameTV = null;
	ImageView mActivityBackIMV = null;

	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in
																		// Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in
																	// Milliseconds

	private LocationManager locationManager;
	private String provider;

	private Button mStartJourneyButton = null;

	private Button mStartLocationButton = null;
	private Button mEndLocationButton = null;
	private int index = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan_traval);
		final Dialog dialog = new Dialog(PlanTravalActivity.this);
		// Include dialog.xml file
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(R.layout.dialog_traval_plan);

		mTravalPlanRadioGroup = (RadioGroup) dialog.findViewById(R.id.dtp_radio_button_group);
		Button confirmeButton = (Button) dialog.findViewById(R.id.dtp_button_confirm);
		confirmeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		mTravalPlanRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				View radioButton = radioGroup.findViewById(i);
				index = i;

			}
		});



		dialog.show();

		initView();
		initGoogleMap();

	}

	private void initGoogleMap() {

		try {

			map = ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1)).getMap();
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			map.getUiSettings().setZoomControlsEnabled(true);
			map.setMyLocationEnabled(true);
			map.getUiSettings().setCompassEnabled(true);
			map.getUiSettings().setMyLocationButtonEnabled(true);
			map.getUiSettings().setRotateGesturesEnabled(true);
			map.getUiSettings().setTiltGesturesEnabled(true);
			mScrollView = (ScrollView) findViewById(R.id.apt_sv); // parent
																	// scrollview
																	// in xml,
																	// give your
																	// scrollview
																	// id value

			((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1))
					.setListener(new WorkaroundMapFragment.OnTouchListener() {
						@Override
						public void onTouch() {
							mScrollView.requestDisallowInterceptTouchEvent(true);
						}
					});

			// map = ((MapFragment)
			// getFragmentManager().findFragmentById(R.id.fragment1)).getMap();
			// isRefreshMap = true;
			// MarkerOptions k = new MarkerOptions()
			// .position(new LatLng(18.518866, 73.935008))
			// .title("Dahlia Tech")
			// .draggable(true).snippet("In Amenora Chember");
			// MarkerOptions k1 = new MarkerOptions()
			// .position(new LatLng(18.540503, 73.934711))
			// .title("Zensar LTD")
			// .draggable(true).snippet("EON IT Park");
			// map.addMarker(k1);

			// CameraPosition cameraPosition = new CameraPosition.Builder()
			// .target(new LatLng(18.518866, 73.935008)).zoom(10).build();
			//
			// map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

			// map.addMarker(k);

			// String url = getMapsApiDirectionsUrl();
			// ReadTask downloadTask = new ReadTask();
			// downloadTask.execute(url);

			// map.moveCamera(CameraUpdateFactory.newLatLngZoom(new
			// LatLng(18.518866, 73.935008),
			// 12));
			// addMarkers();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// try {

	// map = ((MapFragment)
	// getFragmentManager().findFragmentById(R.id.fragment1)).getMap();
	// isRefreshMap = true;
	// // MarkerOptions k = new MarkerOptions()
	// // .position(new LatLng(18.518866, 73.935008))
	// // .title("Dahlia Tech")
	// // .draggable(true).snippet("In Amenora Chember");
	// // MarkerOptions k1 = new MarkerOptions()
	// // .position(new LatLng(18.540503, 73.934711))
	// // .title("Zensar LTD")
	// // .draggable(true).snippet("EON IT Park");
	// // map.addMarker(k1);
	// map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	// map.setMyLocationEnabled(true);
	// map.getUiSettings().setZoomControlsEnabled(true);
	// map.getUiSettings().setZoomGesturesEnabled(true);
	// map.getUiSettings().setCompassEnabled(true);
	// map.getUiSettings().setMyLocationButtonEnabled(true);
	// map.getUiSettings().setRotateGesturesEnabled(true);
	// map.getUiSettings().setTiltGesturesEnabled(true);
	//
	// // CameraPosition cameraPosition = new CameraPosition.Builder()
	// // .target(new LatLng(18.518866, 73.935008)).zoom(10).build();
	// //
	// //
	// map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	//
	// // map.addMarker(k);
	//
	// // String url = getMapsApiDirectionsUrl();
	// // ReadTask downloadTask = new ReadTask();
	// // downloadTask.execute(url);
	//
	// // map.moveCamera(CameraUpdateFactory.newLatLngZoom(new
	// // LatLng(18.518866, 73.935008),
	// // 12));
	// // addMarkers();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	private void initView() {

		mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
		mActivityNameTV.setText("Travel Details");
		mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
		mActivityBackIMV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(PlanTravalActivity.this, AddMeetingAndUpdateMeetingActivity.class);
				startActivity(intent);
				finish();

			}
		});

		// mLocationManager = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);
		// if (ActivityCompat.checkSelfPermission(this,
		// Manifest.permission.ACCESS_FINE_LOCATION) !=
		// PackageManager.PERMISSION_GRANTED) {
		// // TODO: Consider calling
		// // ActivityCompat#requestPermissions
		// // here to request the missing permissions, and then overriding
		// // public void onRequestPermissionsResult(int requestCode, String[]
		// // permissions,
		// // int[] grantResults)
		// // to handle the case where the user grants the permission. See the
		// // documentation
		// // for ActivityCompat#requestPermissions for more details.
		// return;
		// }
		// mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		// MINIMUM_TIME_BETWEEN_UPDATES,
		// MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new
		// MyLocationListener(PlanTravalActivity.this));


		// check if GPS enabled



		mStartLocationButton = (Button) findViewById(R.id.apt_select_start_location_button);
		mStartLocationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				GPSTrackerListener gpsTracker = new GPSTrackerListener(PlanTravalActivity.this);

				if (gpsTracker.getIsGPSTrackingEnabled())
				{
					String stringLatitude = String.valueOf(gpsTracker.getLatitude());

					String stringLongitude = String.valueOf(gpsTracker.getLongitude());


					Toast.makeText(PlanTravalActivity.this,"Lat"+stringLatitude+"Lon"+stringLongitude,Toast.LENGTH_LONG).show();


					END_LOCATIONS = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
					if (START_LOCATIONS != null && END_LOCATIONS != null) {
						initGoogleMap();
						ReadTask downloadTask = new ReadTask();
						String url1 = "https://maps.googleapis.com/maps/api/directions/json?" + "origin="
								+ START_LOCATIONS.latitude + "," + START_LOCATIONS.longitude + "&destination="
								+ END_LOCATIONS.latitude + "," + END_LOCATIONS.longitude
								+ "&sensor=false&mode=driving&language=en-EN";
						downloadTask.execute(url1);
					}


//			String country = gpsTracker.getCountryName(this);
//
//			String city = gpsTracker.getLocality(this);
//
//			String postalCode = gpsTracker.getPostalCode(this);
//
//			String addressLine = gpsTracker.getAddressLine(this);
				}
				else
				{
					// can't get location
					// GPS or Network is not enabled
					// Ask user to enable GPS/network in settings
					gpsTracker.showSettingsAlert();
				}


			}
		});

		mEndLocationButton = (Button) findViewById(R.id.apt_select_client_location_button);
		mEndLocationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GPSTrackerListener gpsTracker = new GPSTrackerListener(PlanTravalActivity.this);

				if (gpsTracker.getIsGPSTrackingEnabled())
				{
					String stringLatitude = String.valueOf(gpsTracker.getLatitude());

					String stringLongitude = String.valueOf(gpsTracker.getLongitude());


					Toast.makeText(PlanTravalActivity.this,"Lat"+stringLatitude+"Lon"+stringLongitude,Toast.LENGTH_LONG).show();


					START_LOCATIONS = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
					if (START_LOCATIONS != null && END_LOCATIONS != null) {
						initGoogleMap();
						ReadTask downloadTask = new ReadTask();
						String url1 = "https://maps.googleapis.com/maps/api/directions/json?" + "origin="
								+ START_LOCATIONS.latitude + "," + START_LOCATIONS.longitude + "&destination="
								+ END_LOCATIONS.latitude + "," + END_LOCATIONS.longitude
								+ "&sensor=false&mode=driving&language=en-EN";
						downloadTask.execute(url1);
					}


//			String country = gpsTracker.getCountryName(this);
//
//			String city = gpsTracker.getLocality(this);
//
//			String postalCode = gpsTracker.getPostalCode(this);
//
//			String addressLine = gpsTracker.getAddressLine(this);
				}
				else
				{
					// can't get location
					// GPS or Network is not enabled
					// Ask user to enable GPS/network in settings
					gpsTracker.showSettingsAlert();
				}
			}
		});

		mDistanceATV = (AutoCompleteTextView) findViewById(R.id.clto_distance_et);

		mStartJourneyButton = (Button) findViewById(R.id.apt_start_location_btn);
		mStartJourneyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Bitmap[] bitmap = new Bitmap[1];
				GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {

					@Override
					public void onSnapshotReady(Bitmap snapshot) {
						bitmap[0] = snapshot;
						try {
							// FileOutputStream out = new
							// FileOutputStream("/mnt/sdcard/Download/googleMapLoc.png");
							// bitmap[0].compress(Bitmap.CompressFormat.PNG, 90,
							// out);
							if ((PLAN_TRAVAL_CHOICE == 0) || (PLAN_TRAVAL_CHOICE == 1)) {
								String perKMStr = "";

								if (PLAN_TRAVAL_CHOICE == 0) {
									perKMStr = "3";
								} else {
									perKMStr = "10";
								}

								final Dialog dialog = new Dialog(PlanTravalActivity.this);
								// Include dialog.xml file
								dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
								dialog.setCanceledOnTouchOutside(true);
								dialog.setContentView(R.layout.dialog_recipt_details);

								TextView baseFareTV = (TextView) dialog.findViewById(R.id.base_fare_value_tv);
								baseFareTV.setText(perKMStr);

								TextView total_distance_value_tv = (TextView) dialog
										.findViewById(R.id.total_distance_value_tv);
								total_distance_value_tv.setText(jDistance);

								// TextView ride_time_fare_value_tv = (TextView)
								// dialog.findViewById(R.id.ride_time_fare_value_tv);
								// ride_time_fare_value_tv.setText("");
								//
								// TextView total_fare_value_tv = (TextView)
								// dialog.findViewById(R.id.total_fare_value_tv);
								// total_fare_value_tv.setText("");
								//
								// TextView taxes_fare_value_tv = (TextView)
								// dialog.findViewById(R.id.taxes_fare_value_tv);
								// taxes_fare_value_tv.setText("");

								TextView total_bill_value_tv = (TextView) dialog.findViewById(R.id.total_bill_value_tv);
								String amountStr = String.format(getString(R.string.rupess_symbol_with_amount),
										getIndianCurrencyCommaSeparatedWithoutSymbol(
												String.valueOf(mDistance * Integer.parseInt(perKMStr) / 1000), false));
								total_bill_value_tv.setText(amountStr);

								String amountStr1 = String.format(getString(R.string.rupess_symbol_with_amount),
										getIndianCurrencyCommaSeparatedWithoutSymbol(
												String.valueOf(mDistance * Integer.parseInt(perKMStr) / 1000), false));

								TextView paid_by_cash_value_tv = (TextView) dialog
										.findViewById(R.id.paid_by_cash_value_tv);
								paid_by_cash_value_tv.setText(amountStr1);

								ImageView ride_details_img = (ImageView) dialog.findViewById(R.id.ride_details_img);
								// ride_details_img.setImageBitmap(bitmap[0]);
								Drawable d = new BitmapDrawable(getResources(), bitmap[0]);
								ride_details_img.setBackground(d);

								dialog.show();
							} else {
								//
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};

				map.snapshot(callback);

			}

		});

		mStartLocationET = (EditText) findViewById(R.id.apt_select_start_location_et);
		mStartLocationET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {

					getMapAPIStartLocaton(mStartLocationET.getText().toString());

					return true;
				}
				return false;
			}
		});

		mStartLocationET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					getMapAPIStartLocaton(mStartLocationET.getText().toString());
				}

			}
		});

		mEndLocationET = (EditText) findViewById(R.id.apt_select_client_location_et);
		mEndLocationET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {

					getMapAPIStartLocaton1(mEndLocationET.getText().toString());

					return true;
				}
				return false;
			}
		});

		mEndLocationET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					getMapAPIStartLocaton1(mEndLocationET.getText().toString());
				}
			}
		});

		mEstimatedAmount = (EditText) findViewById(R.id.apt_amount_atv);

		mDurationTV = (TextView) findViewById(R.id.duration_value_tv);
		mDistanceTV = (TextView) findViewById(R.id.distance_value_tv);

		mPublicTravelCaptureImageButton = (Button) findViewById(R.id.apt_capture_ticket_img_button);
		mPublicTravelCaptureImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectImage();
			}
		});

		mAutoRickshawCaptureImageButton = (Button) findViewById(R.id.apt_auto_capture_ticket_img_button);
		mAutoRickshawCaptureImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectImage();
			}
		});

		mTwoWheelerRBT = (RadioButton) findViewById(R.id.dtp_two_wheeler_radiobtn);
		mFourWheelerRBT = (RadioButton) findViewById(R.id.dtp_four_wheeler_radiobtn);
		mPublicTravelRBT = (RadioButton) findViewById(R.id.dtp_public_travel_radiobtn);
		mAutoRickshawRBT = (RadioButton) findViewById(R.id.dtp_auto_rickshaw_radiobtn);
		mRadioCabRBT = (RadioButton) findViewById(R.id.dtp_radio_cab_radiobtn);

		mPublicTravelSP = (Spinner) findViewById(R.id.dtp_public_travel_radiobtn_spinner);
		SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_status_item, R.id.spinner_value_tv,
				mPublicTravel);
		mPublicTravelSP.setAdapter(adapter);
		mPublicTravelSP.setOnItemSelectedListener(this);

		mRadioCabTravelSP = (Spinner) findViewById(R.id.dtp_radio_cab_radiobtn_sppiner);
		SpinnerAdapter adapter1 = new SpinnerAdapter(this, R.layout.spinner_status_item, R.id.spinner_value_tv,
				mRadioCabTravel);
		mRadioCabTravelSP.setAdapter(adapter1);
		mRadioCabTravelSP.setOnItemSelectedListener(this);

		mTwoWheelerRBT.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTwoWheelerRBT.setChecked(true);
				mFourWheelerRBT.setChecked(false);
				mPublicTravelRBT.setChecked(false);
				mAutoRickshawRBT.setChecked(false);
				mRadioCabRBT.setChecked(false);
				mPublicTravelSP.setVisibility(View.GONE);
				mRadioCabTravelSP.setVisibility(View.GONE);
				mPublicTravelCaptureImageButton.setVisibility(View.GONE);
				mAutoRickshawCaptureImageButton.setVisibility(View.GONE);
				PLAN_TRAVAL_CHOICE = 0;
				mEstimatedAmount.setText(String.valueOf(mDistance * 3 / 1000));
			}
		});

		mFourWheelerRBT.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mFourWheelerRBT.setChecked(true);
				mTwoWheelerRBT.setChecked(false);
				mPublicTravelRBT.setChecked(false);
				mAutoRickshawRBT.setChecked(false);
				mRadioCabRBT.setChecked(false);
				mPublicTravelSP.setVisibility(View.GONE);
				mRadioCabTravelSP.setVisibility(View.GONE);
				mPublicTravelCaptureImageButton.setVisibility(View.GONE);
				mAutoRickshawCaptureImageButton.setVisibility(View.GONE);
				PLAN_TRAVAL_CHOICE = 1;
				mEstimatedAmount.setText(String.valueOf(mDistance * 10 / 1000));
			}
		});

		mPublicTravelRBT.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPublicTravelRBT.setChecked(true);
				mFourWheelerRBT.setChecked(false);
				mTwoWheelerRBT.setChecked(false);
				mAutoRickshawRBT.setChecked(false);
				mRadioCabRBT.setChecked(false);
				mPublicTravelSP.setVisibility(View.VISIBLE);
				mPublicTravelCaptureImageButton.setVisibility(View.VISIBLE);
				mAutoRickshawCaptureImageButton.setVisibility(View.GONE);
				PLAN_TRAVAL_CHOICE = 3;
				mRadioCabTravelSP.setVisibility(View.GONE);
			}
		});

		mAutoRickshawRBT.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAutoRickshawRBT.setChecked(true);
				mFourWheelerRBT.setChecked(false);
				mPublicTravelRBT.setChecked(false);
				mTwoWheelerRBT.setChecked(false);
				mRadioCabRBT.setChecked(false);
				mPublicTravelSP.setVisibility(View.GONE);
				mPublicTravelCaptureImageButton.setVisibility(View.GONE);
				mAutoRickshawCaptureImageButton.setVisibility(View.VISIBLE);
				mRadioCabTravelSP.setVisibility(View.GONE);
				PLAN_TRAVAL_CHOICE = 4;
			}
		});

		mRadioCabRBT.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mRadioCabRBT.setChecked(true);
				mFourWheelerRBT.setChecked(false);
				mPublicTravelRBT.setChecked(false);
				mAutoRickshawRBT.setChecked(false);
				mTwoWheelerRBT.setChecked(false);
				mPublicTravelSP.setVisibility(View.GONE);
				mPublicTravelCaptureImageButton.setVisibility(View.GONE);
				mAutoRickshawCaptureImageButton.setVisibility(View.GONE);
				mRadioCabTravelSP.setVisibility(View.VISIBLE);
				PLAN_TRAVAL_CHOICE = 5;
			}
		});

	}

	public void turnGPSOn() {
		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", true);
		this.sendBroadcast(intent);

		String provider = Settings.Secure.getString(this.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!provider.contains("gps")) { // if gps is disabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			this.sendBroadcast(poke);

		}
	}
	// automa

	public void turnGPSOff() {
		String provider = Settings.Secure.getString(this.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (provider.contains("gps")) { // if gps is enabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			this.sendBroadcast(poke);
		}
	}



	public static String getIndianCurrencyCommaSeparatedWithoutSymbol(String amount, boolean withDecimal) {
		String formattedMoney = amount;
		try {
			Double money = Double.parseDouble(amount);
			if (withDecimal) {
				DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
				DecimalFormatSymbols symbols = new DecimalFormatSymbols();
				symbols.setCurrencySymbol("");
				formatter.setDecimalFormatSymbols(symbols);
				formattedMoney = formatter.format(money);
			} else {
				NumberFormat formatter = DecimalFormat.getInstance(new Locale("en", "IN"));
				formattedMoney = formatter.format(money);
			}
		} catch (NumberFormatException e) {
			Log.e("Utils", e.getMessage());
		}

		return formattedMoney;
	}

	private void selectImage() {
		final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add Photo!");
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					startActivityForResult(intent, 1);
				} else if (options[item].equals("Choose from Gallery")) {
					Intent intent = new Intent(Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, 2);

				} else if (options[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				File f = new File(Environment.getExternalStorageDirectory().toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}
				try {
					Bitmap bitmap;
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

					bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);

					// viewImage.setImageBitmap(bitmap);

					String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "Phoenix"
							+ File.separator + "default";
					f.delete();
					OutputStream outFile = null;
					File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
					try {
						outFile = new FileOutputStream(file);
						bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
						outFile.flush();
						outFile.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (requestCode == 2) {

				Uri selectedImage = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
				String picturePath = c.getString(columnIndex);
				c.close();
				Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
				Log.w("image from gallery", picturePath + "");
				// viewImage.setImageBitmap(thumbnail);
			}
		}
	}

	// https://maps.googleapis.com/maps/api/directions/json?origin=New+York,+NY&destination=Boston,+MA&waypoints=optimize:true|Providence,+RI|Hartford,+CT&key=YOUR_API_KEY

	private String getMapsApiDirectionsUrl() {
		// String waypoints = "waypoints=optimize:true|"
		// + DAHLIA_TECH.latitude + "," + DAHLIA_TECH.longitude
		// + "|" + "|" + ZENSAR.latitude + ","
		// + ZENSAR.longitude;
		//
		// String sensor = "sensor=false";
		// String params = waypoints + "&" + sensor;
		// String output = "json";
		// String url =
		// "https://maps.googleapis.com/maps/api/directions/json?origin="+
		// DAHLIA_TECH.latitude + "," + DAHLIA_TECH.longitude +"&destination="+
		// ZENSAR.latitude + "," +
		// ZENSAR.longitude+"&waypoints=optimize:true|Providence&key=AIzaSyA69pxhm_7XBLl9N5egf7yETeQNOt-g9tc";

		String url = "https://maps.googleapis.com/maps/api/directions/json?origin=18.518866,73.935008&destination=18.540503,%2073.934711&sensor=false&mode=driving&language=en-EN";

		return url;
	}

	private void getMapAPIStartLocaton(String url) {
		String locations = "";
		StringTokenizer tokens = new StringTokenizer(url, " ");
		String[] splited = new String[tokens.countTokens()];
		int index = 0;
		while (tokens.hasMoreTokens()) {
			splited[index] = tokens.nextToken();
			locations = locations + splited[index];
			++index;
		}

		String url1 = "http://maps.google.com/maps/api/geocode/json?address=" + locations + "&sensor=false";
		new DataLongOperationAsynchTask().execute(url1);
	}

	private void getMapAPIStartLocaton1(String url) {
		String locations = "";
		StringTokenizer tokens = new StringTokenizer(url, " ");
		String[] splited = new String[tokens.countTokens()];
		int index = 0;
		while (tokens.hasMoreTokens()) {
			splited[index] = tokens.nextToken();
			locations = locations + splited[index];
			++index;
		}
		String url1 = "http://maps.google.com/maps/api/geocode/json?address=" + locations + "&sensor=false";
		new DataLongOperationAsynchTask1().execute(url1);
	}

	private void addMarkers() {
		if (map != null) {
			map.addMarker(new MarkerOptions().position(START_LOCATIONS).title(mStartLocationET.getText().toString()));
			map.addMarker(new MarkerOptions().position(END_LOCATIONS).title(mEndLocationET.getText().toString()));
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(START_LOCATIONS, 9));
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	// @Override
	// public void beforeTextChanged(CharSequence s, int start, int count, int
	// after) {
	//
	// }
	//
	// @Override
	// public void onTextChanged(CharSequence s, int start, int before, int
	// count) {
	//
	// }
	//
	// @Override
	// public void afterTextChanged(Editable s) {
	// new
	// DataLongOperationAsynchTask().execute(mStartLocationATV.getText().toString());
	// }

	private class ReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				HttpConnection http = new HttpConnection();
				data = http.readUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			new ParserTask().execute(result);
		}
	}

	private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;
			JSONArray jRoutes = null;
			JSONArray jLegs = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				PathJSONParser parser = new PathJSONParser();
				routes = parser.parse(jObject);
				jRoutes = jObject.getJSONArray("routes");
				/** Traversing all routes */
				for (int i = 0; i < jRoutes.length(); i++) {
					jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
					List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

					/** Traversing all legs */
					for (int j = 0; j < jLegs.length(); j++) {
						jDistance = (String) ((JSONObject) ((JSONObject) jLegs.get(j)).get("distance")).get("text");
						distance = (int) ((JSONObject) ((JSONObject) jLegs.get(j)).get("distance")).get("value");
						mDistance = distance;
						jDuration = (String) ((JSONObject) ((JSONObject) jLegs.get(j)).get("duration")).get("text");
					}
				}
				// JSONArray jRoutes = null;
				// jRoutes = jObject.getJSONArray("routes");
				// JSONArray jLegs = null;
				// jLegs = (JSONArray) jRoutes.get(2);
				// JSONObject desc = jLegs.getJSONObject("distance");
				// String distance = desc.getString("text");
				// JSONObject dura = jLegs.getJSONObject("distance");
				// String duration = dura.getString("text");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
			ArrayList<LatLng> points = null;
			PolylineOptions polyLineOptions = null;

			// traversing through routes
			for (int i = 0; i < routes.size(); i++) {
				points = new ArrayList<LatLng>();
				polyLineOptions = new PolylineOptions();
				List<HashMap<String, String>> path = routes.get(i);

				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				polyLineOptions.addAll(points);
				// polyLineOptions.width(2);
				polyLineOptions.color(R.color.colorAccent);
			}

			map.addPolyline(polyLineOptions);
			addMarkers();
			mDurationTV.setText(jDuration);
			mDistanceTV.setText(jDistance);
			mDistanceATV.setText(jDistance);
		}
	}

	private class DataLongOperationAsynchTask extends AsyncTask<String, Void, String[]> {
		// ProgressDialog dialog = new ProgressDialog(this);
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// dialog.setMessage("Please wait...");
			// dialog.setCanceledOnTouchOutside(false);
			// dialog.show();
		}

		@Override
		protected String[] doInBackground(String... params) {
			String response;
			try {
				response = getLatLongByURL(params[0]);
				Log.d("response", "" + response);
				return new String[] { response };
			} catch (Exception e) {
				e.printStackTrace();
				return new String[] { "error" };
			}
		}

		@Override
		protected void onPostExecute(String... result) {
			try {
				JSONObject jsonObject = new JSONObject(result[0]);

				double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
						.getJSONObject("location").getDouble("lng");

				double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
						.getJSONObject("location").getDouble("lat");

				Log.d("latitude", "" + lat);
				Log.d("longitude", "" + lng);
				START_LOCATIONS = new LatLng(lat, lng);
				if (START_LOCATIONS != null && END_LOCATIONS != null) {
					initGoogleMap();
					ReadTask downloadTask = new ReadTask();
					String url1 = "https://maps.googleapis.com/maps/api/directions/json?" + "origin="
							+ START_LOCATIONS.latitude + "," + START_LOCATIONS.longitude + "&destination="
							+ END_LOCATIONS.latitude + "," + END_LOCATIONS.longitude
							+ "&sensor=false&mode=driving&language=en-EN";
					downloadTask.execute(url1);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// if (dialog.isShowing()) {
			// dialog.dismiss();
			// }
		}

		public String getLatLongByURL(String requestURL) {
			URL url;
			String response = "";
			try {
				url = new URL(requestURL);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(15000);
				conn.setConnectTimeout(15000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setDoOutput(true);
				int responseCode = conn.getResponseCode();

				if (responseCode == HttpsURLConnection.HTTP_OK) {
					String line;
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					while ((line = br.readLine()) != null) {
						response += line;
					}
				} else {
					response = "";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
		}
	}

	private class DataLongOperationAsynchTask1 extends AsyncTask<String, Void, String[]> {
		// ProgressDialog dialog = new ProgressDialog(this);
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// dialog.setMessage("Please wait...");
			// dialog.setCanceledOnTouchOutside(false);
			// dialog.show();
		}

		@Override
		protected String[] doInBackground(String... params) {
			String response;
			try {
				response = getLatLongByURL(params[0]);
				Log.d("response", "" + response);
				return new String[] { response };
			} catch (Exception e) {
				return new String[] { "error" };
			}
		}

		@Override
		protected void onPostExecute(String... result) {
			try {
				JSONObject jsonObject = new JSONObject(result[0]);

				double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
						.getJSONObject("location").getDouble("lng");

				double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
						.getJSONObject("location").getDouble("lat");

				Log.d("latitude", "" + lat);
				Log.d("longitude", "" + lng);
				END_LOCATIONS = new LatLng(lat, lng);
				if (START_LOCATIONS != null && END_LOCATIONS != null) {
					initGoogleMap();
					ReadTask downloadTask = new ReadTask();
					String url1 = "https://maps.googleapis.com/maps/api/directions/json?" + "origin="
							+ START_LOCATIONS.latitude + "," + START_LOCATIONS.longitude + "&destination="
							+ END_LOCATIONS.latitude + "," + END_LOCATIONS.longitude
							+ "&sensor=false&mode=driving&language=en-EN";
					downloadTask.execute(url1);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// if (dialog.isShowing()) {
			// dialog.dismiss();
			// }
		}

		public String getLatLongByURL(String requestURL) {
			URL url;
			String response = "";
			try {
				url = new URL(requestURL);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(15000);
				conn.setConnectTimeout(15000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setDoOutput(true);
				int responseCode = conn.getResponseCode();

				if (responseCode == HttpsURLConnection.HTTP_OK) {
					String line;
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					while ((line = br.readLine()) != null) {
						response += line;
					}
				} else {
					response = "";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
		}
	}

}
