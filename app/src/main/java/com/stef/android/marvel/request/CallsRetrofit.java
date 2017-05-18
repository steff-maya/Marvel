package com.stef.android.marvel.request;

import android.util.Log;

import com.stef.android.marvel.BuildConfig;
import com.stef.android.marvel.MarvelAplication;
import com.stef.android.marvel.model.Comics;
import com.stef.android.marvel.utils.MarvelUtils;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Irene on 21-02-17.
 */

public class CallsRetrofit {

    static getComics.MarvelInterfaces service = getComics.getClient();
    private static final ArrayList<Subscription> subscriptions = new ArrayList<>();



    public static  void getComic() throws NoSuchAlgorithmException {
        String ts =MarvelUtils.getTimeStamp();
        Observable<Comics> callComics =
                service.getComics(ts, BuildConfig.PUBLIC_KEY,MarvelUtils.getMD5(ts),"30");
        final Subscription subscription = callComics
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Comics>() {

                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        Broadcasts.dispatchGetComicFail(MarvelAplication.getContext());
                        Log.d("RetrofitTest", "Error code: " + e.getMessage());
                        if (e instanceof HttpException) {
                            HttpException response = (HttpException) e;
                            int code = response.code();
                            Log.d("RetrofitTest", "Error code: " + code);
                            Log.d("RetrofitTest", "Error code: " + response.getMessage());



                        }
                    }

                    @Override
                    public void onNext(Comics response) {
                        Comics.Data comics = response.getData();
                        Broadcasts.dispatchGetComicSuccess(MarvelAplication.getContext(),comics);
                        Log.d("RetrofitTest", "Error code: " + comics);

                        //adapter.notifyDataSetChanged();
                    }
                });

        subscriptions.add(subscription);

    }



    public static  void getComicDetal(String id) throws NoSuchAlgorithmException {
        String ts =MarvelUtils.getTimeStamp();
        Observable<Comics> callComics =
                service.getComicsDetails(id,ts, BuildConfig.PUBLIC_KEY,MarvelUtils.getMD5(ts),"30");
        final Subscription subscription = callComics
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Comics>() {

                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        Broadcasts.dispatchGetComicDetailFail(MarvelAplication.getContext());
                        Log.d("RetrofitTest", "Error code: " + e.getMessage());
                        if (e instanceof HttpException) {
                            HttpException response = (HttpException) e;
                            int code = response.code();
                            Log.d("RetrofitTest", "Error code: " + code);
                            Log.d("RetrofitTest", "Error code: " + response.getMessage());



                        }
                    }

                    @Override
                    public void onNext(Comics response) {
                        Comics.Data comics = response.getData();
                        Broadcasts.dispatchGetComicDetailSuccess(MarvelAplication.getContext(),comics);
                        Log.d("RetrofitTest", "Error code: " + comics);

                        //adapter.notifyDataSetChanged();
                    }
                });

        subscriptions.add(subscription);

    }
}
