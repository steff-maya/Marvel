package com.stef.android.marvel.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.stef.android.marvel.MarvelAplication;
import com.stef.android.marvel.R;
import com.stef.android.marvel.R2;
import com.stef.android.marvel.adapters.ViewPagerAdapter;
import com.stef.android.marvel.fragments.ComicsFragment;
import com.stef.android.marvel.fragments.FavFragment;
import com.stef.android.marvel.request.Broadcasts;
import com.stef.android.marvel.utils.MarvelUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarvelActivity extends AppCompatActivity {


    @BindView(R2.id.toolbar)
    Toolbar toolbar;


    @BindView(R2.id.viewpager)
    ViewPager viewPager;

    @BindView(R2.id.tabs)
    TabLayout tabLayout;

    @BindView(R2.id.search)
    AppCompatEditText search;


    private int[] tabIcons = {
            R.drawable.ic_iron_man,
            R.drawable.ic_captain_america

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marvel);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();




        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();

                Log.d("TAG", "INGRESO" + charSequence.toString());
                Broadcasts.dispatchSearch(MarvelAplication.getContext(),charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);

    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ComicsFragment(), "ONE");
        adapter.addFragment(new FavFragment(), "TWO");

        viewPager.setAdapter(adapter);
    }
}
