package edu.atelier.technique.ui.Pages;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.atelier.technique.R;
import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.models.PublicationModel;
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

        publicationList.add(new PublicationModel(new ImageModel(1, "Nimes", "France",
                "https://www.okvoyage.com/wp-content/uploads/2020/10/nimes-france.jpeg", "12/01/2023")));
        publicationList.add(new PublicationModel(new ImageModel(2, "Nimes2", "France2",
                "https://www.okvoyage.com/wp-content/uploads/2020/10/nimes-france.jpeg", "12/01/2023")));
        publicationList.add(new PublicationModel(new ImageModel(3, "Nimes3", "France3",
                "https://www.okvoyage.com/wp-content/uploads/2020/10/nimes-france.jpeg", "12/01/2023")));

        InterestPageAdapter myAdapter = new InterestPageAdapter(this.getApplicationContext(), R.layout.component_grid_item, publicationList, this);
        simpleList.setAdapter(myAdapter);
    }
}