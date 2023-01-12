package edu.atelier.technique;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import edu.atelier.technique.singletons.ListOfPublications;
import edu.atelier.technique.ui.InfoPage;
import edu.atelier.technique.ui.InterestPage;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);

        getSupportActionBar().hide();

        this.findViewById(R.id.imageButton2).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), InterestPage.class));
        });

        this.findViewById(R.id.imageButton1).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), InfoPage.class));
        });

        //ListOfPublications.getInstance().saveNewInstance(this);
        //ListOfPublications.getInstance().loadOldInstance(this);
    }
}