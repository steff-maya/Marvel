package com.stef.android.marvel.request;

import com.stef.android.marvel.BuildConfig;
import com.stef.android.marvel.model.Comics;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Irene on 21-02-17.
 */

public class getComics {
    private static MarvelInterfaces marvelApiInterface;


    public static MarvelInterfaces getClient() {
        if (marvelApiInterface == null) {


            OkHttpClient okClient = new OkHttpClient.Builder()

                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request original = chain.request();

                                    // Request customization: add request headers
                                    Request.Builder requestBuilder = original.newBuilder()
                                            //   .header("Authorization", token)
                                            .method(original.method(), original.body());


                                    Request request = requestBuilder.build();




                                    long t1 = System.nanoTime();
                                    System.out.println(
                                            String.format("Sending request %s on %s%n%s", request.url(), chain.connection(),
                                                    request.headers()));







                                    return chain.proceed(request);
                                }
                            })
                    .connectTimeout(90, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS).
                            writeTimeout(90, TimeUnit.SECONDS)


                    .build();

            RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());



            Retrofit client = new Retrofit.Builder()
                    .baseUrl(BuildConfig.URL_SERVER)
                    .client(okClient)


                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(rxAdapter)
                    .build();


            marvelApiInterface = client.create(MarvelInterfaces.class);



        }
        return marvelApiInterface;
    }


    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            System.out.println(
                    String.format("Sending request %s on %s%n%s", request.url(), chain.connection(),
                            request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            System.out.println(
                    String.format("Received response for %s in %.1fms%n%s", response.request().url(),
                            (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }


    public interface MarvelInterfaces {

        @GET("v1/public/comics")
        Observable<Comics> getComics(@Query("ts") String ts, @Query("apikey") String apikey,@Query("hash") String hash,@Query("limit") String limit);

        @GET("v1/public/comics/{id}")
        Observable<Comics> getComicsDetails(@Path("id") String id, @Query("ts") String ts, @Query("apikey") String apikey, @Query("hash") String hash, @Query("limit") String limit);



    }

}
