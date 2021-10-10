package com.project.ee104b.ee_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static android.widget.Toast.makeText;

public class EditQuestion extends AppCompatActivity {

    private Intent it;
    private Toast toast;
    private SimpleCursorAdapter sAdapter,cAdapter;
    private String[] Sub_TITLE = new String[]{"Subject"};
    private String[] Cha_TITLE = new String[]{"Chapter"};
    private Spinner sub_spinner,cha_spinner;
    private String Edit_mode,Question;
    private EditText En_Subject,En_Chapter,En_Q_title,En_Question,En_A_ans,En_B_ans,En_C_ans,En_D_ans,En_Score,En_time;
    private Checkable CB_A,CB_B,CB_C,CB_D;
    private String subject,chapter,q_title,question,ans_a,ans_b,ans_c,ans_d,score,time,answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        it = getIntent();
        Edit_mode = it.getStringExtra("新增");
        Question = it.getStringExtra("Question");

        BuildView();

        if(Edit_mode.equals("new")){
            setTitle("新增題目...");
            Set_spinner();
        }
        else if(Edit_mode.equals("old")){
            setTitle("修改題目...");

            final Question_DBHelper DBH = new Question_DBHelper(this);
            Cursor JudgeQus = DBH.FindByQuestion(Question);//設定cursor查詢
            JudgeQus.moveToFirst();

            En_Subject.setText(JudgeQus.getString(JudgeQus.getColumnIndex("Subject")));
            En_Chapter.setText(JudgeQus.getString(JudgeQus.getColumnIndex("Chapter")));
            En_Q_title.setText(JudgeQus.getString(JudgeQus.getColumnIndex("Question_Title")));
            En_Question.setText(JudgeQus.getString(JudgeQus.getColumnIndex("Question_Context")));
            En_A_ans.setText(JudgeQus.getString(JudgeQus.getColumnIndex("Answer_Context_A")));
            En_B_ans.setText(JudgeQus.getString(JudgeQus.getColumnIndex("Answer_Context_B")));
            En_C_ans.setText(JudgeQus.getString(JudgeQus.getColumnIndex("Answer_Context_C")));
            En_D_ans.setText(JudgeQus.getString(JudgeQus.getColumnIndex("Answer_Context_D")));
            En_Score.setText(JudgeQus.getString(JudgeQus.getColumnIndex("Score")));
            En_time.setText(JudgeQus.getString(JudgeQus.getColumnIndex("Time_Setting")));
            answer = JudgeQus.getString(JudgeQus.getColumnIndex("Correct_Answer"));

            if (Objects.equals(answer, JudgeQus.getString(JudgeQus.getColumnIndex("Answer_Context_A"))))//盒子哪個勾
                CB_A.setChecked(true);
            else if (Objects.equals(answer, JudgeQus.getString(JudgeQus.getColumnIndex("Answer_Context_B"))))
                CB_B.setChecked(true);
            else if (Objects.equals(answer, JudgeQus.getString(JudgeQus.getColumnIndex("Answer_Context_C"))))
                CB_C.setChecked(true);
            else if (Objects.equals(answer, JudgeQus.getString(JudgeQus.getColumnIndex("Answer_Context_D"))))
                CB_D.setChecked(true);
        }
    }

    private void setSupportActionBar(Toolbar toolbar) {
        return;
    }

    private void BuildView(){
        CB_A = (CheckBox) findViewById(R.id.CB_A);
        CB_B = (CheckBox) findViewById(R.id.CB_B);
        CB_C = (CheckBox) findViewById(R.id.CB_C);
        CB_D = (CheckBox) findViewById(R.id.CB_D);
        En_Subject = (EditText) findViewById(R.id.En_Subject);
        En_Chapter = (EditText) findViewById(R.id.En_Chapter);
        En_Q_title = (EditText) findViewById(R.id.En_Q_title);
        En_Question = (EditText) findViewById(R.id.En_Question);
        En_A_ans = (EditText) findViewById(R.id.En_A_ans);
        En_B_ans = (EditText) findViewById(R.id.En_B_ans);
        En_C_ans = (EditText) findViewById(R.id.En_C_ans);
        En_D_ans = (EditText) findViewById(R.id.En_D_ans);
        En_Score = (EditText) findViewById(R.id.En_Score);
        En_time = (EditText) findViewById(R.id.En_time);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Edit_mode.equals("new")){
            getMenuInflater().inflate(R.menu.add_q, menu);
        }
        else if(Edit_mode.equals("old")) {
            getMenuInflater().inflate(R.menu.rebuild_q, menu);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_ques) {
            try {
                Question_DBHelper DBH = new Question_DBHelper(this);

                subject = En_Subject.getText().toString().trim();
                chapter = En_Chapter.getText().toString().trim();
                q_title = En_Q_title.getText().toString().trim();
                question = En_Question.getText().toString().trim();
                ans_a = En_A_ans.getText().toString().trim();
                ans_b = En_B_ans.getText().toString().trim();
                ans_c = En_C_ans.getText().toString().trim();
                ans_d = En_D_ans.getText().toString().trim();
                score = En_Score.getText().toString().trim();
                time = En_time.getText().toString().trim();
                int score_int = Integer.valueOf(score);
                int time_int = Integer.valueOf(time);

                answer = "null";
                if (CB_A.isChecked()==true && CB_B.isChecked()==false && CB_C.isChecked()==false && CB_D.isChecked()==false)
                    answer = ans_a;
                else if (CB_B.isChecked() && CB_A.isChecked()==false &&  CB_C.isChecked()==false && CB_D.isChecked()==false)
                    answer = ans_b;
                else if (CB_C.isChecked()==true && CB_A.isChecked()==false && CB_B.isChecked()==false && CB_D.isChecked()==false)
                    answer = ans_c;
                else if (CB_D.isChecked()==true && CB_A.isChecked()==false && CB_B.isChecked()==false && CB_C.isChecked()==false)
                    answer = ans_d;
                else
                    answer = "null";

                if (answer.equals("null")) {
                    makeTextAndShow(this, "加入失敗\n請選擇一個答案", Toast.LENGTH_SHORT);
                }
                else {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//大小寫是固定的,一變會等於另一個它本身定義的數字
                    Date date = new Date(System.currentTimeMillis());
                    String latesttime = simpleDateFormat.format(date);


                    DBH.TeacherAddData(subject, chapter, q_title, question, ans_a, ans_b, ans_c, ans_d, answer, time_int, score_int, latesttime,
                            1);

                    makeTextAndShow(this, "~加入成功~", Toast.LENGTH_SHORT);
                    finish();
                }
            } catch (Exception obj) {
                makeTextAndShow(this, "加入失敗!!!", Toast.LENGTH_SHORT);
            }
            return true;
        }
        if (id == R.id.rebuild_ques) {
            Question_DBHelper DBH = new Question_DBHelper(this);
            Cursor cursor = DBH.FindByQuestion(Question);
            cursor.moveToFirst();//重要!!!


            subject = En_Subject.getText().toString().trim();
            chapter = En_Chapter.getText().toString().trim();
            q_title = En_Q_title.getText().toString().trim();
            question = En_Question.getText().toString().trim();
            ans_a = En_A_ans.getText().toString().trim();
            ans_b = En_B_ans.getText().toString().trim();
            ans_c = En_C_ans.getText().toString().trim();
            ans_d = En_D_ans.getText().toString().trim();
            score = En_Score.getText().toString().trim();
            time = En_time.getText().toString().trim();
            int score_int = Integer.valueOf(score);
            int time_int = Integer.valueOf(time);
            //判斷你勾哪個正確
            String answer = "null";

            if (CB_A.isChecked()==true && CB_B.isChecked()==false && CB_C.isChecked()==false && CB_D.isChecked()==false)
                answer = cursor.getString(cursor.getColumnIndex("Answer_Context_A"));
            else if (CB_B.isChecked() && CB_A.isChecked()==false &&  CB_C.isChecked()==false && CB_D.isChecked()==false)
                answer = cursor.getString(cursor.getColumnIndex("Answer_Context_B"));
            else if (CB_C.isChecked()==true && CB_A.isChecked()==false && CB_B.isChecked()==false && CB_D.isChecked()==false)
                answer = cursor.getString(cursor.getColumnIndex("Answer_Context_C"));
            else if (CB_D.isChecked()==true && CB_A.isChecked()==false && CB_B.isChecked()==false && CB_C.isChecked()==false)
                answer = cursor.getString(cursor.getColumnIndex("Answer_Context_D"));
            else
                answer = "null";

            //複選問題解決?
            if (answer.equals("null")){
                makeTextAndShow(this, "加入失敗\n請選擇一個答案", Toast.LENGTH_SHORT);
            }
            else {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                String latesttime = simpleDateFormat.format(date);

                DBH.Update(subject, chapter, q_title, question, ans_a, ans_b, ans_c, ans_d, answer, time_int, score_int, latesttime, cursor.getInt(0));

                Intent toHome = new Intent(EditQuestion.this, Teac_ques.class);
                toHome.putExtra("更新題目", question);
                startActivity(toHome);
                makeTextAndShow(this, "~更新成功~", Toast.LENGTH_SHORT);

                EditQuestion.this.finish();
            }
            return true;
        }
        else if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private  void Set_spinner(){
        final Question_DBHelper DBH = new Question_DBHelper(this);

        final Cursor cursor=DBH.Search_Subject();

        sAdapter=new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor,Sub_TITLE, new int[]{android.R.id.text1},0);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub_spinner = (Spinner) findViewById(R.id.sub_sp);
        sub_spinner.setAdapter(sAdapter);
        sub_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                String subject = cursor.getString(cursor.getColumnIndex("Subject"));
                En_Subject.setText(subject);
                final Cursor C = DBH.Search_chapter(subject);
                cAdapter=new SimpleCursorAdapter(EditQuestion.this,
                        android.R.layout.simple_spinner_item, C,Cha_TITLE, new int[]{android.R.id.text1},0);
                cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cha_spinner = (Spinner) findViewById(R.id.cha_sp);
                cha_spinner.setAdapter(cAdapter);
                cha_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        C.moveToPosition(position);
                        String chapter = C.getString(C.getColumnIndex("Chapter"));
                        En_Chapter.setText(chapter);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
