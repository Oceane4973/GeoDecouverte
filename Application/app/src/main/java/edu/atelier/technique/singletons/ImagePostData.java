package edu.atelier.technique.singletons;

public class ImagePostData {

    private String imageLink;

    private static ImagePostData INSTANCE;

    public static ImagePostData getInstance() {
        if (INSTANCE == null){ INSTANCE = new ImagePostData();}
        return INSTANCE;
    }

    public void setImageLink(String link){
        this.imageLink=link;
    }

    public String getImageLink(){
        return this.imageLink;
    }
}
