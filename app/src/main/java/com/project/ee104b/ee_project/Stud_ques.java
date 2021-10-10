package com.project.ee104b.ee_project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Objects;

import static android.widget.Toast.makeText;

public class Stud_ques extends AppCompatActivity {

    private Intent it;
    private static Toast toast;
    private  String a,b,c,d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_ques);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        it = getIntent();
        setTitle(it.getStringExtra("問題標題"));
        String Question = it.getStringExtra("問題");

        TextView Question_text = (TextView) findViewById(R.id.Question_text);
        TextView A = (TextView) findViewById(R.id.Ans_A);
        TextView B = (TextView) findViewById(R.id.Ans_B);
        TextView C = (TextView) findViewById(R.id.Ans_C);
        TextView D = (TextView) findViewById(R.id.Ans_D);


        a = it.getStringExtra("a");
        b = it.getStringExtra("b");
        c = it.getStringExtra("c");
        d = it.getStringExtra("d");

        Question_text.setText(Question);
        A.setText("(A) " + a);
        B.setText("(B) " + b);
        C.setText("(C) " + c);
        D.setText("(D) " + d);
    }

    private void setSupportActionBar(Toolbar toolbar) {
        return;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.st_show_answer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.show_ans) {
            ANSWER();
            return true;
        }
        else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ANSWER() {
        String answer = it.getStringExtra("answer");
        if (Objects.equals(answer, a))
            makeTextAndShow(this, "(A)", Toast.LENGTH_SHORT);
        else if (Objects.equals(answer, b))
            makeTextAndShow(this, "(B)", Toast.LENGTH_SHORT);
        else if (Objects.equals(answer, c))
            makeTextAndShow(this, "(C)", Toast.LENGTH_SHORT);
        else if (Objects.equals(answer, d))
            makeTextAndShow(this, "(D)", Toast.LENGTH_SHORT);

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
