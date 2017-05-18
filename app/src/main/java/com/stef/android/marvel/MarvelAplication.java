package com.stef.android.marvel;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Irene on 20-02-17.
 */

public class MarvelAplication extends Application {


    private static Context context;
    public static final String AUTH_URL = "http://" + "127.0.0.1:9080" + ":9080/auth";
    public static final String REALM_URL = "realm://" + "127.0.0.1:9080" + ":9080/~/realmtasks";


    public void onCreate() {
        super.onCreate();
        MarvelAplication.context = getApplicationContext();
        Realm.init(this);
        getKeyHash();
    }

    public static Context getContext() {
        return MarvelAplication.context;
    }

    public void getKeyHash() {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.stef.android.marvel", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d("KEUY",something);
                //String something = new String(Base64.encodeBytes(md.digest()));

            }
        } catch (PackageManager.NameNotFoundException e1) {
            //Logs.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            //Logs.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            //Logs.e("exception", e.toString());
        }
    }

}
