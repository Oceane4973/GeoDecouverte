package edu.atelier.technique.models;

import android.graphics.Bitmap;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Cette classe permet de stocker les données relative à une image. Elle est le modèle qui représsente les données recues de l'API Rest
 */
public class ImageModel {


    private int id;
    public String city, country, url, date, filename;
    public Bitmap bitmap;


    /**
     * Constructeur
     * @param id
     * @param city
     * @param country
     * @param filename
     * @param date
     */
    public ImageModel(int id, String city, String country, String filename, String date) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.filename = filename;
        //this.url est crée dynamiquement lors de la création de l'ImageModel
        this.url = "https://api-2-atelier-technique-geodecouverte.vercel.app/image/" + filename + ".png";
        this.date = date;
    }

    /**
     * Récupère l'id
     * @return id
     */
    public int getId() { return id; }

    /**
     * Récupère la ville
     * @return city
     */
    public String getCity() { return city; }

    /**
     * Récupère le pays
     * @return country
     */
    public String getCountry() { return country; }

    /**
     * Récupère l'url de l'image pour récupérer le [ Bitmap ]
     * @return url
     */
    public String getUrl() { return url; }

    /**
     * Convertit les données d'un [ImageModel] au format JSON
     * @return JSONObject
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", this.id);
            json.put("city", this.city);
            json.put("country", this.country);
            json.put("filename", this.filename);
            json.put("date", this.date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}

