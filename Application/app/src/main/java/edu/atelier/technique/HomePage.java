package edu.atelier.technique;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.singletons.ListOfPublications;
import edu.atelier.technique.ui.Adapter.HomePageAdapter;
import edu.atelier.technique.models.PublicationModel;
import android.content.Intent;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import edu.atelier.technique.ui.Pages.InfoPage;
import edu.atelier.technique.ui.Pages.InterestPage;
import edu.atelier.technique.ui.Pages.Picpic_activity;
import edu.atelier.technique.useCase.GetAllImagesUseCase;
import edu.atelier.technique.useCase.GetAllImagesWithRadiusUseCase;
import edu.atelier.technique.useCase.GetImagesWithCityFiltersUseCase;
import edu.atelier.technique.useCase.GetImagesWithCountryFiltersUseCase;

public class HomePage extends AppCompatActivity {

    private ListView simpleList;
    private ArrayList<PublicationModel> publicationList = new ArrayList<PublicationModel>();
    private LinearLayout filterLayout;
    private Button filterButton;
    private SeekBar seekBar;
    private int radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        this.filterLayout = (LinearLayout) findViewById(R.id.filterLayout);
        this.filterButton = (Button) this.findViewById(R.id.details_filters);
        this.seekBar = (SeekBar) this.findViewById(R.id.seekBar);

        this.simpleList = (ListView) findViewById(R.id.homePageListView);

        GetAllImagesUseCase getImages = new GetAllImagesUseCase();
        Runnable runnable = ()->{
            getImages.doInBackGround();
            runOnUiThread( ()-> loadAdapter(getImages.itemList));
        };
        Executors.newSingleThreadExecutor().execute( runnable );

        setUpButton();
        setUpSeekBar();
    }

    private void loadAdapter(List<ImageModel> list){
        publicationList = new ArrayList<PublicationModel>();
        for(ImageModel element  : list){
            publicationList.add(new PublicationModel(element));
        }
        HomePageAdapter myAdapter = new HomePageAdapter(this.getApplicationContext(), R.layout.component_post, publicationList, this);
        simpleList.setAdapter(myAdapter);
    }

    private void searchFilters(){
        String textInput = ((TextView) this.findViewById(R.id.input_filters)).getText().toString();
        String choice = ((Spinner) this.findViewById(R.id.spinner)).getSelectedItem().toString();
        String radius = ((TextView) findViewById(R.id.km)).getText().toString();
        if (textInput.equals("") && Integer.parseInt(radius) == 0) {
            GetAllImagesUseCase getImages = new GetAllImagesUseCase();
            Runnable runnable = ()->{
                getImages.doInBackGround();
                runOnUiThread( ()-> loadAdapter(getImages.itemList));
            };
            Executors.newSingleThreadExecutor().execute( runnable );
        } else if (textInput.equals("") && Integer.parseInt(radius) != 0){
            GetAllImagesWithRadiusUseCase getImages = new GetAllImagesWithRadiusUseCase(this.radius);
            Runnable runnable = ()->{
                getImages.doInBackGround();
                runOnUiThread( ()-> loadAdapter(getImages.itemList));
            };
            Executors.newSingleThreadExecutor().execute( runnable );
        } else if (choice.equals(getString(R.string.ville))){
            GetImagesWithCityFiltersUseCase getImages = new GetImagesWithCityFiltersUseCase(textInput, this.radius);
            Runnable runnable = ()->{
                getImages.doInBackGround();
                runOnUiThread( ()-> loadAdapter(getImages.itemList));
            };
            Executors.newSingleThreadExecutor().execute( runnable );
        } else {
            GetImagesWithCountryFiltersUseCase getImages = new GetImagesWithCountryFiltersUseCase(textInput, this.radius);
            Runnable runnable = ()->{
                getImages.doInBackGround();
                runOnUiThread( ()-> loadAdapter(getImages.itemList));
            };
            Executors.newSingleThreadExecutor().execute( runnable );
        }
    }

    private void setUpButton(){
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

    private void setUpSeekBar(){
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                ((TextView) findViewById(R.id.km)).setText(String.valueOf(progress) + " km");
                radius = Integer.parseInt(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }


    private void setFiltersVisibility(){
        if(filterLayout.getVisibility() == View.VISIBLE){
            filterLayout.setVisibility(View.GONE);
            filterButton.setText(getString(R.string.addFiltres));
        }else{
            filterLayout.setVisibility(View.VISIBLE);
            filterButton.setText(getString(R.string.subFiltres));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}