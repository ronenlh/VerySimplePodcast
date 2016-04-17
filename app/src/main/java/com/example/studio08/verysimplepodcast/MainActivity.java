package com.example.studio08.verysimplepodcast;

import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menuitem_search:
                    Toast.makeText(this, getString(R.string.ui_menu_search),
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menuitem_send:
                    Toast.makeText(this, getString(R.string.ui_menu_send),
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menuitem_add:
                    Toast.makeText(this, getString(R.string.ui_menu_add),
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menuitem_share:
                    Toast.makeText(this, getString(R.string.ui_menu_share),
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menuitem_feedback:
                    Toast.makeText(this, getString(R.string.ui_menu_feedback),
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menuitem_about:
                    Toast.makeText(this, getString(R.string.ui_menu_about),
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menuitem_quit:
                    Toast.makeText(this, getString(R.string.ui_menu_quit),
                            Toast.LENGTH_SHORT).show();
                    finish(); // close the activity
                    return true;
            }
            return false;
        }

        // end sliding tab

//        Intent intent = new Intent(this, FeedSelectorActivity.class);
//        startActivity(intent);
    }


}
