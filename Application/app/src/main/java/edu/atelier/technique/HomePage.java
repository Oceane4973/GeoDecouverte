package edu.atelier.technique;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.singletons.ListOfPermissions;
import edu.atelier.technique.singletons.ListOfPublications;
import edu.atelier.technique.ui.Adapter.HomePageAdapter;
import edu.atelier.technique.models.PublicationModel;
import android.content.Intent;

import edu.atelier.technique.ui.Pages.InfoPage;
import edu.atelier.technique.ui.Pages.InterestPage;
import edu.atelier.technique.ui.Pages.Picpic_activity;

public class HomePage extends AppCompatActivity {

    private static final int LOCATION_PERMISSION = 2000;
    private static final int EXTERNAL_STORAGE = 3000;
    private ListView simpleList;
    private ArrayList<PublicationModel> publicationList = new ArrayList<PublicationModel>();

    private LinearLayout filterLayout;
    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initiatePermissions();

        if(!ListOfPermissions.getInstance().getExternalStoragePermission()){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE);
        }else{
            if(!ListOfPermissions.getInstance().getlocationPermission()){
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);
            }
        }


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

    private void initiatePermissions(){
        ListOfPermissions.getInstance().setCameraPermission((ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED));
        ListOfPermissions.getInstance().setlocalisationPermission(
                (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        );
        ListOfPermissions.getInstance().setExternalStoragePermission(
                (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    ListOfPermissions.getInstance().setlocalisationPermission(true);
                } else {
                    ListOfPermissions.getInstance().setlocalisationPermission(false);
                }
                break;

            case EXTERNAL_STORAGE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    ListOfPermissions.getInstance().setExternalStoragePermission(true);
                } else {
                    ListOfPermissions.getInstance().setExternalStoragePermission(false);
                }
                if (!ListOfPermissions.getInstance().getlocationPermission()) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);
                }
                break;

            default:
                break;
        }
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
}