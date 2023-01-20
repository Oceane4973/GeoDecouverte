package edu.atelier.technique.ui.Pages;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import edu.atelier.technique.R;
import edu.atelier.technique.singletons.ListOfPermissions;

/**
 * Cette activity permet à l'utilisateur de changé ses permissions
 */
public class SettingsPage extends AppCompatActivity {


    private static final int CAMERA_PERMISSION = 1000;
    private static final int LOCATION_PERMISSION = 2000;
    private static final int EXTERNAL_STORAGE = 3000;

    private SwitchCompat CameraSwitch;
    private SwitchCompat LocationSwitch;
    private SwitchCompat ExternalStorageSwitch;


    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        this.findViewById(R.id.back).setOnClickListener(click -> {
            super.onBackPressed();
        });

        CameraSwitch = (SwitchCompat) findViewById(R.id.CameraSwitch);
        CameraSwitch.setChecked(ListOfPermissions.getInstance().getCameraPermission());

        LocationSwitch = (SwitchCompat) findViewById(R.id.LocationSwitch);
        LocationSwitch.setChecked(ListOfPermissions.getInstance().getlocationPermission());

        ExternalStorageSwitch = (SwitchCompat) findViewById(R.id.ExternalStorageSwitch);
        ExternalStorageSwitch.setChecked(ListOfPermissions.getInstance().getExternalStoragePermission());

        this.findViewById(R.id.CameraSwitch).setOnClickListener(click -> {
            if(!ListOfPermissions.getInstance().getCameraPermission()){
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            }else{
                CameraSwitch.setChecked(true);
            }
        });
        this.findViewById(R.id.LocationSwitch).setOnClickListener(click -> {
            if(!ListOfPermissions.getInstance().getlocationPermission()){
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);
            }else{
                LocationSwitch.setChecked(true);
            }
        });
        this.findViewById(R.id.ExternalStorageSwitch).setOnClickListener(click -> {
            if(!ListOfPermissions.getInstance().getExternalStoragePermission()){
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE);
            }else{
                ExternalStorageSwitch.setChecked(true);
            }
        });
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
            case CAMERA_PERMISSION:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    ListOfPermissions.getInstance().setCameraPermission(true);
                }else{
                    ListOfPermissions.getInstance().setCameraPermission(false);
                    CameraSwitch.setChecked(ListOfPermissions.getInstance().getCameraPermission());
                }
                break;

            case LOCATION_PERMISSION:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    ListOfPermissions.getInstance().setlocalisationPermission(true);
                } else {
                    ListOfPermissions.getInstance().setlocalisationPermission(false);
                    LocationSwitch.setChecked(ListOfPermissions.getInstance().getlocationPermission());
                }
                break;

            case EXTERNAL_STORAGE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    ListOfPermissions.getInstance().setExternalStoragePermission(true);
                } else {
                    ListOfPermissions.getInstance().setExternalStoragePermission(false);
                    ExternalStorageSwitch.setChecked(ListOfPermissions.getInstance().getExternalStoragePermission());
                }
                break;

            default:
                break;
        }
    }
}