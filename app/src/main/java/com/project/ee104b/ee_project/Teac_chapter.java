package com.project.ee104b.ee_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import static android.widget.Toast.makeText;

public class Teac_chapter extends AppCompatActivity implements DialogInterface.OnClickListener {

    private Intent next_it;
    private String chapter;
    private Toast toast;


    @Override
    /*public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teac_chapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final Intent it = getIntent();
        String subject = it.getStringExtra("科目");
        setTitle(subject);

        Show_Chapter();
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
    private void Show_Chapter() {
        final Intent it = getIntent();
        final String subject = it.getStringExtra("科目");
        Cursor cur = null;
        final Question_DBHelper DBH = new Question_DBHelper(this);
        cur = DBH.Search_chapter(subject);
        if (cur.getCount() == 0){
            Teac_chapter.this.finish();
        }
        final ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);

        mAdapter = new MyExpandableListAdapter(cur, this,
                R.layout.chapter_lv,                     // Your row layout for a group
                R.layout.te_ques_lv,                 // Your row layout for a child
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

                next_it = new Intent(Teac_chapter.this, Teac_ques.class);
                final Cursor getChapter = DBH.Search_chapter(subject);
                getChapter.moveToPosition(groupPosition);
                String chapter = getChapter.getString(getChapter.getColumnIndex("Chapter"));
                final Cursor curToFinal = DBH.curToFinal(subject, chapter);
                curToFinal.moveToPosition(childPosition);
                String Question = curToFinal.getString(curToFinal.getColumnIndex("Question_Context"));
                next_it.putExtra("問題", Question);
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
        elv.setOnItemLongClickListener(new ExpandableListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                long packedPosition = elv.getExpandableListPosition(position);
                int itemType = ExpandableListView.getPackedPositionType(packedPosition);
                int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);

                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    //do your per-item callback here
                    return false; //true if we consumed the click, false if not

                } else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    Question_DBHelper DB = new Question_DBHelper(Teac_chapter.this);
                    final Intent it = getIntent();
                    final String subject = it.getStringExtra("科目");
                    final Cursor cursor_subject = DB.Search_chapter(subject);
                    cursor_subject.moveToPosition(groupPosition);
                    chapter= cursor_subject.getString(cursor_subject.getColumnIndex("Chapter"));
                    AlertDialog.Builder bdr = new AlertDialog.Builder(Teac_chapter.this);
                    bdr.setTitle("確定要刪除所有"+"「"+chapter+"」"+"?")
                            .setPositiveButton("確認", Teac_chapter.this)
                            .setNegativeButton("取消", Teac_chapter.this)
                            .setIcon(android.R.drawable.ic_menu_delete)
                            .show();
                    //do your per-group callback here
                    return true; //true if we consumed the click, false if not

                } else {
                    // null item; we don't consume the click
                    return false;
                }
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
        });收起其餘list*/
    }

    public class MyExpandableListAdapter extends SimpleCursorTreeAdapter {
        MyExpandableListAdapter(Cursor cursor, Context context, int groupLayout, int childLayout, String[] groupFrom, int[] groupTo, String[] childrenFrom, int[] childrenTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childrenFrom, childrenTo);
        }
        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            Question_DBHelper DBH = new Question_DBHelper(Teac_chapter.this);
            Cursor childCursor = DBH.fetchChildren(groupCursor.getString(groupCursor.getColumnIndex("Chapter")));
            childCursor.moveToFirst();
            return childCursor;
        }
    }
    protected void onResume(){
        super.onResume();
        Show_Chapter();
    }
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            Question_DBHelper DB = new Question_DBHelper(Teac_chapter.this);
            final Cursor cursor_chapter = DB.ForthCursor(chapter);
            cursor_chapter.moveToFirst();
            if (cursor_chapter.moveToFirst())
                do{
                    DB.Delete(cursor_chapter.getInt(0));
                }
                while (cursor_chapter.moveToNext()) ;
            makeTextAndShow(Teac_chapter.this, "刪除成功~!", Toast.LENGTH_SHORT);
            Show_Chapter();
            //好像還要再一行把原程式finish()
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            //沒事!
        }
    }
    public void makeTextAndShow(final Context context, final String text, final int duration) {
        if (toast == null) {
            //如果還沒有用過makeText方法，才使用
            toast = makeText(context, text, duration);
        } else {
            toast.setText(text);
            toast.setDuration(duration);
        }
        toast.show();
    }
}
