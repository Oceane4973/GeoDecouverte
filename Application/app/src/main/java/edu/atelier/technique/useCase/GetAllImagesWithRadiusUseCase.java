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

/**
 * Cette classe permet de faire appel à notre service API [ APIService ] selon le cas d'utilisation :
 * "récupérer toutes les images existantes dans la base de données autour de l'utilisateur selon un rayon prédéfinis"
 *
 * EXEMPLE D'APPEL :
 *  GetAllImagesWithRadiusUseCase getImages = new GetAllImagesWithRadiusUseCase(radius);
 *  Runnable runnable = getImages::doInBackGround;
 *  Executors.newSingleThreadExecutor().execute( runnable );
 *
 */
public class GetAllImagesWithRadiusUseCase {


    private String TAG = "AtelierTechnique";
    private String url = "/images/radius_filter";
    public List<ImageModel> itemList;
    private int radius;
    private APIService webService;


    /**
     * Constructeur
     * @param radius
     */
    public GetAllImagesWithRadiusUseCase(int radius){
        super();
        this.webService = new APIService();
        this.radius = radius;
    }

    /**
     * Declenche la requête HTTP
     * @param latlng
     */
    public void doInBackGround(LatLng latlng) {
        String jsonStr = webService.makeServiceCall(this.url+ "/" + latlng.latitude + "/" + latlng.longitude + "/" + this.radius);

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
