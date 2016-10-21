package com.sfamobile.dahlia.sfamobile.Activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sfamobile.dahlia.sfamobile.Fragments.WorkaroundMapFragment;
import com.sfamobile.dahlia.sfamobile.JSONParser.HttpConnection;
import com.sfamobile.dahlia.sfamobile.JSONParser.PathJSONParser;
import com.sfamobile.dahlia.sfamobile.R;
import com.sfamobile.dahlia.sfamobile.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrackingUserGoogleMapActivity extends AppCompatActivity {

    GoogleMap map;

    private LatLng START_LOCATIONS = null;
    private LatLng END_LOCATIONS = null;

    private TextView mDistanceTV = null;
    private TextView mDurationTV = null;

    int mDistance;

    String jDistance = "";
    String jDuration = "";

    int distance;
    private String mStartLocationName;
    private String mEndLocationName;
    private int PLAN_TRAVAL_CHOICE = 0;
    private Button mEndJourny = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_user_google_map);




        Bundle bundle = getIntent().getParcelableExtra("bundle");
        START_LOCATIONS = bundle.getParcelable("StartLocation");
        END_LOCATIONS = bundle.getParcelable("EndLocation");


        mDurationTV = (TextView) findViewById(R.id.duration_value_tv);
        mDistanceTV = (TextView) findViewById(R.id.distance_value_tv);

        mEndJourny = (Button) findViewById(R.id.atugm_start_location_btn);
        mEndJourny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Bitmap[] bitmap = new Bitmap[1];

                PLAN_TRAVAL_CHOICE = 1;

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

                                final Dialog dialog = new Dialog(TrackingUserGoogleMapActivity.this);
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
                                        Utils.getIndianCurrencyCommaSeparatedWithoutSymbol(
                                                String.valueOf(mDistance * Integer.parseInt(perKMStr) / 1000), false));
                                total_bill_value_tv.setText(amountStr);

                                String amountStr1 = String.format(getString(R.string.rupess_symbol_with_amount),
                                        Utils.getIndianCurrencyCommaSeparatedWithoutSymbol(
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



        if (START_LOCATIONS != null && END_LOCATIONS != null) {
            initGoogleMap();
            ReadTask downloadTask = new ReadTask();
            String url1 = "https://maps.googleapis.com/maps/api/directions/json?" + "origin="
                    + START_LOCATIONS.latitude + "," + START_LOCATIONS.longitude + "&destination="
                    + END_LOCATIONS.latitude + "," + END_LOCATIONS.longitude
                    + "&sensor=false&mode=driving&language=en-EN";
            downloadTask.execute(url1);
        }






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




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
        }
    }
    private void addMarkers() {
        if (map != null) {
            map.addMarker(new MarkerOptions().position(START_LOCATIONS).title(mStartLocationName));
            map.addMarker(new MarkerOptions().position(END_LOCATIONS).title(mEndLocationName));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(START_LOCATIONS, 9));
        }
    }

}
