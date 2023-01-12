package edu.atelier.technique.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.atelier.technique.R;
import edu.atelier.technique.models.PublicationModel;

public class HomePageAdapter extends ArrayAdapter {

    ArrayList<PublicationModel> publicationList = new ArrayList<>();

    public HomePageAdapter(Context context, int resource, ArrayList<PublicationModel> objects) {
        super(context, resource, objects);
        publicationList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.post, null);

        TextView city_name = (TextView) v.findViewById(R.id.city_name);
        TextView country_name = (TextView) v.findViewById(R.id.country_name);
        ImageView imageView = (ImageView) v.findViewById(R.id.image);

        city_name.setText(publicationList.get(position).getImage().getCity());
        country_name.setText(publicationList.get(position).getImage().getCountry());
        //imageView.setImageResource(publicationList.get(position).getPublicationImage().getImageUrl());

        return v;
    }

}
