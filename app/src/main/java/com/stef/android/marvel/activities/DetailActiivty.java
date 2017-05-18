package com.stef.android.marvel.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stef.android.marvel.R;
import com.stef.android.marvel.adapters.CharactersAdapter;
import com.stef.android.marvel.adapters.CreatorAdapters;
import com.stef.android.marvel.adapters.StoriesAdapter;
import com.stef.android.marvel.model.Comics;
import com.stef.android.marvel.model.Results;
import com.stef.android.marvel.request.Broadcasts;
import com.stef.android.marvel.request.CallsRetrofit;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.stef.android.marvel.request.Broadcasts.COMIC_ITEMS;
import static com.stef.android.marvel.request.Broadcasts.GET_COMICDETAIL_FAIL;
import static com.stef.android.marvel.request.Broadcasts.GET_COMICDETAIL_SUCCESS;

public class DetailActiivty extends AppCompatActivity {

    private View view;
    Unbinder unbinder;

    @BindView(R.id.htab_header)
    ImageView htab_header;

    @BindView(R.id.tittle)
    TextView tittle;

    @BindView(R.id.pages)
    TextView pages;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.modified)
    TextView modified;

    @BindView(R.id.txtcre)
    TextView textcreators;


    @BindView(R.id.creators)
    RecyclerView creators;


    @BindView(R.id.txtstories)
    TextView txtstories;


    @BindView(R.id.stories)
    RecyclerView stories;


    @BindView(R.id.characterstxt)
    TextView characterstxt;


    @BindView(R.id.characters)
    RecyclerView characteres;
    ProgressDialog dialog;


    @BindView(R.id.list_empty)
    RelativeLayout list_empty;




    CreatorAdapters adapter;
    StoriesAdapter adapterStories;
    CharactersAdapter charactersStories;
    private List<Results> comicList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_actiivty);
        ButterKnife.bind(this);





        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);

        dialog = new ProgressDialog(this, android.R.style.Theme_Dialog) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.loading);
                //getWindow().setLayout(CoordinatorLayout.LayoutParams.FILL_PARENT,
                //      CoordinatorLayout.LayoutParams.FILL_PARENT);

                getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
        };

        dialog.setCancelable(true);

        try {
            dialog.show();
            CallsRetrofit.getComicDetal(String.valueOf(id));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            dialog.dismiss();
            list_empty.setVisibility(View.VISIBLE);
        }






    }


    public void setUi(){



        Glide.with(this).load(comicList.get(0).getThumbnail().getPath()+"/landscape_incredible.jpg") .diskCacheStrategy( DiskCacheStrategy.RESULT ).crossFade().placeholder(R.mipmap.marvellogo).into(htab_header);

        tittle.setText(comicList.get(0).getTitle());
        pages.setText(String.valueOf(comicList.get(0).getPageCount()));
        description.setText(comicList.get(0).getDescription());
        modified.setText(comicList.get(0).getModified());
        creators.setNestedScrollingEnabled(false);
        stories.setNestedScrollingEnabled(false);
        characteres.setNestedScrollingEnabled(false);

        if(comicList.get(0).getCreators().getItems().size()>0){


            CreatorAdapters adapter = new CreatorAdapters(this, comicList.get(0).getCreators().getItems());


            creators.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



            creators.setAdapter(adapter);

        }else {
            textcreators.setVisibility(View.GONE);
        }

        Log.d("TAG", String.valueOf(comicList.get(0).getStories().getItems().size()));

        if(comicList.get(0).getStories().getItems().size()>0){


            adapterStories = new StoriesAdapter(this, comicList.get(0).getStories().getItems());


            stories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



            stories.setAdapter(adapterStories);

        }else {
            txtstories.setVisibility(View.GONE);
        }

        Log.d("TAG", String.valueOf(comicList.get(0).getStories().getItems().size()));

        if(comicList.get(0).getCharacters().getItems().size()>0){


            charactersStories = new CharactersAdapter(this, comicList.get(0).getCharacters().getItems());


            characteres.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



            characteres.setAdapter(charactersStories);

        }else {
            characterstxt.setVisibility(View.GONE);
        }
    }


    private BroadcastReceiver getData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get the received random number

            Comics.Data list = intent.getExtras().getParcelable(COMIC_ITEMS);
            //list = intent.getExtras().getParcelableArrayList(COMIC_ITEMS);


            //  loadAlbums(list);
            comicList.addAll(list.getResults());

            setUi();
            dialog.dismiss();
            list_empty.setVisibility(View.GONE);




            ObjectMapper objectM = new ObjectMapper();
            try {
                Log.d("RESP", "onReceive: " + objectM.writeValueAsString(list));
            } catch (IOException e) {
                e.printStackTrace();
            }


            //loadRows(list);


        }


    };

    private BroadcastReceiver failData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get the received random number
            list_empty.setVisibility(View.VISIBLE);
            //Comics.Data  list = intent.getExtras().getParcelable(COMIC_ITEMS);
            //list = intent.getExtras().getParcelableArrayList(COMIC_ITEMS);
            dialog.dismiss();


            //loadRows(list);


        }


    };


    @Override
    public void onStart() {
        super.onStart();

        Broadcasts.registerSuccess(this, getData, GET_COMICDETAIL_SUCCESS);
        Broadcasts.registerFail(this, failData, GET_COMICDETAIL_FAIL);



    }

    @Override
    public void onStop() {
        super.onStop();

        Broadcasts.unregisterSuccess(this, getData);
        Broadcasts.unregisterSuccess(this, failData);


    }
}
