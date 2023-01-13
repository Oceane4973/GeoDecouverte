package edu.atelier.technique.ui.Adapter;

import android.app.Activity;
import android.content.Context;
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

public class HomePageAdapter extends ArrayAdapter {

    ArrayList<PublicationModel> publicationList = new ArrayList<>();
    Activity activity;

    public HomePageAdapter(Context context, int resource, ArrayList<PublicationModel> objects, Activity activity) {
        super(context, resource, objects);
        publicationList = objects;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.component_post, null);

        TextView city_name = (TextView) v.findViewById(R.id.city_name);
        TextView country_name = (TextView) v.findViewById(R.id.country_name);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

        city_name.setText(publicationList.get(position).getImage().getCity());
        country_name.setText(publicationList.get(position).getImage().getCountry());


        ImageAsyncService getImage = new ImageAsyncService(publicationList.get(position).getImage().getUrl());

        Runnable runnable = () -> {
            getImage.doInBackGround();
            this.activity.runOnUiThread(() -> imageView.setImageBitmap(getImage.getItemResult()));
        };
        Executors.newSingleThreadExecutor().execute(runnable);

        return v;
    }

}
