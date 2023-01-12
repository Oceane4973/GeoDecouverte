package edu.atelier.technique.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.atelier.technique.HomePage;
import edu.atelier.technique.R;

public class InterestPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved);

        getSupportActionBar().hide();

        this.findViewById(R.id.back).setOnClickListener(click -> {
            super.onBackPressed();
        });
    }
}