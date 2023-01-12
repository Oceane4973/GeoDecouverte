package edu.atelier.technique.models;

public class ImageModel {

    private int id;
    public String city, country, url, date;
    public int image;

    public ImageModel(int id, String city, String country, String url, String date) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.url = url;
        this.date = date;
    }

    public String toJsonString() {
        return (" { \"id\" : " + id + "\n" +
                " \"city\" : " + city + "\n" +
                " \"country\" : " + country + "\n" +
                " \"url\" : " + url + "\n" +
                " \"date\" : " + date + " } \n");
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

    public String gettDate() {
        return date;
    }
}
