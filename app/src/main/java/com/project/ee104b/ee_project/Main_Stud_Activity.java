package com.project.ee104b.ee_project;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

public class Main_Stud_Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener,TabLayout.OnTabSelectedListener{

    private ViewPager StudViewPager;
    private TabLayout StudTabLayout;
    private String[] titles = new String[]{"成績","科目","測驗"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_stud);
        setTitle(titles[0]);
        getSupportActionBar().setElevation(0);

        vpComponnent();
        vplaunch();


    }

    private void vpComponnent(){
        StudTabLayout = (TabLayout) findViewById(R.id.StudTabLayout);
        StudViewPager = (ViewPager) findViewById(R.id.StudViewPager);
        StudViewPager.addOnPageChangeListener(this);
        StudTabLayout.addOnTabSelectedListener(this);

    }
    private void vplaunch(){
        StudViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new fg_stud_score();
                    case 1:
                        return new fg_stud_question();
                    case 2:
                        return new fg_stud_test();
                }
                return null;
            }
            @Override
            public int getCount() {
                return 3;
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.add_ques);
        item.setVisible(false);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                break;
            case R.id.action_help:
                break;
            case R.id.action_delete:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //進階選項
    @Override
    public void onPageSelected(int position) {
        StudTabLayout.getTabAt(position).select();
        setTitle(titles[position]);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //StudViewPager.sets
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
    @Override
    //監聽ViewPaper
    public void onTabSelected(TabLayout.Tab tab) {StudViewPager.setCurrentItem(tab.getPosition());}
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}
    @Override
    public void onTabReselected(TabLayout.Tab tab) {}
    //監聽TabLayout
}
