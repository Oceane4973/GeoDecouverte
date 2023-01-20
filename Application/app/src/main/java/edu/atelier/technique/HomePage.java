package edu.atelier.technique;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.singletons.ListOfPermissions;
import edu.atelier.technique.singletons.ListOfPublications;
import edu.atelier.technique.singletons.Location;
import edu.atelier.technique.ui.Adapter.HomePageAdapter;
import edu.atelier.technique.models.PublicationModel;
import android.content.Intent;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import edu.atelier.technique.ui.Pages.InfoPage;
import edu.atelier.technique.ui.Pages.InterestPage;
import edu.atelier.technique.ui.Pages.Picpic_activity;
import edu.atelier.technique.useCase.GetAllImagesUseCase;
import edu.atelier.technique.useCase.GetAllImagesWithRadiusUseCase;
import edu.atelier.technique.useCase.GetImagesWithCityFiltersUseCase;
import edu.atelier.technique.useCase.GetImagesWithCountryFiltersUseCase;
import edu.atelier.technique.ui.Pages.SettingsPage;

/**
 * Cette activité est l'activité principal de l'application.
 * Elle permet à l'utilisteur de voir les publications disponibles et de les filtres selon ces besoins
 */
public class HomePage extends AppCompatActivity {


    private static final int LOCATION_PERMISSION = 2000;
    private static final int EXTERNAL_STORAGE = 3000;
    private ListView simpleList;
    private ArrayList<PublicationModel> publicationList = new ArrayList<PublicationModel>();
    private LinearLayout filterLayout;
    private Button filterButton;
    private SeekBar seekBar;
    private int radius;


    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initiatePermissions();

        if (!ListOfPermissions.getInstance().getExternalStoragePermission()) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    EXTERNAL_STORAGE);
        } else {
            if (!ListOfPermissions.getInstance().getlocationPermission()) {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                        LOCATION_PERMISSION);
            }
        }

        Location.getInstance().setUp(this,  new Thread(() -> searchFilters()));
        ListOfPublications.getInstance().setUp(this);

        this.filterLayout = (LinearLayout) findViewById(R.id.filterLayout);
        this.filterButton = (Button) this.findViewById(R.id.details_filters);
        this.seekBar = (SeekBar) this.findViewById(R.id.seekBar);
        this.simpleList = (ListView) findViewById(R.id.homePageListView);

        setUpButton();
        setUpSeekBar();
    }

    /**
     * appel l'adapter [ HomePageAdapter ] pour visualiser les publications disponibles
     * @param list
     */
    private void loadAdapter(List<ImageModel> list) {
        publicationList = new ArrayList<PublicationModel>();
        for (ImageModel element : list) {
            publicationList.add(new PublicationModel(element));
        }
        HomePageAdapter myAdapter = new HomePageAdapter(this.getApplicationContext(), R.layout.component_post_add,
                publicationList, this);
        simpleList.setAdapter(myAdapter);
    }

    /**
     * met à jour les données des publications visibles par rapport aux filtres
     */
    private void searchFilters() {
        String textInput = ((TextView) this.findViewById(R.id.input_filters)).getText().toString();
        String choice = ((Spinner) this.findViewById(R.id.spinner)).getSelectedItem().toString();
        String radius = ((TextView) findViewById(R.id.km)).getText().toString().split(" ")[0];
        if (textInput.equals("") && Integer.parseInt(radius) == 0) {
            GetAllImagesUseCase getImages = new GetAllImagesUseCase();
            Runnable runnable = () -> {
                getImages.doInBackGround();
                runOnUiThread(() -> loadAdapter(getImages.itemList));
            };
            Executors.newSingleThreadExecutor().execute(runnable);
        } else if (textInput.equals("") && Integer.parseInt(radius) != 0) {
            GetAllImagesWithRadiusUseCase getImages = new GetAllImagesWithRadiusUseCase(this.radius);
            LatLng latlng = Location.getInstance().getCurrentLocation();
            if (latlng == null) {
                Toast toast = Toast.makeText(this, R.string.LocationNotFoundText, Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            Runnable runnable = () -> {
                getImages.doInBackGround(latlng);
                runOnUiThread(() -> loadAdapter(getImages.itemList));
            };
            Executors.newSingleThreadExecutor().execute(runnable);
        } else if (choice.equals(getString(R.string.ville))) {
            GetImagesWithCityFiltersUseCase getImages = new GetImagesWithCityFiltersUseCase(textInput, this.radius);
            Runnable runnable = () -> {
                getImages.doInBackGround();
                runOnUiThread(() -> loadAdapter(getImages.itemList));
            };
            Executors.newSingleThreadExecutor().execute(runnable);
        } else {
            GetImagesWithCountryFiltersUseCase getImages = new GetImagesWithCountryFiltersUseCase(textInput,
                    this.radius);
            Runnable runnable = () -> {
                getImages.doInBackGround();
                runOnUiThread(() -> loadAdapter(getImages.itemList));
            };
            Executors.newSingleThreadExecutor().execute(runnable);
        }
    }

    /**
     * initialise le comportement des éléments dynamiques de la page
     */
    private void setUpButton() {
        this.findViewById(R.id.imageButtonBookMark).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), InterestPage.class));
        });
        this.findViewById(R.id.imageButtonInformations).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), InfoPage.class));
        });

        this.findViewById(R.id.imageButtonSettings).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), SettingsPage.class));
        });

        this.filterButton.setOnClickListener(click -> {
            setFiltersVisibility();
        });
        this.findViewById(R.id.dontsearch).setOnClickListener(click -> {
            ((TextView) this.findViewById(R.id.input_filters)).setText("");
            ((TextView) findViewById(R.id.km)).setText(R.string.kilometres);
            searchFilters();
            setFiltersVisibility();
        });
        this.findViewById(R.id.search).setOnClickListener(click -> {
            searchFilters();
            setFiltersVisibility();
        });
        this.findViewById(R.id.imageButtonInformations).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), InfoPage.class));
        });
        this.findViewById(R.id.imageButtonAddPublication).setOnClickListener(click -> {
            startActivity(new Intent(getApplicationContext(), Picpic_activity.class));
        });
    }

    /**
     * initialise le comportement de la seekBar
     */
    private void setUpSeekBar() {
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView) findViewById(R.id.km)).setText(progress + " km");
                radius = Integer.parseInt(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    /**
     * initialise [ ListOfPermissions ]
     */
    private void initiatePermissions() {
        ListOfPermissions.getInstance()
                .setCameraPermission((ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED));
        ListOfPermissions.getInstance().setlocalisationPermission(
                (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED));
        ListOfPermissions.getInstance().setExternalStoragePermission(
                (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));
    }

    /**
     * onRequestPermissionsResult
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
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
                    ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                            LOCATION_PERMISSION);
                }
                break;

            default:
                break;
        }
    }

    /**
     * Modifie la visibilité du filtre
     */
    private void setFiltersVisibility() {
        if (filterLayout.getVisibility() == View.VISIBLE) {
            filterLayout.setVisibility(View.GONE);
            filterButton.setText(getString(R.string.addFiltres));
        } else {
            filterLayout.setVisibility(View.VISIBLE);
            filterButton.setText(getString(R.string.subFiltres));
        }
    }
}