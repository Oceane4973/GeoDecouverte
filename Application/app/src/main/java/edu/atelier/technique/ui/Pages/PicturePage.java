package edu.atelier.technique.ui.Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import edu.atelier.technique.R;
import edu.atelier.technique.singletons.ImagePostData;
import edu.atelier.technique.HomePage;
import edu.atelier.technique.singletons.ListOfPermissions;

public class PicturePage extends AppCompatActivity implements ImageAnalysis.Analyzer, View.OnClickListener {

    private static final int CAMERA_PERMISSION = 1000;
    private static final int LOCATION_PERMISSION = 2000;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    PreviewView previewView;
    private ImageCapture imageCapture;
    // private VideoCapture videoCapture;

    private ImageButton bCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picpic);

        if (!ListOfPermissions.getInstance().getCameraPermission()) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, CAMERA_PERMISSION);
        } else {
            if (!ListOfPermissions.getInstance().getlocationPermission()) {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                        LOCATION_PERMISSION);
            }
        }

        previewView = findViewById(R.id.previewView);
        bCapture = findViewById(R.id.bPicCapture);

        bCapture.setOnClickListener(this);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());
    }

    private Bitmap toBitmap(Image image) {
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer yBuffer = planes[0].getBuffer();
        ByteBuffer uBuffer = planes[1].getBuffer();
        ByteBuffer vBuffer = planes[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uSize + vSize];
        //U and V are swapped
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out);

        byte[] imageBytes = out.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }


    Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    @SuppressLint("RestrictedApi")
    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        Preview preview = new Preview.Builder()
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // Image capture use case
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        // Image analysis use case
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(getExecutor(), this);

        // bind to lifecycle:
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture);
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        // image processing here for the current frame
        image.close();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bPicCapture:
                capturePhoto();
                break;
        }
    }

/*
    bCapture.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            imageCapture.takePicture(new ImageCapture.OnImageCapturedListener()
            {
                @Override
                public void onCaptureSuccess(ImageProxy image, int rotationDegrees)
                {
                    ImageView imageView = findViewById(R.id.imageView);
                    imageView.setImageBitmap(imageProxyToBitmap(image));
                    imageView.setRotation(rotationDegrees);

                    image.close();
                }
            });
        }
    });*/
    private void capturePhoto() {
        long timestamp = System.currentTimeMillis();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");


        Log.d("===========================================","====================");
        Log.d("content resolv", getContentResolver().toString());
        Log.d("media store", MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
        Log.d("content values all", contentValues.toString());
        Log.d("content values disp name", contentValues.get("_display_name").toString());
        Log.d("===========================================","====================");

        imageCapture.takePicture(

                new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {

                    public void onCaptureSuccess(ImageProxy image, int rotationDegrees)
                    {

                        Log.d("aaaa","on passe par onCaptureSuccess");
                        ImagePostData.getInstance().setBitMap(toBitmap((Image) image));



                    }











                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        //ImagePostData.getInstance().setBitMap(toBitmap((Image) ImageCapture.));
                        Toast.makeText(PicturePage.this, "Pic saved at "+
                                "Pictures/"+contentValues.get("_display_name").toString(),
                                Toast.LENGTH_LONG).show();

                        /*
                        Uri imageUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                         */
                        ImagePostData.getInstance().setImageLink("Pictures/"+contentValues.get("_display_name").toString());

                        /*
                        Uri imageUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        ImagePostData.getInstance().setBitMap(bitmap);
                        */

                        //goToValidate();
                        startActivity(new Intent(getApplicationContext(), ValidatePicPage.class));
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(PicturePage.this, "Error saving photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

/*
    public void goToValidate(){
        this.findViewById(R.id.imageButtonAddPublication).setOnClickListener(click -> {

            startActivity(new Intent(getApplicationContext(), ValidatePicPage.class));
        });
    }
*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    ListOfPermissions.getInstance().setCameraPermission(true);
                    if (!ListOfPermissions.getInstance().getlocationPermission()) {
                        ActivityCompat.requestPermissions(this,
                                new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, LOCATION_PERMISSION);
                    }
                } else {
                    ListOfPermissions.getInstance().setCameraPermission(false);
                    startActivity(new Intent(getApplicationContext(), HomePage.class));
                }
                break;

            case LOCATION_PERMISSION:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    ListOfPermissions.getInstance().setlocalisationPermission(true);
                } else {
                    ListOfPermissions.getInstance().setlocalisationPermission(false);
                }
                break;

            default:
                break;
        }
    }
}