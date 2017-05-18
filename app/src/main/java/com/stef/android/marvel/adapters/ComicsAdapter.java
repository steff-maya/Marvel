package com.stef.android.marvel.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stef.android.marvel.MarvelAplication;
import com.stef.android.marvel.R;


import com.stef.android.marvel.activities.DetailActiivty;
import com.stef.android.marvel.anim.SparkButton;
import com.stef.android.marvel.anim.SparkEventListener;
import com.stef.android.marvel.model.Results;
import com.stef.android.marvel.realm.RealmController;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Irene on 21-02-17.
 */
public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.MyViewHolder> implements Filterable {

    private Context mContext ;
    private List<Results> comicList;
    private Realm realm;



    private List<Results> origPlanetList;

    private Filter planetFilter;

    @Override
    public Filter getFilter() {
        if (planetFilter == null)
            planetFilter = new PlanetFilter();

        return planetFilter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail;
        public SparkButton overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (SparkButton) view.findViewById(R.id.overflow);

        }
    }


    public ComicsAdapter(Context mContext, List<Results> comicList) {
        this.mContext = mContext;
        this.comicList = comicList;
        //get realm instance


        this.origPlanetList = comicList;
        this.realm = RealmController.with().getRealm();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comic_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.title.setText(comicList.get(position).getTitle());
        holder.count.setText(comicList.get(position).getPrices().get(0).getPrice() + " $");

        // loading album cover using Glide library
        Log.d("PATH",comicList.get(position).getThumbnail().getPath());
        Log.d("PATH",comicList.get(position).getPrices().get(0).getType());

        Glide.with(mContext).load(comicList.get(position).getThumbnail().getPath()+"/portrait_incredible.jpg") .diskCacheStrategy( DiskCacheStrategy.RESULT ).crossFade().placeholder(R.mipmap.marvellogo).into(holder.thumbnail);

        if(RealmController.getData(realm,comicList.get(position).getId())){
            holder.overflow.setChecked(true);
        }else {
            holder.overflow.setChecked(false);
        }
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Clic");

                Intent intent = new Intent(MarvelAplication.getContext(), DetailActiivty.class);
                intent.putExtra("id",comicList.get(position).getId() );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MarvelAplication.getContext().startActivity(intent);










            }
        });

        holder.overflow.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {

                if (buttonState) {
                    Log.d("TAG", "is Active");
                    // Button is active

                    // Button is inactive



                    // Button is inactive
                    holder.overflow.playAnimation();
                    addFavorito(position);



                } else {


                    holder.overflow.playAnimation();
                    Log.d("TAG", "is inactive");
                    deleteFavorito(position);
                }

            }
        });



    }

    private void addFavorito(int position) {
        RealmController.saveData(realm,comicList.get(position));

    }

    private void deleteFavorito(int position) {
        RealmController.deleteData(realm,comicList.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }



    private class PlanetFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = comicList;
                results.count = comicList.size();
            } else {
                // We perform filtering operation
                List<Results> nPlanetList = new ArrayList<Results>();

                for (Results p : comicList) {
                    if (constraint.length() > 1) {
                        // Logs.d("TAG FILTRANDI ", String.valueOf(constraint.length()));

                        if (p.getTitle().toUpperCase().contains(constraint.toString().toUpperCase())) {
                            nPlanetList.add(p);

                        }

                    } else {


                        if (p.getTitle().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                            nPlanetList.add(p);

                        }

                    }

                }
                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0) {

                comicList = origPlanetList;


                notifyDataSetChanged();

            } else {
                comicList = (List<Results>) results.values;

                notifyDataSetChanged();


            }

        }


    }

    public void resetData() {
        comicList = origPlanetList;
    }

}