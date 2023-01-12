package edu.atelier.technique.models;

public class ImageModel {

    private int id;
    public String city, country, url, date;

    public ImageModel(int id, String city, String country, String url, String date){
        this.id = id;
        this.city = city;
        this.country = country;
        this.url = url;
        this.date = date;
    }

    public int getId(){
        return id;
    }

    public String getCity(){
        return city;
    }

    public String getCountry(){
        return country;
    }

    public String gettUrl(){
        return url;
    }

    public String gettDate(){
        return date;
    }
}
