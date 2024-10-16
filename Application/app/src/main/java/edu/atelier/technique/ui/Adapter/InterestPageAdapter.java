package edu.atelier.technique.ui.Adapter;

import android.annotation.SuppressLint;
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

/**
 * Cette classe permet d'adapter visuellement les différents éléments d'une même liste.
 * Notez que sa configuration est différentes de [ HomePageAdapter ]
 */
public class InterestPageAdapter extends ArrayAdapter {


    ArrayList<PublicationModel> publicationList = new ArrayList<>();
    Activity activity;


    /**
     * Constructeur
     * @param context
     * @param resource
     * @param objects
     * @param activity
     */
    public InterestPageAdapter(Context context, int resource, ArrayList<PublicationModel> objects, Activity activity) {
        super(context, resource, objects);
        publicationList = objects;
        this.activity = activity;
    }

    /**
     * récupère le nombre d'élément dans la liste à adapté
     * @return super.getCount()
     */
    @Override
    public int getCount() { return super.getCount(); }

    /**
     * récupère la view généré par élément de la liste
     * @param position
     * @param convertView
     * @param parent
     * @return la view relative à un et un seul élément
     */
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.component_grid_item, null);

        TextView city_name = (TextView) v.findViewById(R.id.gridText);
        ImageView imageView = (ImageView) v.findViewById(R.id.gridImage);

        city_name.setText(publicationList.get(position).getImage().getCity());

        if(publicationList.get(position).getImage().bitmap == null) {
            ImageAsyncService getImage = new ImageAsyncService(publicationList.get(position).getImage().getUrl());
            Runnable runnable = () -> {
                getImage.doInBackGround();
                this.activity.runOnUiThread(() -> {
                    publicationList.get(position).getImage().bitmap = getImage.getItemResult();
                    imageView.setImageBitmap(publicationList.get(position).getImage().bitmap);
                });
            };
            Executors.newSingleThreadExecutor().execute(runnable);
        } else { imageView.setImageBitmap(publicationList.get(position).getImage().bitmap); }

        return v;
    }

}
