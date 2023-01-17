package edu.atelier.technique.useCase;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.services.APIService;
import edu.atelier.technique.singletons.Location;

/** EXEMPLE D'APPEL :
private void executeInThread(){
       GetAllImagesUseCase getImages = new GetAllImagesUseCase();
       Runnable runnable = getImages::doInBackGround;
       Executors.newSingleThreadExecutor().execute( runnable );
}
 **/

public class GetAllImagesWithRadiusUseCase {

    private String TAG = "AtelierTechnique";
    private String url = "/images/radius_filter";
    public List<ImageModel> itemList;
    private int radius;

    private APIService webService;

    public GetAllImagesWithRadiusUseCase(int radius){
        super();
        this.webService = new APIService();
        this.radius = radius;
    }

    public void doInBackGround() {
        LatLng latlng = Location.getInstance().getCurrentLocation();
        String jsonStr = webService.makeServiceCall(this.url+ "/" + latlng.latitude + "/" + latlng.longitude + "/" + this.radius);
        Log.d(TAG, jsonStr);

        this.itemList = new ArrayList<ImageModel>();

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray images = jsonObj.getJSONArray("images");
                for (int i = 0; i < images.length(); i++) {
                    JSONObject jsonObject = images.getJSONObject(i);
                    itemList.add(
                            (ImageModel) new ImageModel(
                                    Integer.parseInt(jsonObject.getString("id")),
                                    jsonObject.getString("city"),
                                    jsonObject.getString("country"),
                                    jsonObject.getString("filename"),
                                    jsonObject.getString("date")
                            )
                    );
                }
            } catch (final JSONException e) {
                Log.d(TAG, "Erreur converssion JSON " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Problème de connexion ");
        }
        Log.d(TAG, "images =" + itemList);
    }
}
