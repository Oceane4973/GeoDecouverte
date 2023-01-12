package edu.atelier.technique.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageModel {

    private int id;
    public String city, country, url, date;

    public ImageModel(int id, String city, String country, String url, String date) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.url = url;
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

    public String gettUrl() {
        return url;
    }

    public String gettDate() {
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
