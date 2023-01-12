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

public class ImageAsyncService {
    private String TAG = "Tag" + HomePage.class.getSimpleName();
    private String urlImage;
    private HttpHandler webService;
    private Bitmap imageBitmap;

    public ImageAsyncService(String url) {
        super();
        this.urlImage = url;
        webService = new HttpHandler();
    }

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getItemResult() {
        return imageBitmap;
    }

    class HttpHandler { //innerClass

        public String makeServiceCall(String reqUrl) {
            String response = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl).openConnection();
                connection.setRequestMethod("GET");
                // lecture du fichier
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                response = convertStreamToString(inputStream);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }

        //Conversion flux en String
        private String convertStreamToString(InputStream inputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                    Log.e(TAG,line);
                }
            }
            catch (IOException e) {  e.printStackTrace();   }
            finally {
                try { inputStream.close(); } catch (IOException e) { e.printStackTrace();  }
            }
            return stringBuilder.toString();
        }
    }
}