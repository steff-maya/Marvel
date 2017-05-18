package com.stef.android.marvel.realm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.stef.android.marvel.MarvelAplication;
import com.stef.android.marvel.model.FavComic;
import com.stef.android.marvel.model.Results;
import com.stef.android.marvel.request.Broadcasts;

import java.io.ByteArrayOutputStream;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Irene on 22-02-17.
 */

public class RealmController {
   private static RealmController instance;
    private Realm realm;

    public RealmController() {
        realm = Realm.getDefaultInstance();
    }
    public Realm getRealm() {

        return realm;
    }


    public static RealmController with() {

        if (instance == null) {
            instance = new RealmController();
        }
        return instance;
    }



    public static void saveData(Realm realm, final Results results){
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    // Add a person
                    FavComic comic = realm.createObject(FavComic.class,results.getId());





                    comic.setTitle(results.getTitle());
                    comic.setPrices(results.getPrices().get(0).getPrice().toString());
                    comic.setThumbnail(results.getThumbnail().getPath());
                    comic.setDescription(results.getDescription());
                    Broadcasts.dispatchaddFav(MarvelAplication.getContext());


                }



            });
        }catch (Exception e){
            Log.d("TAG sa",e.getMessage());
        }

        }

    public static void  deleteData(Realm realm,int id){
        try {
            final RealmResults<FavComic> results = realm.where(FavComic.class).equalTo("id", id).findAll();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.get(0).deleteFromRealm(); // indirectly delete object
                    Broadcasts.dispatchaddFav(MarvelAplication.getContext());
                }
            });
        }catch (Exception e){
            Log.d("TAG",e.getMessage());
        }



    }


    public static boolean  getData(Realm realm,int id){
        final RealmResults<FavComic> results = realm.where(FavComic.class).equalTo("id", id).findAll();


        if (results.size() > 0) {
         return  true;
        }else {
           return  false;
        }


    }


    public static RealmResults<FavComic>  getAllData(Realm realm){
        final RealmResults<FavComic> results = realm.where(FavComic.class).findAll();
        Log.d("TAG save", String.valueOf(results.size()));


      return  results;


    }



    /** public RealmController(Context application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Context ctx) {

        if (instance == null) {
            instance = new RealmController(ctx);
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.re();
    }

    //clear all objects from FavComic.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(FavComic.class);
        realm.commitTransaction();
    }

    //find all objects in the FavComic.class
    public RealmResults<FavComic> getComics() {

        return realm.where(FavComic.class).findAll();
    }

    //query a single item with the given id
    public FavComic getComic(String id) {

        return realm.where(FavComic.class).equalTo("id", id).findFirst();
    }

    //check if FavComic.class is empty
    public boolean hasFavComic() {

        return !realm.allObjects(FavComic.class).isEmpty();
    }




    //query example
    public RealmResults<FavComic> getComicFav(int id) {

        return realm.where(FavComic.class)
                .equalTo("id", id)
                .or()

                .findAll();

    }*/
}
