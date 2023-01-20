package edu.atelier.technique.singletons;

import android.graphics.Bitmap;

public class ImagePostData {

    private String imageLink;
    private Bitmap img;

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

    public void setBitMap(Bitmap img){ this.img = img;}
    public Bitmap getBitMap(){ return this.img;}

    /*
    Uri imageUri = data.getData();
    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

     */
}
