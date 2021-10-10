package com.project.ee104b.ee_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.UserHandle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Main_Teac_Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener,TabLayout.OnTabSelectedListener{

    private Menu amenu;
    private ViewPager TeacViewPager;
    private TabLayout TeacTabLayout;
    private String[] titles = new String[]{"班級成績","題目","考試"};
    private String page ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_teac);
        setTitle(titles[0]);

        vpComponnent();
        vplaunch();

    }

    private void vpComponnent(){
        TeacTabLayout = (TabLayout) findViewById(R.id.TeacTabLayout);
        TeacViewPager = (ViewPager) findViewById(R.id.TeacViewPager);
        TeacViewPager.addOnPageChangeListener(this);
        TeacTabLayout.addOnTabSelectedListener(this);
    }
    private void vplaunch(){
        TeacViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new fg_teac_score();
                    case 1:
                        return new fg_teac_question();
                    case 2:
                        return new fg_teac_room();
                }
                return null;
            }
            @Override
            public int getCount() {
                return 3;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        amenu = menu;
        MenuItem item = amenu.findItem(R.id.add_ques);
        item.setVisible(false);
        return true;
    }
    private void rebuildmenu(){
        MenuItem item = amenu.findItem(R.id.add_ques);
        if (page.equals("題目")) {
            item.setVisible(true);
        }
        else{
            item.setVisible(false);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.add_ques:
                Intent it = new Intent(this, EditQuestion.class);
                it.putExtra("新增", "new");
                startActivity(it);
                break;
            case R.id.action_settings:
                break;
            case R.id.action_help:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //進階選項
    @Override
    public void onPageSelected(int position) {
        TeacTabLayout.getTabAt(position).select();
        setTitle(titles[position]);
        page = titles[position];
        rebuildmenu();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    @Override
    public void onPageScrollStateChanged(int state) {}
    //監聽ViewPaper
    @Override
    public void onTabSelected(TabLayout.Tab tab) {TeacViewPager.setCurrentItem(tab.getPosition());}
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}
    @Override
    public void onTabReselected(TabLayout.Tab tab) {}
    //監聽TabLayout
}

