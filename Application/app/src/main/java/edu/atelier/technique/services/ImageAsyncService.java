package edu.atelier.technique.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import edu.atelier.technique.HomePage;

/**
 * Cette classe permet de charger une image via une url. Cette image sera converti en [ Bitmap ]
 */
public class ImageAsyncService {


    private final String urlImage;
    private Bitmap imageBitmap;


    /**
     * Constructeur
     * @param url
     */
    public ImageAsyncService(String url) {
        super();
        this.urlImage = url;
    }

    /**
     * Declenche la requète HTTP
     */
    public void doInBackGround() {
        try {
            URL url = new URL(urlImage);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.connect();

            int resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpConn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);

                imageBitmap = bitmap;
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * récupère la [ Bitmap ] trouvée
     * @return imageBitmap
     */
    public Bitmap getItemResult() { return imageBitmap; }
}