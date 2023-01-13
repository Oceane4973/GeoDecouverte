package edu.atelier.technique;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.singletons.ListOfPublications;
import edu.atelier.technique.ui.Adapter.HomePageAdapter;
import edu.atelier.technique.models.PublicationModel;
import android.content.Intent;

import edu.atelier.technique.ui.Pages.InfoPage;
import edu.atelier.technique.ui.Pages.InterestPage;
import edu.atelier.technique.ui.Pages.Picpic_activity;

public class HomePage extends AppCompatActivity {

    private ListView simpleList;
    private ArrayList<PublicationModel> publicationList = new ArrayList<PublicationModel>();

    private LinearLayout filterLayout;
    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        this.filterLayout = (LinearLayout) findViewById(R.id.filterLayout);
        this.filterButton = (Button) this.findViewById(R.id.details_filters);

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

        
        this.filterButton.setOnClickListener( click -> {
            setFiltersVisibility();
        });

        this.findViewById(R.id.dontsearch).setOnClickListener( click -> {
            setFiltersVisibility();
        });

        this.findViewById(R.id.search).setOnClickListener( click -> {
            setFiltersVisibility();
        });

        this.findViewById(R.id.imageButtonInformations).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), InfoPage.class));
        });


        this.findViewById(R.id.imageButtonAddPublication).setOnClickListener(click -> {

            startActivity(new Intent(getApplicationContext(), Picpic_activity.class));
        });
        
        ListOfPublications.getInstance().writeToFile(this);
        ListOfPublications.getInstance().readFromFile(this);
    }

    private void setFiltersVisibility(){
        if(filterLayout.getVisibility() == View.VISIBLE){
            filterLayout.setVisibility(View.GONE);
            filterButton.setText("+ Filtres");
        }else{
            filterLayout.setVisibility(View.VISIBLE);
            filterButton.setText("- Filtres");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }
}