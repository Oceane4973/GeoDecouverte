package edu.atelier.technique;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.ui.Adapter.HomePageAdapter;
import edu.atelier.technique.models.PublicationModel;

import android.content.Intent;

import edu.atelier.technique.ui.Pages.InfoPage;
import edu.atelier.technique.ui.Pages.InterestPage;

public class HomePage extends AppCompatActivity {

    private ListView simpleList;
    private ArrayList<PublicationModel> publicationList = new ArrayList<PublicationModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        this.simpleList = (ListView) findViewById(R.id.homePageListView);

        publicationList.add(new PublicationModel(new ImageModel(1, "Nimes", "France",
                "https://www.okvoyage.com/wp-content/uploads/2020/10/nimes-france.jpeg", "12/01/2023")));
        publicationList.add(new PublicationModel(new ImageModel(2, "Nimes2", "France2",
                "https://www.okvoyage.com/wp-content/uploads/2020/10/nimes-france.jpeg", "12/01/2023")));
        publicationList.add(new PublicationModel(new ImageModel(3, "Nimes3", "France3",
                "https://www.okvoyage.com/wp-content/uploads/2020/10/nimes-france.jpeg", "12/01/2023")));

        HomePageAdapter myAdapter = new HomePageAdapter(this.getApplicationContext(), R.layout.component_post, publicationList, this);
        simpleList.setAdapter(myAdapter);

        this.findViewById(R.id.imageButtonBookMark).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), InterestPage.class));
        });

        this.findViewById(R.id.imageButtonInformations).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), InfoPage.class));
        });

        // ListOfPublications.getInstance().saveNewInstance(this);
        // ListOfPublications.getInstance().loadOldInstance(this);
    }
}