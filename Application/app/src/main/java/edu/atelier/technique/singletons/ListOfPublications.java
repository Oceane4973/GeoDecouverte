package edu.atelier.technique.singletons;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.models.PublicationModel;

/** EXEMPLE D'APPEL :
 ListOfPublications.getInstance().getList();
*/

public class ListOfPublications {

    private String FILE_NAME = "LIST_OF_PUBLICATIONS.json";
    private ArrayList<PublicationModel> list;

    private static ListOfPublications INSTANCE;

    public static ListOfPublications getInstance() {
        if (INSTANCE == null){ INSTANCE = new ListOfPublications();}
        return INSTANCE;
    }

    private ListOfPublications(){
        this.list = new ArrayList<PublicationModel>();
        this.list.add(
                new PublicationModel(
                        new ImageModel(
                                1,
                                "city",
                                "country",
                                "url",
                                "date"
                        )
                )
        );
    }

    public ArrayList<PublicationModel> getList(){
        return this.list;
    }

    private String toJsonString(){
        String jsonString = "[";
        for(PublicationModel publication : this.list){
            jsonString+= publication.toJsonString() + ",";
        }
        return jsonString + "]";
    }

    public void saveNewInstance(Context context){
        FileOutputStream fos  = null;

        String JsonString = "hey";
        try {
            context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(JsonString.getBytes(), 0, JsonString.length());
            Log.d("AtelierTechnique","fichier sauvé : " + JsonString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadOldInstance(Context context){
        FileInputStream fis  = null;
        try {
            fis = context.openFileInput(FILE_NAME);
            InputStreamReader json = new InputStreamReader(fis);
            this.list = new ArrayList<PublicationModel>();
            try {
                JSONArray publications = new JSONArray(json);

                for( int i = 0; i < publications.length(); i++){
                    JSONObject publication = publications.getJSONObject(i);
                    this.list.add(
                            new PublicationModel(new ImageModel(
                            Integer.parseInt(publication.getString("id")),
                            publication.getString("city"),
                            publication.getString("country"),
                            publication.getString("url"),
                            publication.getString("date")
                    )));
                }
                Log.d("AtelierTechnique","fichier chargé : " + this.toJsonString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}