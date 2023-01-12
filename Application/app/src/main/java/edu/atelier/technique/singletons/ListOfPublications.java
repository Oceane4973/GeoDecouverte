package edu.atelier.technique.singletons;

import java.util.ArrayList;

import edu.atelier.technique.models.PublicationModel;

/** EXEMPLE D'APPEL :
 ListOfPublications.getInstance().getCurrentLocation();
 */

public class ListOfPublications {

    private ArrayList<PublicationModel> list;

    private static ListOfPublications INSTANCE;

    public static ListOfPublications getInstance() {
        if (INSTANCE == null){ INSTANCE = new ListOfPublications();}
        return INSTANCE;
    }

    private ListOfPublications(){
        this.list = new ArrayList<PublicationModel>();
    }

    public void saveNewInstance(){
        //TODO : enregistrer dans le fichier ListOfPublications.json
    }

    public void loadOldInstance(){
        //TODO : enregistrer dans le fichier ListOfPublications.json
    }
}