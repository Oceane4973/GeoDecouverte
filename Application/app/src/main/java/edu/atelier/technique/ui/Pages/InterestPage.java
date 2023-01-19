package edu.atelier.technique.ui.Pages;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.atelier.technique.R;
import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.models.PublicationModel;
import edu.atelier.technique.singletons.ListOfPublications;
import edu.atelier.technique.ui.Adapter.InterestPageAdapter;

public class InterestPage extends AppCompatActivity {

    private GridView simpleList;
    private ArrayList<PublicationModel> publicationList = new ArrayList<PublicationModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_page);

        this.findViewById(R.id.back).setOnClickListener(click -> {
            super.onBackPressed();
        });

        this.simpleList = (GridView) findViewById(R.id.grid);

        publicationList = ListOfPublications.getInstance().getList();
        if(publicationList == null ){
            Toast toast = Toast.makeText(this, "Un problème est survenu. Ce service est momentanément indisponible", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        InterestPageAdapter myAdapter = new InterestPageAdapter(this.getApplicationContext(), R.layout.component_grid_item, publicationList, this);
        simpleList.setAdapter(myAdapter);
    }
}