package edu.atelier.technique.ui.Pages;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

import edu.atelier.technique.R;
import edu.atelier.technique.models.ImageModel;
import edu.atelier.technique.models.PublicationModel;
import edu.atelier.technique.singletons.ListOfPermissions;
import edu.atelier.technique.singletons.ListOfPublications;
import edu.atelier.technique.ui.Adapter.InterestPageAdapter;

public class InterestPage extends AppCompatActivity {
    private static final int EXTERNAL_STORAGE = 3000;
    private GridView simpleList;
    private ArrayList<PublicationModel> publicationList = new ArrayList<PublicationModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_page);

        if (!ListOfPermissions.getInstance().getlocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    EXTERNAL_STORAGE);
        }

        this.findViewById(R.id.back).setOnClickListener(click -> {
            super.onBackPressed();
        });

        this.simpleList = (GridView) findViewById(R.id.grid);

        publicationList = ListOfPublications.getInstance().getList();
        if (publicationList == null) {
            Toast toast = Toast.makeText(this, "Un problème est survenu. Ce service est momentanément indisponible",
                    Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        InterestPageAdapter myAdapter = new InterestPageAdapter(this.getApplicationContext(),
                R.layout.component_grid_item, publicationList, this);
        simpleList.setAdapter(myAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case EXTERNAL_STORAGE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    ListOfPermissions.getInstance().setExternalStoragePermission(true);
                } else {
                    ListOfPermissions.getInstance().setExternalStoragePermission(false);
                }
                break;

            default:
                break;
        }
    }
}