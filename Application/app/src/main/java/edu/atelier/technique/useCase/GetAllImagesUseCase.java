package edu.atelier.technique.useCase;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.services.APIService;

/**
 * Cette classe permet de faire appel à notre service API [ APIService ] selon le cas d'utilisation :
 * "récupérer toutes les images existantes dans la base de données"
 *
 * EXEMPLE D'APPEL :
 *  GetAllImagesUseCase getImages = new GetAllImagesUseCase();
 *  Runnable runnable = getImages::doInBackGround;
 *  Executors.newSingleThreadExecutor().execute( runnable );
 *
 */
public class GetAllImagesUseCase{


    private String TAG = "AtelierTechnique";
    private String url = "/images/all";
    public List<ImageModel> itemList;
    private APIService webService;


    /**
     * Constructeur
     */
    public GetAllImagesUseCase(){
        super();
        this.webService = new APIService();
    }

    /**
     * Declenche la requète HTTP
     */
    public void doInBackGround() {
        String jsonStr = webService.makeServiceCall(this.url);

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
    }
}
