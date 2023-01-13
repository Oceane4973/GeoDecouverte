package edu.atelier.technique.singletons;

/** EXEMPLE D'APPEL :
 ListOfPermissions.getInstance().getCameraPermission();
 */

public class ListOfPermissions {

    private boolean cameraPermission;
    private boolean locationPermission;
    private boolean externalStoragePermission;

    private static ListOfPermissions INSTANCE;

    public static ListOfPermissions getInstance() {
        if (INSTANCE == null){ INSTANCE = new ListOfPermissions();}
        return INSTANCE;
    }

    private ListOfPermissions(){
        cameraPermission = false;
        locationPermission = false;
        externalStoragePermission = false;
    }

    public boolean getCameraPermission() {
        return cameraPermission;
    }

    public boolean getlocationPermission() {
        return locationPermission;
    }

    public boolean getExternalStoragePermission() {
        return externalStoragePermission;
    }

    public void setCameraPermission(boolean _boolean){
        this.cameraPermission = _boolean;
    }

    public void setlocalisationPermission(boolean _boolean){
        this.locationPermission = _boolean;
    }

    public void setExternalStoragePermission(boolean _boolean){
        this.externalStoragePermission = _boolean;
    }
}