package edu.atelier.technique.ui.Pages;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import edu.atelier.technique.R;

public class InfoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

        this.findViewById(R.id.back).setOnClickListener(click -> {
            super.onBackPressed();
        });
    }
}