package com.project.ee104b.ee_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class choose_quse extends AppCompatActivity {

    private SimpleCursorAdapter sAdapter,cAdapter,qAdapter;
    private String[] Sub_TITLE = new String[]{"Subject"};
    private String[] Cha_TITLE = new String[]{"Chapter"};
    private String[] Ques_TITLE = new String[]{"Question_Title"};
    private Spinner sub_spinner,cha_spinner,ques_spinner;
    private TextView time_set,score,question;
    private String class_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_quse);

        setTitle("選擇考試題目");

        final Intent it = getIntent();
        class_name=it.getStringExtra("班級");

        time_set=(TextView)findViewById(R.id.time_set);
        score=(TextView)findViewById(R.id.score);
        question=(TextView)findViewById(R.id.question);



        Set_spinner();


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
                //EditText En_Subject = (EditText) findViewById(R.id.En_Subject);
                //En_Subject.setText(subject);
                final Cursor C = DBH.Search_chapter(subject);
                cAdapter=new SimpleCursorAdapter(choose_quse.this, android.R.layout.simple_spinner_item, C,Cha_TITLE, new int[]{android.R.id.text1},0);
                cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cha_spinner = (Spinner) findViewById(R.id.cha_sp);
                cha_spinner.setAdapter(cAdapter);
                cha_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        C.moveToPosition(position);
                        String chapter = C.getString(C.getColumnIndex("Chapter"));
                        final Cursor Q = DBH.Search_ques(chapter);
                        qAdapter=new SimpleCursorAdapter(choose_quse.this, android.R.layout.simple_spinner_item,Q,Ques_TITLE,new int[]{android.R.id.text1},0);
                        qAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ques_spinner = (Spinner) findViewById(R.id.ques_sp);
                        ques_spinner.setAdapter(qAdapter);
                        ques_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Q.moveToPosition(position);
                                question.setText(Q.getString(Q.getColumnIndex("Question_Context")));
                                time_set.setText(Q.getString(Q.getColumnIndex("Time_Setting")));
                                score.setText(Q.getString(Q.getColumnIndex("Score")));

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
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void onClick(View v){
        Intent it = new Intent(this, Test.class);
        it.putExtra("題目",  question.getText());
        it.putExtra("班級", class_name);
        startActivity(it);
        finish();
    }
}
