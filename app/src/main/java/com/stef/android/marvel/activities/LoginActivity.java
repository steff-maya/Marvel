package com.stef.android.marvel.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.stef.android.marvel.MarvelAplication;
import com.stef.android.marvel.R;
import com.stef.android.marvel.R2;
import com.stef.android.marvel.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity  {

    private final String TAG = getClass().getSimpleName();
    @BindView(R2.id.btn_signup_fb)
    AppCompatButton btn_signup_fb;

    CallbackManager callbackManager;
    SessionManager sessionManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        FacebookSdk.sdkInitialize(MarvelAplication.getContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, mFacebookCallBack);




        sessionManager = new SessionManager(MarvelAplication.getContext());
        if(!sessionManager.isLoggedIn()){
            btn_signup_fb.setVisibility(View.VISIBLE);
            btn_signup_fb.setEnabled(true);
        }else {
            btn_signup_fb.setVisibility(View.GONE);
            btn_signup_fb.setEnabled(false);
            Intent i = new Intent(MarvelAplication.getContext(), MarvelActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MarvelAplication.getContext().startActivity(i);
            finish();
        }




    }

    @OnClick
    (R.id.btn_signup_fb)
    public void singup() {
        Log.d("TAG","FDSdf");

        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("user_friends", "email"));


       /* Intent i = new Intent(MarvelAplication.getContext(), MarvelActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MarvelAplication.getContext().startActivity(i);
        finish();*/

    }




    private FacebookCallback<LoginResult> mFacebookCallBack = new FacebookCallback<LoginResult>()
    {

        @Override
        public void onSuccess(LoginResult loginResult)
        {

            String accessToken = loginResult.getAccessToken().getToken();
            Log.i("accessToken", accessToken);

            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Log.i("LoginActivity", response.toString());
                    // Get facebook data from login

                    sessionManager.createLoginSession();

                    Intent i = new Intent(MarvelAplication.getContext(), MarvelActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MarvelAplication.getContext().startActivity(i);
                    finish();



                }

            });
            Bundle parameters = new Bundle();

            parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // ParÃ¡metros que pedimos a facebo
            request.setParameters(parameters);
            request.executeAsync();




        }

        @Override
        public void onCancel()
        {
            Log.d(TAG,"FacebookCallback loginResult onCancel");

        }

        @Override
        public void onError(FacebookException e)
        {
            Log.e(TAG,"FacebookCallback loginResult onError->"+e.toString());
            e.printStackTrace();


        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }


    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id="";
            try{
                id = object.getString("id");
            }
            catch (Exception e){
                id="0";

            }


            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));




            return bundle;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }



}
