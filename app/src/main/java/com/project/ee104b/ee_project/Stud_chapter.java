package com.project.ee104b.ee_project;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Toolbar;


public class Stud_chapter extends AppCompatActivity {

    Intent next_it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_chapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        final Intent it = getIntent();
        String subject = it.getStringExtra("科目");
        setTitle(subject);

        fillData();
    }

    private void setSupportActionBar(Toolbar toolbar) {
        return;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    SimpleCursorTreeAdapter mAdapter;
    private void fillData() {
        final Intent it = getIntent();
        final String subject = it.getStringExtra("科目");

        final Question_DBHelper DBH = new Question_DBHelper(this);
        final Cursor cur = DBH.Search_chapter(subject);
        //String ChapterNumber = cur.getString(cur.getColumnIndex("Question_Chapter_Number"));
        //number.setText(ChapterNumber);
        final ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);

        mAdapter = new MyExpandableListAdapter(cur, this,
                R.layout.chapter_lv,                     // Your row layout for a group
                R.layout.st_ques_lv,                 // Your row layout for a child
                new String[]{"Chapter","SUM(Question_Number)"},                      // Field(s) to use from group cursor
                new int[]{R.id.chapter, R.id.ch_total_num},                 // Widget ids to put group data into
                /*這其實是Question_Context(Chapter)*/
                new String[]{"Question_Title","Latest_Update"},  // Field(s) to use from child cursors
                new int[]{R.id.ques_title,R.id.up_time});          // Widget ids to put child data into


        elv.setAdapter(mAdapter);
        elv.setGroupIndicator(null);//隱藏Expandable下拉圖示
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                next_it = new Intent(Stud_chapter.this, Stud_ques.class);
                final Cursor getChapter = DBH.Search_chapter(subject);
                getChapter.moveToPosition(groupPosition);
                String chapter = getChapter.getString(getChapter.getColumnIndex("Chapter"));

                final Cursor curToFinal = DBH.curToFinal(subject, chapter);

                curToFinal.moveToPosition(childPosition);

                String Question_title = curToFinal.getString(curToFinal.getColumnIndex("Question_Title"));
                next_it.putExtra("問題標題", Question_title);
                String Question =curToFinal.getString(curToFinal.getColumnIndex("Question_Context"));
                next_it.putExtra("問題", Question);
                String Ans_A = curToFinal.getString(curToFinal.getColumnIndex("Answer_Context_A"));
                next_it.putExtra("a", Ans_A);
                String Ans_B = curToFinal.getString(curToFinal.getColumnIndex("Answer_Context_B"));
                next_it.putExtra("b", Ans_B);
                String Ans_C =curToFinal.getString(curToFinal.getColumnIndex("Answer_Context_C"));
                next_it.putExtra("c", Ans_C);
                String Ans_D =curToFinal.getString(curToFinal.getColumnIndex("Answer_Context_D"));
                next_it.putExtra("d", Ans_D);

                String ANSWER =curToFinal.getString(curToFinal.getColumnIndex("Correct_Answer"));
                next_it.putExtra("answer", ANSWER);

                startActivity(next_it);
                return true;
            }
        });
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        /*
        elv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = elv.getExpandableListAdapter().getGroupCount();
                for(int j = 0; j < count; j++){
                    if(j != groupPosition){
                        elv.collapseGroup(j);
                    }
                }
            }
        });收起其餘ist*/
    }

    public class MyExpandableListAdapter extends SimpleCursorTreeAdapter {

        ImageView imageView;MyExpandableListAdapter(Cursor cursor, Context context, int groupLayout,
                                                    int childLayout, String[] groupFrom, int[] groupTo, String[] childrenFrom, int[] childrenTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childrenFrom, childrenTo);
        }

        @Override
        protected void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild) {
            super.bindChildView(view, context, cursor, isLastChild);
            setPicture(view, cursor);
        }

        private void setPicture(View view, Cursor cursor) {
            imageView = (ImageView) view.findViewById(R.id.con);
            if (cursor.getInt(cursor.getColumnIndex("Correct_Number")) == 1) {
                imageView.setImageResource(R.drawable.correct);
            }
            else
                imageView.setImageResource(R.drawable.nocorrect);
        }

        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            Question_DBHelper DBH = new Question_DBHelper(Stud_chapter.this);
            Cursor childCursor = DBH.fetchChildren(groupCursor.getString(groupCursor.getColumnIndex("Chapter")));
            childCursor.moveToFirst();
            return childCursor;
        }
    }
}
