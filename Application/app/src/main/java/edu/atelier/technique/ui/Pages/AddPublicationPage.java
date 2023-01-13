package edu.atelier.technique.ui.Pages;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import edu.atelier.technique.HomePage;
import edu.atelier.technique.R;

public class AddPublicationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 100);
            Log.d("Camera Permission","Denied");
        }else{
            Log.d("Camera Permission","Granted");
        }

        getSupportActionBar().hide();

        this.findViewById(R.id.back).setOnClickListener(click -> {
            super.onBackPressed();
        });

        this.findViewById(R.id.container_advertissement).setOnClickListener( click -> {
            startActivity(new Intent(getApplicationContext(), InfoPage.class));
        });

        this.findViewById(R.id.YES).setOnClickListener( click -> {
            //TODO : ajouter la photo à la base de donnée
            startActivity(new Intent(getApplicationContext(), HomePage.class));
        });

        this.findViewById(R.id.NO).setOnClickListener( click -> {
            super.onBackPressed();
        });
    }
}