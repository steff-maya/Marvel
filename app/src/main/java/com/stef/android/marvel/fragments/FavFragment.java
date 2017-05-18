package com.stef.android.marvel.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.stef.android.marvel.R;
import com.stef.android.marvel.adapters.ComicsAdapter;
import com.stef.android.marvel.adapters.FavAdapter;
import com.stef.android.marvel.model.Comics;
import com.stef.android.marvel.model.Results;
import com.stef.android.marvel.realm.RealmController;
import com.stef.android.marvel.request.Broadcasts;
import com.stef.android.marvel.utils.GridSpacingItemDecoration;
import com.stef.android.marvel.utils.MarvelUtils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

import static com.stef.android.marvel.request.Broadcasts.COMIC_ITEMS;
import static com.stef.android.marvel.request.Broadcasts.FAV_ITEMS;
import static com.stef.android.marvel.request.Broadcasts.GET_COMIC_FAIL;
import static com.stef.android.marvel.request.Broadcasts.GET_COMIC_SUCCESS;
import static com.stef.android.marvel.request.Broadcasts.SEARCH_ITEMS;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {

    private  View view;
    Unbinder unbinder;
    private FavAdapter adapter;

    @BindView(R.id.list_empty)
    RelativeLayout list_empty;


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Realm realm;


    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment





            view = inflater.inflate(R.layout.fragment_fav, container, false);
            unbinder = ButterKnife.bind(this, view);

            realm = RealmController.with().getRealm();

            adapter = new FavAdapter(getActivity(), RealmController.getAllData(realm));
            if(RealmController.getAllData(realm).size()>0){
                list_empty.setVisibility(View.GONE);
            }else {
                list_empty.setVisibility(View.VISIBLE);
            }


            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, MarvelUtils.dpToPx(getActivity(),10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);




        return  view;
    }


    private BroadcastReceiver getData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get the received random number

            Log.d("FAV","FAV");

            if(RealmController.getAllData(realm).size()>0){
                list_empty.setVisibility(View.GONE);
            }else {
                list_empty.setVisibility(View.VISIBLE);
            }



            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }


    };




    @Override
    public void onStart() {
        super.onStart();

        Broadcasts.registerSuccess(getActivity(), getData, FAV_ITEMS);



    }

    @Override
    public void onStop() {
        super.onStop();

        Broadcasts.unregisterSuccess(getActivity(), getData);



    }

}
