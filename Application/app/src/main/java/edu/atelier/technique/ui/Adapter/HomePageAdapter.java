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
import android.widget.Toast;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import edu.atelier.technique.R;
import edu.atelier.technique.models.PublicationModel;
import edu.atelier.technique.services.ImageAsyncService;
import edu.atelier.technique.singletons.ListOfPublications;

/**
 * Cette classe permet d'adapter visuellement les différents éléments d'une même liste.
 * Notez que sa configuration est différentes de [ InterestPageAdapter ]
 */
public class HomePageAdapter extends ArrayAdapter {


    ArrayList<PublicationModel> publicationList = new ArrayList<>();
    Activity activity;
    private static Drawable isSaved = null;
    private static Drawable isNotSaved = null;


    /**
     * Constructeur
     * @param context
     * @param resource
     * @param objects
     * @param activity
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public HomePageAdapter(Context context, int resource, ArrayList<PublicationModel> objects, Activity activity) {
        super(context, resource, objects);
        publicationList = objects;
        this.activity = activity;
        isSaved = activity.getApplicationContext().getDrawable(R.drawable.ic_bookmark_fill);
        isNotSaved = activity.getApplicationContext().getDrawable(R.drawable.ic_bookmark_outline);
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

        v = inflater.inflate(R.layout.component_post_view, null);

        TextView city_name = (TextView) v.findViewById(R.id.city_name);
        TextView country_name = (TextView) v.findViewById(R.id.country_name);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        ImageView savedButton = (ImageView) v.findViewById(R.id.ic_save);

        if(ListOfPublications.getInstance().publicationIsSaved(publicationList.get(position))){
            publicationList.get(position).setIsFavoris(true);
        }

        city_name.setText(publicationList.get(position).getImage().getCity());
        country_name.setText(publicationList.get(position).getImage().getCountry());

        savedButton.setOnClickListener( click -> {
            //Change son etat
            publicationList.get(position).setIsFavoris(ListOfPublications.getInstance().addOrDeletePublication(publicationList.get(position)));

            Toast toast;
            if(publicationList.get(position).isFavoris()){
                savedButton.setImageDrawable(isSaved);
                toast = Toast.makeText(this.activity.getApplicationContext(), R.string.addInListOfPublication, Toast.LENGTH_SHORT);
            }else{
                savedButton.setImageDrawable(isNotSaved);
                toast = Toast.makeText(this.activity.getApplicationContext(), R.string.subInListOfPublication, Toast.LENGTH_SHORT);
            }
            toast.show();
        });

        ((ImageView) v.findViewById(R.id.ic_gps)).setImageResource(R.drawable.ic_gps_location);

        if (publicationList.get(position).isFavoris()) {
            ((ImageView) v.findViewById(R.id.ic_save)).setImageResource(R.drawable.ic_bookmark_fill);
        } else {
            ((ImageView) v.findViewById(R.id.ic_save)).setImageResource(R.drawable.ic_bookmark_outline);
        }

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
