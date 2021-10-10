package com.project.ee104b.ee_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

public class class_people extends AppCompatActivity {

    private String Class_get;
    private ListAdapter adapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_people);

        StudentData_DBHelper DB = new StudentData_DBHelper(this);
        Intent it = getIntent();
        Class_get = it.getStringExtra("班級");
        setTitle(Class_get);

        lv = (ListView) findViewById(R.id.lv);


        Cursor Total_Data = null;
        Total_Data = DB.TotalData();



        String Name =Total_Data.getColumnName(2);
        String Id = Total_Data.getColumnName(3);
        String GetPoint = Total_Data.getColumnName(4);
        String TotalPoint = Total_Data.getColumnName(5);
        String[] ColumnNames = {Name, Id, GetPoint, TotalPoint};

        Cursor cursor = DB.judgeByClass_2(Class_get);
        cursor.moveToFirst();

        adapter = new StudentSimpleCursorAdapter(this,
                R.layout.student_lv, cursor, ColumnNames, new int[] {
                R.id.Name, R.id.Id, R.id.score,R.id.Total },0);

        lv.setAdapter(adapter);
    }

}
