package edu.atelier.technique.ui.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import edu.atelier.technique.R;
import edu.atelier.technique.models.PublicationModel;
import edu.atelier.technique.services.ImageAsyncService;
import edu.atelier.technique.singletons.ListOfPublications;

public class HomePageAdapter extends ArrayAdapter {

    ArrayList<PublicationModel> publicationList = new ArrayList<>();
    Activity activity;

    private static Drawable isSaved = null;
    private static Drawable isNotSaved = null;

    @SuppressLint("UseCompatLoadingForDrawables")
    public HomePageAdapter(Context context, int resource, ArrayList<PublicationModel> objects, Activity activity) {
        super(context, resource, objects);
        publicationList = objects;
        this.activity = activity;
        isSaved = activity.getApplicationContext().getDrawable(R.drawable.ic_bookmark_fill);
        isNotSaved = activity.getApplicationContext().getDrawable(R.drawable.ic_bookmark_outline);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.component_post_view, null);

        TextView city_name = (TextView) v.findViewById(R.id.city_name);
        TextView country_name = (TextView) v.findViewById(R.id.country_name);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        ImageView savedButton = (ImageView) v.findViewById(R.id.ic_save);

        city_name.setText(publicationList.get(position).getImage().getCity());
        country_name.setText(publicationList.get(position).getImage().getCountry());

        savedButton.setOnClickListener( click -> {
            if(savedButton.getDrawable() == isSaved){
                ListOfPublications.getInstance().addPublication(publicationList.get(position));
                savedButton.setImageDrawable(isNotSaved);
            } else {
                savedButton.setImageDrawable(isSaved);
                ListOfPublications.getInstance().subPublication(publicationList.get(position));
            }
        });

        ((ImageView) v.findViewById(R.id.ic_gps)).setImageResource(R.drawable.ic_gps_location);
        ((ImageView) v.findViewById(R.id.ic_save)).setImageResource(R.drawable.ic_bookmark_outline);
        ImageAsyncService getImage = new ImageAsyncService(publicationList.get(position).getImage().getUrl());
        Runnable runnable = () -> {
            getImage.doInBackGround();
            this.activity.runOnUiThread(() -> imageView.setImageBitmap(getImage.getItemResult()));
        };
        Executors.newSingleThreadExecutor().execute(runnable);

        return v;
    }

}
