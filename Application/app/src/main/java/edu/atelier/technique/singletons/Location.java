package edu.atelier.technique.singletons;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/** EXEMPLE D'APPEL :
 Location.getInstance().getCurrentLocation();

 NOTE :
 Pensez à initisialiser le singleton dans la première activité avec : Location.getInstance().setUp(this);
 */

public class Location {

    private LatLng latLng;
    private Boolean isSetUp;

    private static Location INSTANCE;

    public static Location getInstance() {
        if (INSTANCE == null){ INSTANCE = new Location();}
        return INSTANCE;
    }

    private Location(){
        this.isSetUp = false;
    }

    public LatLng getCurrentLocation(){
        if (! this.isSetUp) { return null; }
        return this.latLng;
    }

    public void setUp(Context context) {
        if(context == null ){ return; }

        this.isSetUp = true;

        String location_context = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) context.getSystemService(location_context);
        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(provider, 1000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull android.location.Location location) {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                }

                public void onProviderDisabled(String provider) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {
                }
            });
            android.location.Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
            }
        }
    }
}