package edu.atelier.technique.models;

import java.util.Date;

public class Publication {
    Integer id;
    ImageModel myImage;
    Boolean isFavoris;

    Publication(ImageModel _myImage){
        this.myImage = _myImage;
        this.isFavoris = false;
    }
}
