package com.j4pro.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    Context mContext;
    private Location currentBestLocation = null;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void compartirContenidojs(String toast) {
        //saca mnesajes

        //   Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        compartirContenido(toast);
    }
    @JavascriptInterface
    public String getLastBestLocationjs() {
        //saca mnesajes
        Location lastBestLocation = getLastBestLocation();
        if(lastBestLocation!=null){
            return lastBestLocation.getLatitude()+";"+ lastBestLocation.getLongitude()+";"+lastBestLocation.getSpeed();
        }
        return "";
        //   Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
      // return getLastBestLocation();
    }


   /* @SuppressLint("MissingPermission")
    public Location getLocation() {
        if (mLocationClient != null && mLocationClient.isConnected()) {
            return mLocationClient.getLastLocation();
        } else {
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocationGPS != null) {
                    return lastKnownLocationGPS;
                } else {
                    return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            } else {
                return null;
            }
        }
    }*/
    /**
     * @return the last know best location
     */
    @SuppressLint("MissingPermission")
    private Location getLastBestLocation() {
        //  LocationManager mLocationManager = (LocationManager) mContext.getSystemService(LocationManager.GPS_PROVIDER);

        LocationManager mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);


       // if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
      //          ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

   //   if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {

        Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);



        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {

            return locationGPS;
        }
        else {
            return locationNet;
        }
      //  }
      //  return null;
    }
    public  void compartirContenido(String url) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        mContext.startActivity(shareIntent);
    }

}