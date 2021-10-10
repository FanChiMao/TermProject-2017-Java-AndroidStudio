package com.project.ee104b.ee_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YiKai on 2017/10/21.
 */

public class Question_DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final String DB_NAME = "Questions";
    private static final String TB_NAME = "questions";
    private static final int DB_VERSION = 1;


    Question_DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase db) {String createTable = "CREATE TABLE IF NOT EXISTS " + TB_NAME +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Subject TEXT, " +
            "Chapter TEXT, " +
            "Question_Number INTEGER, " +
            "Correct_Number INTEGER, " +
            "Question_Title TEXT," +
            "Question_Context TEXT, " +
            "Answer_Context_A TEXT, " +
            "Answer_Context_B TEXT, " +
            "Answer_Context_C TEXT, " +
            "Answer_Context_D TEXT, " +
            "Correct_Answer TEXT, " +
            "Time_Setting INTEGER," +
            "Score INTEGER," +
            "Latest_Update TEXT)";//17
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String createTable = "DROP TABLE" + TB_NAME;
        db.execSQL(createTable);
    }
    public void addData(String Subject,
                         String Chapter,
                         int Question_Number,
                         int Correct_Number,
                         String Question_Title,
                         String Question_Context,
                         String Answer_Context_A,
                         String Answer_Context_B,
                         String Answer_Context_C,
                         String Answer_Context_D,
                         String Correct_Answer,
                         int Time_Setting,
                         int Score,
                         String Latest_Update) {
        /*匯入database*/
        ContentValues cv = new ContentValues();
        cv.put("Subject", Subject);
        cv.put("Chapter", Chapter);                                 //老師/學生
        cv.put("Question_Number", Question_Number); //老師/學生
        cv.put("Correct_Number", Correct_Number);   //學生
        cv.put("Question_Title", Question_Title);                   //老師/學生
        cv.put("Question_Context", Question_Context);               //老師/學生
        cv.put("Answer_Context_A", Answer_Context_A);               //老師/學生
        cv.put("Answer_Context_B", Answer_Context_B);               //老師/學生
        cv.put("Answer_Context_C", Answer_Context_C);               //老師/學生
        cv.put("Answer_Context_D", Answer_Context_D);               //老師/學生
        cv.put("Correct_Answer", Correct_Answer);                   //老師/學生
        cv.put("Time_Setting", Time_Setting);                       //老師
        cv.put("Score", Score);                                     //老師
        cv.put("Latest_Update", Latest_Update);                     //老師/學生
        db.insert(TB_NAME, null, cv);
    }
    private void TotalData() {
        addData("數學","加法",1,1,"加法1","1+1=?","1","2","3","4",          "2",  10  , 2  ,    "2017-10-25");
        addData("數學","加法",1,0,"加法2","2+2=?","1","2","3","4",          "4",   30,4 ,       "2017-10-26");
        addData("數學","加法",1,1,"加法3","3+3=?","2","4","6","8",          "6",    10,2 ,      "2017-10-27");
        addData("數學","乘法",1,0,"乘法1","2*2=?","1","2","3","4",          "4",     10,9  ,    "2017-10-24");
        addData("國文","國字",1,1,"國字1","國...Q1","A1","A2","A3","A4",    "A2",  9 ,6 ,      "2017-10-18");
        addData("國文","國字",1,0,"國字2","國...Q2","A1","A2","A3","A4",    "A4",   6,9,       "2017-11-10");
        addData("英文","拼音",1,1,"拼音1","英...Q1","A1","A2","A3","A4",    "A1",    5,5,      "2017-10-19");
        addData("英文","拼音",1,1,"拼音2","英...Q2","A1","A2","A3","A4",    "A2",    5,5,      "2017-10-20");
        addData("英文","對話",1,1,"對話1","英...Q3","A1","A2","A3","A4",    "A1",     10,1,     "2017-10-21");
        addData("數學","乘法",1,1,"乘法2","100*2=?","100","200","300","400","200", 10,2,   "2017-10-24");
        addData("數學","加法",1,1,"加法4","5+6+9=?","10","20","30","40",    "20",  5,6,   "2017-10-27");
        addData("地理","國家",1,0,"地理1","台灣首都?","台北","高雄","新竹","桃園","台北", 6,6,  "2017-10-31");
        /*
        addData("數學","加法",1,1,"加法1","1+1=?","1","2","3","4","2",30,10,"2017-10-25");
        addData("數學","加法",1,0,"加法2","2+2=?","1","2","3","4","4",30,10,"2017-10-26");
        addData("數學","加法",1,1,"加法3","3+3=?","2","4","6","8","6",30,10,"2017-10-27");
        addData("數學","乘法",1,0,"乘法1","2*2=?","1","2","3","4","4",30,10,"2017-10-24");
        addData("國文","國字",1,1,"國字1","國...Q1","A1","A2","A3","A4","A2",30,10,"2017-10-18");
        addData("國文","國字",1,0,"國字2","國...Q2","A1","A2","A3","A4","A4",30,10, "2017-11-10");
        addData("英文","拼音",1,1,"拼音1","英...Q1","A1","A2","A3","A4","A1",30,10,"2017-10-19");
        addData("英文","拼音",1,1,"拼音2","英...Q2","A1","A2","A3","A4","A 2",30,10,"2017-10-20");
        addData("英文","對話",1,1,"對話1","英...Q3","A1","A2","A3","A4","A1",30,10,"2017-10-21");
        addData("數學","乘法",1,1,"乘法2","100*2=?","100","200","300","400","200",30,10,"2017-10-24");
        addData("數學","加法",1,1,"加法4","5+6+9=?","10","20","30","40","20",30,10,"2017-10-27");
        addData("地理","國家",1,0,"地理1","台灣首都?","台北","高雄","新竹","桃園","台北",30,10,"2017-10-31");*/
    }


    Cursor Search_Subject() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT *,SUM(Correct_Number),SUM(Question_Number),MAX(Latest_Update) FROM questions GROUP BY Subject", null);
        return res;
    }
    Cursor Search_chapter(String Subject) {
        SQLiteDatabase db = this.getReadableDatabase();
        //ASC:由小到大 DESC:由大到小
        Cursor res = db.rawQuery("SELECT *,SUM(Correct_Number),SUM(Question_Number),MAX(Latest_Update) FROM questions WHERE Subject = ? GROUP BY Chapter ORDER BY MAX(Latest_Update) DESC ",
                new String[]{Subject});
        return res;
    }

    Cursor Search_ques(String Chapter) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT *,MAX(Latest_Update) FROM questions WHERE Chapter = ? GROUP BY Question_Title ORDER BY Question_Title ASC ", new String[]{Chapter});
        return res;
    }

    Cursor ThirdCursor(String Subject) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res ;
        res = db.rawQuery("SELECT * FROM questions WHERE Subject = ?",
                new String[]{Subject});
        return res;
    }

    Cursor ForthCursor(String Chapter) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res ;
        res = db.rawQuery("SELECT * FROM questions WHERE Chapter = ?",
                new String[]{Chapter});
        return res;
    }

    Cursor fetchChildren(String chapter) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res ;
        res = db.rawQuery("SELECT * FROM questions WHERE Chapter = ? ",
                new String[]{chapter});
        return res;
    }

    Cursor curToFinal(String subject,String chapter) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res ;
        res = db.rawQuery("SELECT * FROM questions WHERE Subject = ? AND Chapter = ? ",
                new String[]{subject, chapter});
        return res;
    }


    Cursor FindByQuestion(String question) {
        SQLiteDatabase db = this.getReadableDatabase();/*Order BY函數-->排序用*/
        Cursor res ;
        res = db.rawQuery("SELECT * FROM questions WHERE Question_Context = ? ",
                new String[]{question});
        return res;
    }

    public void Update(String Subject,
                       String Chapter,
                       String Question_Title,
                       String Question_Context,
                       String Answer_Context_A,
                       String Answer_Context_B,
                       String Answer_Context_C,
                       String Answer_Context_D,
                       String Correct_Answer,
                       int Time_Setting,
                       int Score,
                       String latesttime,
                       int id)
    {
        ContentValues cv = new ContentValues(13);
        cv.put("Subject", Subject);
        cv.put("Chapter", Chapter);
        cv.put("Question_Title", Question_Title);
        cv.put("Question_Context", Question_Context);
        cv.put("Answer_Context_A", Answer_Context_A);
        cv.put("Answer_Context_B", Answer_Context_B);
        cv.put("Answer_Context_C", Answer_Context_C);
        cv.put("Answer_Context_D", Answer_Context_D);
        cv.put("Correct_Answer", Correct_Answer);
        cv.put("Time_Setting", Time_Setting);
        cv.put("Score", Score);
        cv.put("Latest_Update", latesttime);

        db.update(TB_NAME, cv,"_id="+id, null);
    }

    public void TeacherAddData(String Subject,
                               String Chapter,
                               String Question_Title,
                               String Question_Context,
                               String Answer_Context_A,
                               String Answer_Context_B,
                               String Answer_Context_C,
                               String Answer_Context_D,
                               String Correct_Answer,
                               int Time_Setting,
                               int Score,
                               String Latest_Time,
                               int Question_Number
    ) {

        ContentValues cv = new ContentValues(14);
        cv.put("Subject", Subject);
        cv.put("Chapter", Chapter);
        cv.put("Question_Title", Question_Title);
        cv.put("Question_Context", Question_Context);
        cv.put("Answer_Context_A", Answer_Context_A);
        cv.put("Answer_Context_B", Answer_Context_B);
        cv.put("Answer_Context_C", Answer_Context_C);
        cv.put("Answer_Context_D", Answer_Context_D);
        cv.put("Correct_Answer", Correct_Answer);
        cv.put("Time_Setting", Time_Setting);
        cv.put("Score", Score);
        cv.put("Latest_Update", Latest_Time);
        cv.put("Question_Number", Question_Number);
        db.insert(TB_NAME, null, cv);
    }

    public void Delete(int id)
    {
        db.delete(TB_NAME, "_id="+id, null);
    }
}
