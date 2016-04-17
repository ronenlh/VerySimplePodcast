package com.example.studio08.verysimplepodcast;

import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.studio08.verysimplepodcast.view.SlidingTabLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    SlidingTabLayout slidingTabLayout;
    ActionTabsViewPagerAdapter myViewPagerAdapter;
    ViewPager viewPager;
    ArrayList fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // sliding tab

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        fragments = new ArrayList<Fragment>();

        myViewPagerAdapter =new ActionTabsViewPagerAdapter(getFragmentManager(), fragments);

        // end sliding tab

//        Intent intent = new Intent(this, FeedSelectorActivity.class);
//        startActivity(intent);
    }


}
