package com.stef.android.marvel.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stef.android.marvel.MarvelAplication;
import com.stef.android.marvel.R;
import com.stef.android.marvel.activities.DetailActiivty;
import com.stef.android.marvel.anim.SparkButton;
import com.stef.android.marvel.anim.SparkEventListener;
import com.stef.android.marvel.model.FavComic;
import com.stef.android.marvel.model.Results;
import com.stef.android.marvel.realm.RealmController;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Irene on 22-02-17.
 */

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MyViewHolder> {

    private Context mContext ;
    private RealmResults<FavComic> comicList;
    private Realm realm;

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


    public FavAdapter(Context mContext, RealmResults<FavComic> comicList ) {
        this.mContext = mContext;
        this.comicList = comicList;
        //get realm instance
        this.realm = RealmController.with().getRealm();
    }

    @Override
    public FavAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comic_card, parent, false);

        return new FavAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FavAdapter.MyViewHolder holder, final int position) {

        holder.title.setText(comicList.get(position).getTitle());
        holder.count.setText(comicList.get(position).getPrices() + " $");



        Glide.with(mContext).load(comicList.get(position).getThumbnail()+"/portrait_incredible.jpg") .diskCacheStrategy( DiskCacheStrategy.RESULT ).crossFade().placeholder(R.mipmap.marvellogo).into(holder.thumbnail);

        if(RealmController.getData(realm,comicList.get(position).getId())){
            holder.overflow.setChecked(true);
        }else {
            holder.overflow.setChecked(false);
        }

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    //addFavorito(position);



                } else {


                    holder.overflow.playAnimation();
                    Log.d("TAG", "is inactive");
                    deleteFavorito(position);
                }

            }
        });



    }



    private void deleteFavorito(int position) {
        RealmController.deleteData(realm,comicList.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }
}