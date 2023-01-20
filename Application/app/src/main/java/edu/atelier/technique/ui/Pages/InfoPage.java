package edu.atelier.technique.ui.Pages;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import edu.atelier.technique.R;


/**
 * Cette activité permet à l'utilisateur de consulter les conditions d'utilisations de notre service
 */
public class InfoPage extends AppCompatActivity {

    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

        this.findViewById(R.id.back).setOnClickListener(click -> {
            super.onBackPressed();
        });
    }
}