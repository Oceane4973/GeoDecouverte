package edu.atelier.technique.services;

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

public class APIService {

    private String TAG = "AtelierTechnique";
    private String URL = "https://api-atelier-technique.vercel.app";

    public APIService(){}

    public String makeServiceCall(String root) {
        String response = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(this.URL + root).openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            response = convertStreamToString(inputStream);
        } catch (MalformedURLException e) {
            Log.e(TAG,"MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
                Log.d(TAG,line);
            }
        }
        catch (IOException e) {  e.printStackTrace();   }
        finally {
            try { inputStream.close(); } catch (IOException e) { e.printStackTrace();  }
        }
        return stringBuilder.toString();
    }
}
