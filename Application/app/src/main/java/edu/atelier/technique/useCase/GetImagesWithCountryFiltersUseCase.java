package edu.atelier.technique.useCase;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.services.APIService;

/** EXEMPLE D'APPEL :
private void executeInThread(){
     GetImagesWithCountryFiltersUseCase getImagesWithFilters = new GetImagesWithCountryFiltersUseCase(<Country>);
     Runnable runnable = getImagesWithFilters::doInBackGround;
     Executors.newSingleThreadExecutor().execute( runnable );
}
 **/

public class GetImagesWithCountryFiltersUseCase {

    private String TAG = "AtelierTechnique";
    private String countryFilters;
    private String url = "/images/country_filter";

    private APIService webService;

    public GetImagesWithCountryFiltersUseCase(String countryFilters){
        super();
        this.countryFilters = countryFilters;
        this.webService = new APIService();
    }

    public void doInBackGround() {
        String jsonStr = webService.makeServiceCall(this.url + "/" + countryFilters);
        Log.d(TAG, jsonStr);

        List<ImageModel> itemList = new ArrayList<ImageModel>();

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
                                    jsonObject.getString("url"),
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
