package edu.atelier.technique.singletons;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.models.PublicationModel;

/**
 * Cette classe permet de stocker toutes les données et les fonctions relatives au fichier JSON écrit et lu dans le stackage externe.
 * Ce fichier contient nottament toutes les informations sur les [ ImageModel ]  enregistrées.
 * Elle est sous forme de singleton par conséquent, nous vous donnons un exemple d'appel.
 *
 * NOTE :
 * Certaines données ne sont pas accéssible depuis cette classe.
 * Pour son bon fonctionnement pensez donc à initisialiser le singleton dans la première activité avec : ListOfPublications.getInstance().setUp(this);
 *
 * EXEMPLE D'APPEL :
 * ListOfPublications.getInstance().writeToFile();
 * ListOfPublications.getInstance().readFromFile();
 * ListOfPublications.getInstance().publicationIsSaved(publicationModel);
 */
public class ListOfPublications {


    private String FILE_NAME = "LIST_OF_PUBLICATIONS.json";
    private ArrayList<PublicationModel> list;
    private Context context;
    private static ListOfPublications INSTANCE;


    /**
     * récupère l'instance de ListOfPublications
     * @return une instance existante ou une nouvelle
     */
    public static ListOfPublications getInstance() {
        if (INSTANCE == null){ INSTANCE = new ListOfPublications();}
        return INSTANCE;
    }

    /**
     * permet l'initialisation de la variable LatLng
     * @param context
     */
    public void setUp(Context context){
        this.context = context;
        this.readFromFile();
        //this.writeToFile();
    }

    /**
     * Constructeur
     */
    private ListOfPublications(){ this.list = new ArrayList<PublicationModel>();}

    /**
     * Répercute une demande de modification de statut de sauvegarde par l'utilisateur sur le fichier JSON stocké
     * @param publication
     * @return boolean
     */
    public boolean addOrDeletePublication(PublicationModel publication){
        if(publication.isFavoris()){ this.list.remove(publication);
        }else{ this.list.add(publication); }
        writeToFile();
        return !publication.isFavoris();
    }

    /**
     * récupère l'etat de sauvegarde d'une [ PublicationImage ]
     * @param publicationModel
     * @return boolean
     */
    public boolean publicationIsSaved(PublicationModel publicationModel){
        for(PublicationModel publication : this.list){
            if (publication.getImage().getId() == publicationModel.getImage().getId()){
                return true;
            }
        }
        return false;
    }

    /**
     * récupère toute la liste des [ PublicationImage ] sauvegardés
     * @return list
     */
    public ArrayList<PublicationModel> getList(){ return this.list; }

    /**
     * converti toute la liste des [ PublicationImage ] sauvegardés au format JSON
     * @return JSONArray
     */
    private JSONArray JSONList(){
        JSONArray json = new JSONArray();
        for(PublicationModel publication : this.list) {
            json.put(publication.toJson());
        }
        return json;
    }

    /**
     * met à jour et écrit dans le fichier JSON
     */
    public void writeToFile() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(JSONList().toString());
            outputStreamWriter.close();
            Log.d("AtelierTechnique", "ecriture : " + JSONList().toString());
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * lit le fichier JSON
     */
    private void readFromFile() {
        this.list = new ArrayList<PublicationModel>();
        JSONArray publications = null;
        try {
            InputStream inputStream = context.openFileInput(FILE_NAME);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                publications = new JSONArray(stringBuilder.toString());
                for (int i = 0; i < publications.length(); i++) {
                    JSONObject publication = publications.getJSONObject(i);
                    this.list.add(
                            new PublicationModel(
                                    new ImageModel(
                                        Integer.parseInt(publication.getString("id")),
                                        publication.getString("city"),
                                        publication.getString("country"),
                                        publication.getString("filename"),
                                        publication.getString("date")
                                    )
                            )
                    );
                }
                Log.d("AtelierTechnique", "lecture : " + JSONList().toString());
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}