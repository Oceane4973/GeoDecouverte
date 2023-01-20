package edu.atelier.technique.models;

import org.json.JSONObject;

/**
 * Cette classe permet de stocker les données relative à une publication. Et elle fait référence à une instance de ImageModel.
 */
public class PublicationModel {


    private ImageModel image;
    private boolean isFavoris;


    /**
     * Constructeur
     * Création a partir d'un [ ImageModel ]
     * @param _image
     */
    public PublicationModel(ImageModel _image) {
        this.image = _image;
        this.isFavoris = false;
    }

    /**
     * Modifie l'état de mise en favoris
     * @param bool
     */
    public void setIsFavoris(boolean bool){
        this.isFavoris = bool;
    }

    /**
     * Récupère l'image [ ImageModel ] 
     * @return image
     */
    public ImageModel getImage() { return image; }

    /**
     * Récupère l'etat de mise en favoris'
     * @return isFavoris
     */
    public boolean isFavoris() { return isFavoris; }

    /**
     * Récupère le fichier JSON représentant les données de sa donnée membre ImageModel
     * @return JSONObject
     */
    public JSONObject toJson() { return image.toJson();}
}
