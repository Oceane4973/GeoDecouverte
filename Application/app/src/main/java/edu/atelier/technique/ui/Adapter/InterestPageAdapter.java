package edu.atelier.technique.ui.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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

public class InterestPageAdapter extends ArrayAdapter {

    ArrayList<PublicationModel> publicationList = new ArrayList<>();
    Activity activity;

    public InterestPageAdapter(Context context, int resource, ArrayList<PublicationModel> objects, Activity activity) {
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

        v = inflater.inflate(R.layout.grid_item, null);

        TextView city_name = (TextView) v.findViewById(R.id.gridText);
        ImageView imageView = (ImageView) v.findViewById(R.id.gridImage);

        city_name.setText(publicationList.get(position).getImage().getCity());

        ImageAsyncService getImage = new ImageAsyncService(publicationList.get(position).getImage().getUrl());

        Runnable runnable = () -> {
            getImage.doInBackGround();
            this.activity.runOnUiThread(() -> imageView.setImageBitmap(getImage.getItemResult()));
        };
        Executors.newSingleThreadExecutor().execute(runnable);

        return v;
    }

}
