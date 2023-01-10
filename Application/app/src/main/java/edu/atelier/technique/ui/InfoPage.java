package edu.atelier.technique.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.atelier.technique.HomePage;
import edu.atelier.technique.R;

public class InfoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

        getSupportActionBar().hide();

        this.findViewById(R.id.back).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), HomePage.class));
        });


    }
}