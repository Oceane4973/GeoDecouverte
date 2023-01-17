package edu.atelier.technique.ui.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import edu.atelier.technique.R;

public class ValidatePicPage extends AppCompatActivity {


    private ImageButton bValidate;
    private ImageButton bPicCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_pic);


        bValidate = findViewById(R.id.bValidate);
        bPicCancel = findViewById(R.id.bPicCancel);

        this.findViewById(R.id.bValidate).setOnClickListener(click -> {

            startActivity(new Intent(getApplicationContext(), AddPublicationPage.class));
        });

        this.findViewById(R.id.bPicCancel).setOnClickListener(click -> {

            startActivity(new Intent(getApplicationContext(), PicturePage.class));
        });
/*
        bValidate.setOnClickListener();
        bPicCancel.setOnClickListener();
*/
    }

/*
    public void validate(){
        this.findViewById(R.id.bValidate).setOnClickListener(click -> {

            startActivity(new Intent(getApplicationContext(), AddPublicationPage.class));
        });
    }


    public void cancel(){
        this.findViewById(R.id.bPicCancel).setOnClickListener(click -> {

            startActivity(new Intent(getApplicationContext(), PicturePage.class));
        });
    }*/
}
