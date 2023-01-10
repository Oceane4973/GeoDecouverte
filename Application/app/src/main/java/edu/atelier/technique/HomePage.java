package edu.atelier.technique;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);

        ArrayList<String> listOfPublication = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomePage.this,android.R.layout.simple_list_item_1, listOfPublication);
        ((ListView)findViewById(R.id.listView)).setAdapter(adapter);
    }
}