package com.project.ee104b.ee_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YiKai on 2018/3/20.
 */

public class StudentData_DBHelper extends SQLiteOpenHelper{
    private SQLiteDatabase db;
    private static final String DB_NAME = "Class";
    private static final String TB_NAME = "StudentData";
    private static final int DB_VERSION = 1;


    StudentData_DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {String createTable = "CREATE TABLE IF NOT EXISTS " + TB_NAME +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Class TEXT, " +
            "Student_Name TEXT, " +
            "Student_Id TEXT, " +
            "Getting_Score INTEGER, " +
            "Total_Score INTEGER, " +
            "Testing_Date TEXT, " +
            "Student_CorrectOrNot INTEGER, " +//這只是用來算總分
            "Student_Number TEXT, " +
            "Blank_3 TEXT, " +
            "Blank_4 TEXT)";        //11
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String createTable = "DROP TABLE" + TB_NAME;
        db.execSQL(createTable);
    }

    private void addData(String Class,
                         String Student_Name,
                         String Student_Id,
                         int Getting_Score,
                         int Total_Score,
                         String Testing_Date,
                         int Student_CorrectOrNot
    )
    {
        /*匯入database-----for test*/
        ContentValues cv = new ContentValues(7);
        cv.put("Class", Class);
        cv.put("Student_Name", Student_Name);
        cv.put("Student_Id", Student_Id);
        cv.put("Getting_Score", Getting_Score);     //這個人考過對的總分數
        cv.put("Total_Score", Total_Score);         //這個人考過的題目的總分數
        cv.put("Testing_Date", Testing_Date);
        cv.put("Student_CorrectOrNot", Student_CorrectOrNot);
        cv.put("Student_Number", 1);
        db.insert(TB_NAME, null, cv);
    }



    Cursor TotalData() {

        SQLiteDatabase db = this.getReadableDatabase();/*Order BY函數-->排序用*/
        Cursor res = db.rawQuery("SELECT * FROM StudentData ORDER BY Student_Id ASC", null);
        if (res.getCount()== 0) {//沒資料就加入

        }
        res = db.rawQuery("SELECT * FROM StudentData ORDER BY Student_Id ASC", null);
        return res;
    }

    Cursor judgeByClass() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT *,SUM(Student_Number) FROM StudentData GROUP BY Class", null);
        if (res.getCount()== 0) {//沒資料就加入

        }
        res = db.rawQuery("SELECT *,SUM(Student_Number) FROM StudentData GROUP BY Class", null);//重新查詢
        return res;
    }

    Cursor judgeByClass_2(String Class) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM StudentData WHERE Class = ?",
                new String[]{Class});

        return res;
    }

    Cursor Search_Attend(String Class,String Student_Id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM StudentData WHERE Class = ? AND Student_Id = ? ",
                new String[]{Class, Student_Id});

        return res;
    }

    Cursor Search_Class() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM StudentData GROUP BY Class", null);
        return res;
    }

    public void FirstGetLatestData
            (//第一次加入學生資料
             String Class,
             String Student_Name,
             String Student_Id,
             int This_Question_Score,
             String Testing_Date,
             int Student_CorrectOrNot
            ) {

        int total_Point = This_Question_Score * Student_CorrectOrNot;
        addData(Class,                      //***
                Student_Name,               //***
                Student_Id,                 //***
                total_Point, //***-->這欄是學生這題考對的分數，別搞混    ex:5  -->這一題5分
                This_Question_Score,        //   -->這欄是學生考過的分數總合，注意!(addData()再看一遍)
                Testing_Date,               //***
                Student_CorrectOrNot);
    }



    public void SecondGetLatestData
            (String Class,
             String Student_Name,
             String Student_Id,
             int This_Question_Score,
             String Testing_Date,
             int Student_CorrectOrNot,
             int id
            )
    {

        SQLiteDatabase db = this.getReadableDatabase();/*Order BY函數-->排序用*/
        Cursor res = db.rawQuery("SELECT * FROM StudentData Where Class = ? AND Student_Id = ? ",
                new String[]{Class, Student_Id});
        res.moveToFirst();
        int Getting_Score = res.getInt(res.getColumnIndex("Getting_Score"))
                + This_Question_Score * Student_CorrectOrNot;
        int Total_Score = res.getInt(res.getColumnIndex("Total_Score"))
                + This_Question_Score;



        ContentValues cv = new ContentValues(8);
        cv.put("Class", Class);
        cv.put("Student_Name", Student_Name);
        cv.put("Student_Id", Student_Id);
        cv.put("Getting_Score", Getting_Score);     //這個人考過對的總分數
        cv.put("Total_Score", Total_Score);         //這個人考過的題目的總分數
        cv.put("Testing_Date", Testing_Date);
        cv.put("Student_CorrectOrNot", Student_CorrectOrNot);

        db.update(TB_NAME, cv,"_id="+id, null);
    }

    public void Delete(int id)
    {

        db.delete(TB_NAME, "_id="+id, null);
    }

}