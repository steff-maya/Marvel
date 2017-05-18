package com.stef.android.marvel.request;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.stef.android.marvel.model.Comics;

import java.util.List;

/**
 * Created by Irene on 21-02-17.
 */

public class Broadcasts {

    public static String GET_COMIC_SUCCESS = "GET_COMIC_SUCCESS";
    public static String GET_COMIC_FAIL = "GET_COMIC_FAIL";
    public static String FAV_ITEMS = "FAV_ITEMS";
    public static String COMIC_ITEMS = "COMIC_ITEMS";
    public static String SEARCH_ITEMS = "SEARCH_ITEMS";
    public static String GET_COMICDETAIL_FAIL = "GET_COMICDETAIL_FAIL";
    public static String GET_COMICDETAIL_SUCCESS = "GET_COMICDETAIL_SUCCESS";
    public static String COMICDETAIL_ITEMS = "COMICDETAIL_ITEMS";


    public static void registerSuccess(Context context, final BroadcastReceiver success, String intent) {
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        broadcastManager.registerReceiver(success, new IntentFilter(intent));
    }


    public static void registerFail(Context context, final BroadcastReceiver success, String intent) {
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        broadcastManager.registerReceiver(success, new IntentFilter(intent));
    }


    public static void unregisterSuccess(Context context, final BroadcastReceiver success) {
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        broadcastManager.unregisterReceiver(success);
    }


    public static void unregisterFail(Context context, final BroadcastReceiver success) {
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        broadcastManager.unregisterReceiver(success);
    }

    public static void dispatchGetComicSuccess(Context context, Comics.Data response) {

        try {
            Intent intent = new Intent(GET_COMIC_SUCCESS);
            intent.putExtra(COMIC_ITEMS, response);



            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        } catch (Exception e) {

        }

    }




    public static void dispatchGetComicFail(Context context) {
        Intent intent = new Intent(GET_COMIC_FAIL);


        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    public static void dispatchGetComicDetailSuccess(Context context, Comics.Data response) {

        try {
            Intent intent = new Intent(GET_COMICDETAIL_SUCCESS);
            intent.putExtra(COMIC_ITEMS, response);



            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        } catch (Exception e) {

        }

    }




    public static void dispatchGetComicDetailFail(Context context) {
        Intent intent = new Intent(GET_COMICDETAIL_SUCCESS);


        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    public static void dispatchSearch(Context context, String response) {

        try {
            Intent intent = new Intent(SEARCH_ITEMS);
            intent.putExtra("search",response);




            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        } catch (Exception e) {

        }

    }



    public static void dispatchaddFav(Context context) {
        Intent intent = new Intent(FAV_ITEMS);


        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
