package edu.atelier.technique;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;

import edu.atelier.technique.models.Location;
import edu.atelier.technique.models.MyAdapter;
import edu.atelier.technique.models.Publication;

public class HomePage extends AppCompatActivity {

    ListView simpleList;
    ArrayList<Publication> listOfPublication = new ArrayList<Publication>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);

        simpleList = (ListView) findViewById(R.id.listView);

        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_view_item, listOfPublication);
    }
}