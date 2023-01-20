package edu.atelier.technique.singletons;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Cette classe permet de stocker toutes les données et les fonctions relatives à la récupération de la localisation de l'utilisateur
 * Elle est sous forme de singleton par conséquent, nous vous donnons un exemple d'appel.
 *
 * NOTE :
 * Certaines données ne sont pas accéssible depuis cette classe.
 * Pour son bon fonctionnement pensez donc à initisialiser le singleton dans la première activité avec : Location.getInstance().setUp(Activity activity, Thread callBack);
 *
 * EXEMPLE D'APPEL :
 *  Location.getInstance().getCurrentLocation();
 */
public class Location {


    private LatLng latLng;
    private boolean isSetUp;
    private boolean gpsEnabled = false;
    private static Location INSTANCE;


    /**
     * récupère l'instance de Location
     * @return une instance existante ou une nouvelle
     */
    public static Location getInstance() {
        if (INSTANCE == null){ INSTANCE = new Location();}
        return INSTANCE;
    }

    /**
     * Constructeur
     */
    private Location(){
        this.isSetUp = false;
    }

    /**
     * récupère la localisation current
     * @return LatLng si la classe à été initialisé sinon null
     */
    public LatLng getCurrentLocation(){
        if (! this.isSetUp) { return null; }
        return this.latLng;
    }

    /**
     * redirige l'utilisateur sur la page d'activation du GPS
     * @param activity
     */
    private void enableLocationSettings(Activity activity) {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivity(settingsIntent);
    }

    /**
     * vérifie que le GPS est activé
     * @param activity
     * @param callBack
     */
    public void setUp(Activity activity, Thread callBack) {
        Context context = activity.getApplicationContext();

        if(activity.getApplicationContext() == null ){ return; }

        String location_context = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) context.getSystemService(location_context);

        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            Runnable runnable = () -> {
                activity.runOnUiThread(() -> {
                    new AlertDialog.Builder(activity)
                            .setTitle("Localisation désactivée")
                            .setMessage("Afin d'améliorer votre experience utilisateur, notre service à nécéssairement besoin de vous géolocaliser.")
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                enableLocationSettings(activity);
                                gpsEnabled = true;
                                initCurrentLocation(locationManager, context, callBack);
                            })
                            .setNegativeButton(android.R.string.no, (dialog, which) -> setUp(activity, callBack))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                });
            };
            Executors.newSingleThreadExecutor().execute(runnable);
        } else { initCurrentLocation(locationManager, context, callBack); }
    }

    /**
     * initialise la variable LatLng
     * @param locationManager
     * @param context
     * @param callBack
     */
    private void initCurrentLocation(LocationManager locationManager, Context context, Thread callBack){
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
                public void onProviderDisabled(String provider) {}
                public void onProviderEnabled(String provider) {}
                public void onStatusChanged(String provider, int status, Bundle extras) {}
            });
            android.location.Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
            }
        }
        this.isSetUp = true;
        callBack.start();
    }
}