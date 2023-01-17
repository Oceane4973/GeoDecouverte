package edu.atelier.technique.models;

import android.Manifest;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageModel {

    private int id;
    public String city, country, url, date;
    public int image;

    public ImageModel(int id, String city, String country, String filename, String date) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.url = "https://api-2-atelier-technique-geodecouverte.vercel.app/image/" + filename + ".png";
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", this.id);
            json.put("city", this.city);
            json.put("country", this.country);
            json.put("url", this.url);
            json.put("date", this.date);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
