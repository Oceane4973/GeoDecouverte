package edu.atelier.technique;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import android.app.ListActivity;
import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.ui.Adapter.HomePageAdapter;
import edu.atelier.technique.models.PublicationModel;
import edu.atelier.technique.services.ImageAsyncService;
import android.content.Intent;
import android.os.Bundle;

import edu.atelier.technique.singletons.ListOfPublications;
import edu.atelier.technique.ui.InfoPage;
import edu.atelier.technique.ui.InterestPage;

public class HomePage extends AppCompatActivity {

    private ListView simpleList;
    private ArrayList<PublicationModel> publicationList = new ArrayList<PublicationModel>();
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_page_layout);

        executeInThread();
    }

    private void executeInThread() {
        ImageAsyncService getImage = new ImageAsyncService(
                "https://www.okvoyage.com/wp-content/uploads/2020/10/nimes-france.jpeg");

        Runnable runnable = () -> {
            getImage.doInBackGround();
            runOnUiThread(() -> onPostExecute(getImage.getItemResult()));
        };
        Executors.newSingleThreadExecutor().execute(runnable);
    }

    private void onPostExecute(Bitmap imageBitmap) {
        this.simpleList = (ListView) findViewById(R.id.homePageListView);
        // this.imageView.setImageBitmap(imageBitmap);

        publicationList.add(new PublicationModel(new ImageModel(1, "Nimes", "France",
                "https://www.okvoyage.com/wp-content/uploads/2020/10/nimes-france.jpeg", "12/01/2023")));
        publicationList.add(new PublicationModel(new ImageModel(2, "Nimes2", "France2",
                "https://www.okvoyage.com/wp-content/uploads/2020/10/nimes-france.jpeg", "12/01/2023")));
        publicationList.add(new PublicationModel(new ImageModel(3, "Nimes3", "France3",
                "https://www.okvoyage.com/wp-content/uploads/2020/10/nimes-france.jpeg", "12/01/2023")));

        HomePageAdapter myAdapter = new HomePageAdapter(this, R.layout.list_view_item, publicationList);
        simpleList.setAdapter(myAdapter);

        this.findViewById(R.id.imageButtonBookMark).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), InterestPage.class));
        });

        this.findViewById(R.id.imageButtonInformations).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), InfoPage.class));
        });


        ListOfPublications.getInstance().writeToFile(this);
        ListOfPublications.getInstance().readFromFile(this);
    }
}