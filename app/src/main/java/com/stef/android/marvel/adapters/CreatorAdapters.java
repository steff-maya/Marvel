package com.stef.android.marvel.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.stef.android.marvel.anim.TextDrawable;
import com.stef.android.marvel.model.Creators;
import com.stef.android.marvel.model.Results;
import com.stef.android.marvel.realm.RealmController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;

/**
 * Created by Irene on 22-02-17.
 */

public class CreatorAdapters extends RecyclerView.Adapter<CreatorAdapters.MyViewHolder> {

    private Context mContext ;
    private ArrayList<Creators.Items> comicList;
    private Realm realm;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, role;
        public ImageView thumbnail;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            role = (TextView) view.findViewById(R.id.role);
            thumbnail = (ImageView) view.findViewById(R.id.image_view);


        }
    }


    public CreatorAdapters(Context mContext, ArrayList<Creators.Items> comicList) {
        this.mContext = mContext;
        this.comicList = comicList;
        //get realm instance
        this.realm = RealmController.with().getRealm();
    }

    @Override
    public CreatorAdapters.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.creators_row, parent, false);

        return new CreatorAdapters.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CreatorAdapters.MyViewHolder holder, final int position) {

        holder.title.setText(comicList.get(position).getName());
        holder.role.setText(comicList.get(position).getRole());

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));


        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(String.valueOf(comicList.get(position).getName().charAt(0)), color,10);

        holder.thumbnail.setImageDrawable(drawable);


    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }
}