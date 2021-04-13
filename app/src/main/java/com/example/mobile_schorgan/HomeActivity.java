package com.example.mobile_schorgan;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {

    private ViewPagerAdapter mAdapter;
    private Fragment firstFragment, secondFragment;
    private Integer position;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabs);

        firstFragment = new CadastrosFragment();
        secondFragment = new SecondFragment();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);
        setupIcons();

        if (position == null){
            position = 1;
        }
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        if (tab != null){
            tab.select();
        }

        tabLayout.addOnTabSelectedListener(this);
        setupIcons();

    }

    private void setupViewPager(ViewPager viewPager){
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(firstFragment, "");
        mAdapter.addFragment(secondFragment, "");
        viewPager.setAdapter(mAdapter);
    }

    private void setupIcons() {
        TabLayout.Tab tab0 = tabLayout.getTabAt(0);
        TabLayout.Tab tab1 = tabLayout.getTabAt(1);
        tab0.setIcon(R.drawable.ic_baseline_add_24);
        tab1.setIcon(R.drawable.ic_baseline_calendar_today_24);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


}