package edu.atelier.technique.models;

import org.json.JSONObject;

public class PublicationModel {
    private int id;
    private ImageModel myImage;
    private boolean isFavoris;

    public PublicationModel(ImageModel _myImage) {
        this.myImage = _myImage;
        this.isFavoris = false;
    }

    public ImageModel getImage() {
        return myImage;
    }

    public boolean isFavoris() {
        return isFavoris;
    }

    public JSONObject toJson() {
        return myImage.toJson();
    }
}
