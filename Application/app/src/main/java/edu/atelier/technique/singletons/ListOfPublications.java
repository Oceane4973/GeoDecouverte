package edu.atelier.technique.singletons;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.NavigableMap;

import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.models.PublicationModel;
import edu.atelier.technique.notifications.Notifications;

/** EXEMPLE D'APPEL :
 *  ListOfPublications.getInstance().writeToFile(this);
 *  ListOfPublications.getInstance().readFromFile(this);
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

    private JSONArray JSONList(){
        JSONArray json = new JSONArray();
        for(PublicationModel publication : this.list) {
            json.put(publication.toJson());
        }
        return json;
    }

    public void writeToFile(Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(JSONList().toString());
            outputStreamWriter.close();
            Log.d("AtelierTechnique","Fichier crée : " + JSONList().toString());
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public JSONArray readFromFile(Context context) {

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
                            new PublicationModel(new ImageModel(
                                    Integer.parseInt(publication.getString("id")),
                                    publication.getString("city"),
                                    publication.getString("country"),
                                    publication.getString("url"),
                                    publication.getString("date")
                            )));
                }
                Log.d("AtelierTechnique", "fichier chargé (JSON): " + this.JSONList());
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
        return publications;
    }
}