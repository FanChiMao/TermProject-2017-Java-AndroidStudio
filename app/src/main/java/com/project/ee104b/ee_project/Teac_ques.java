package com.project.ee104b.ee_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import static android.widget.Toast.makeText;

public class Teac_ques extends AppCompatActivity implements DialogInterface.OnClickListener {

    private Intent it;
    private String a,b,c,d,ans,Question;
    private TextView Cor_ans,Question_text,A,B,C,D,time_set;
    private Cursor Show_ques;
    private Intent new_it;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teac_ques);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        it = getIntent();

        Question = it.getStringExtra("更新題目");
        if (Question == null) {
            Question = it.getStringExtra("問題");
        }

        Question_text = (TextView) findViewById(R.id.Question_text);
        A = (TextView) findViewById(R.id.Ans_A);
        B = (TextView) findViewById(R.id.Ans_B);
        C = (TextView) findViewById(R.id.Ans_C);
        D = (TextView) findViewById(R.id.Ans_D);
        Cor_ans = (TextView) findViewById(R.id.Cor_Ans);
        time_set = (TextView) findViewById(R.id.time_set);


        show_ques();
    }

    private void setSupportActionBar(Toolbar toolbar) {
        return;
    }

    public void show_ques() {

        Question_DBHelper DBH = new Question_DBHelper(this);
        Show_ques = DBH.FindByQuestion(Question);
        Show_ques.moveToFirst();


        a = Show_ques.getString(Show_ques.getColumnIndex("Answer_Context_A"));
        b = Show_ques.getString(Show_ques.getColumnIndex("Answer_Context_B"));
        c = Show_ques.getString(Show_ques.getColumnIndex("Answer_Context_C"));
        d = Show_ques.getString(Show_ques.getColumnIndex("Answer_Context_D"));

        setTitle(Show_ques.getString(Show_ques.getColumnIndex("Question_Title")));
        Question_text.setText(Question);
        A.setText("(A) " + a);
        B.setText("(B) " + b);
        C.setText("(C) " + c);
        D.setText("(D) " + d);
        ans = Show_ques.getString(Show_ques.getColumnIndex("Correct_Answer"));
        time_set.setText("時間 : "+Show_ques.getString(Show_ques.getColumnIndex("Time_Setting")));

        if (ans.equals(a))
            Cor_ans.setText("解答 (A)");
        else if (ans.equals(b))
            Cor_ans.setText("解答 (B)");
        else if (ans.equals(c))
            Cor_ans.setText("解答 (C)");
        else if (ans.equals(d))
            Cor_ans.setText("解答 (D)");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.te_ques_show, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_correct) {
            it = new Intent(this, EditQuestion.class);
            it.putExtra("新增", "old");
            it.putExtra("Question", Question);
            startActivity(it);
            finish();
            return true;
        }
        if (id == R.id.action_delete) {
            AlertDialog.Builder bdr = new AlertDialog.Builder(this);
            bdr.setTitle("確定要刪除?")
                    .setPositiveButton("是", this)
                    .setNegativeButton("否", this)
                    .setIcon(android.R.drawable.ic_menu_delete)
                    .show();
            return true;
        }
        else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            final Intent it4 = getIntent();
            String Question1 = it4.getStringExtra("問題");
            Question_DBHelper DBH = new Question_DBHelper(this);
            Cursor cursor = DBH.FindByQuestion(Question1);
            cursor.moveToFirst();//很重要!!!!!!!!!
            DBH.Delete(cursor.getInt(0));
            makeTextAndShow(this, "刪除成功~!", Toast.LENGTH_SHORT);
            Teac_ques.this.finish();
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            return;
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
