package edu.atelier.technique.singletons;

/**
 * Cette classe permet de stocker toutes les données et les fonctions relatives aux permissions de l'application.
 * Elle est sous forme de singleton par conséquent, nous vous donnons un exemple d'appel.
 *
 * EXEMPLE D'APPEL :
 *  ListOfPermissions.getInstance().getCameraPermission();
 */
public class ListOfPermissions {


    private boolean cameraPermission;
    private boolean locationPermission;
    private boolean externalStoragePermission;
    private static ListOfPermissions INSTANCE;


    /**
     * récupère l'instance de ListOfPermissions
     * @return une instance existante ou une nouvelle
     */
    public static ListOfPermissions getInstance() {
        if (INSTANCE == null){ INSTANCE = new ListOfPermissions();}
        return INSTANCE;
    }

    /**
     * Constructeur
     */
    private ListOfPermissions(){
        cameraPermission = false;
        locationPermission = false;
        externalStoragePermission = false;
    }

    /**
     * récupère le statut de la permission de Camera
     * @return cameraPermission
     */
    public boolean getCameraPermission() { return cameraPermission; }

    /**
     * récupère le statut de la permission de Localisation
     * @return locationPermission
     */
    public boolean getlocationPermission() { return locationPermission; }

    /**
     * récupère le statut de la permission sur le Stockage Externe
     * @return externalStoragePermission
     */
    public boolean getExternalStoragePermission() { return externalStoragePermission; }

    /**
     * Modifie le statut de la permission de Camera
     * @param _boolean
     */
    public void setCameraPermission(boolean _boolean){ this.cameraPermission = _boolean; }

    /**
     * Modifie le statut de la permission de Localisation
     * @param _boolean
     */
    public void setlocalisationPermission(boolean _boolean){ this.locationPermission = _boolean; }

    /**
     * Modifie le statut de la permission sur le Stockage Externe
     * @param _boolean
     */
    public void setExternalStoragePermission(boolean _boolean){ this.externalStoragePermission = _boolean; }
}