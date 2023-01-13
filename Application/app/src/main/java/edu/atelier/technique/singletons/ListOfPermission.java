package edu.atelier.technique.singletons;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

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

public class ListOfPermission {

    private boolean isCameraAuthorized;
    private ArrayList<Boolean> list;

    private static ListOfPermission INSTANCE;

    public static ListOfPermission getInstance() {
        if (INSTANCE == null){ INSTANCE = new ListOfPermission();}
        return INSTANCE;
    }

    private ListOfPermission(){
        isCameraAuthorized = false;
    }

    public ArrayList<Boolean> getList(){
        return this.list;
    }
}