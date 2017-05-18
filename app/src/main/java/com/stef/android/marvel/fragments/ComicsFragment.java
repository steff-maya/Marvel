package com.stef.android.marvel.fragments;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.stef.android.marvel.MarvelAplication;
import com.stef.android.marvel.R;
import com.stef.android.marvel.activities.MarvelActivity;
import com.stef.android.marvel.adapters.ComicsAdapter;
import com.stef.android.marvel.model.Comics;
import com.stef.android.marvel.model.Results;
import com.stef.android.marvel.request.Broadcasts;
import com.stef.android.marvel.request.CallsRetrofit;
import com.stef.android.marvel.utils.GridSpacingItemDecoration;
import com.stef.android.marvel.utils.MarvelUtils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.stef.android.marvel.request.Broadcasts.COMIC_ITEMS;
import static com.stef.android.marvel.request.Broadcasts.GET_COMICDETAIL_FAIL;
import static com.stef.android.marvel.request.Broadcasts.GET_COMICDETAIL_SUCCESS;
import static com.stef.android.marvel.request.Broadcasts.GET_COMIC_FAIL;
import static com.stef.android.marvel.request.Broadcasts.GET_COMIC_SUCCESS;
import static com.stef.android.marvel.request.Broadcasts.SEARCH_ITEMS;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicsFragment extends Fragment {

    private  View view;
    Unbinder unbinder;
    private ComicsAdapter adapter;
    private List<Results> comicList = new ArrayList<>();
    private List<Results> comicList_aux = new ArrayList<>();
    ProgressDialog dialog;



    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.list_empty)
    RelativeLayout list_empty;

    @BindView(R.id.fab)
    FloatingActionButton fab;


    public ComicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        if(view==null){

            view=inflater.inflate(R.layout.fragment_comics, container, false);
            unbinder= ButterKnife.bind(this,view);

            adapter = new ComicsAdapter(getActivity(), comicList);


            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, MarvelUtils.dpToPx(getActivity(),10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);



             dialog = new ProgressDialog(getActivity(), android.R.style.Theme_Dialog) {
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
                CallsRetrofit.getComic();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                dialog.dismiss();
                list_empty.setVisibility(View.VISIBLE);
            }
        }else {
            unbinder= ButterKnife.bind(this,view);
        }

        return  view;
    }



    private BroadcastReceiver getData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get the received random number

            Comics.Data  list = intent.getExtras().getParcelable(COMIC_ITEMS);
            //list = intent.getExtras().getParcelableArrayList(COMIC_ITEMS);


            //  loadAlbums(list);
            list_empty.setVisibility(View.GONE);


            comicList.addAll(list.getResults());
            Log.d("SIZE", String.valueOf(comicList.size()));
            adapter = new ComicsAdapter(getActivity(), comicList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            dialog.dismiss();
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

            dialog.dismiss();
            list_empty.setVisibility(View.VISIBLE);


            //loadRows(list);


        }


    };


    @Override
    public void onStart() {
        super.onStart();

        Broadcasts.registerSuccess(getActivity(), getData, GET_COMIC_SUCCESS);
        Broadcasts.registerSuccess(getActivity(), searchData, SEARCH_ITEMS);
        Broadcasts.registerFail(getActivity(), failData, GET_COMIC_FAIL);



    }

    @Override
    public void onStop() {
        super.onStop();

        Broadcasts.unregisterSuccess(getActivity(), getData);
        Broadcasts.unregisterSuccess(getActivity(), failData);
        Broadcasts.unregisterSuccess(getActivity(), searchData);


    }


    @OnClick(R.id.fab)
    public void loadMore() {
        Log.d("TAG","FDSdf");

        comicList_aux=new ArrayList<Results>();
        comicList_aux.addAll(comicList);
        Collections.shuffle(comicList_aux);

        adapter = new ComicsAdapter(getActivity(), comicList_aux);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }





    private BroadcastReceiver searchData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get the received random number

            //Comics.Data  list = intent.getExtras().getParcelable(COMIC_ITEMS);
            //list = intent.getExtras().getParcelableArrayList(COMIC_ITEMS);
            String search = intent.getStringExtra("search");
            Log.d("Search",search );
            search(search);


            //loadRows(list);


        }


    };






    public void search(String text) {
        Log.d("SFFD", "Buscar");
        adapter.resetData();
        adapter.getFilter().filter(text);

    }


}
