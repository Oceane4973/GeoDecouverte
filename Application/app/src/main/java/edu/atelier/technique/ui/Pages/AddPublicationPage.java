package edu.atelier.technique.ui.Pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.atelier.technique.HomePage;
import edu.atelier.technique.R;

public class AddPublicationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);

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