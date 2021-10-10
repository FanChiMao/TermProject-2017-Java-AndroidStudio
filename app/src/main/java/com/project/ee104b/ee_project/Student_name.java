package com.project.ee104b.ee_project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Student_name extends AppCompatActivity {

    private EditText name;
    private EditText num;
    private String Student;
    private String Student_num;
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_name);

        name = (EditText)findViewById(R.id.stud_name);
        num = (EditText)findViewById(R.id.stud_num);
        ID = null;
    }
    public void enter_name(View v){
        if("".equals(name.getText().toString().trim())){
            return;
        }
        else if("".equals(num.getText().toString().trim())){
            return;
        }
        else {
            Student = name.getText().toString().trim();
            Student_num = num.getText().toString().trim();
            ID = "Student";
            Intent main_intent = new Intent(Student_name.this, Main_Stud_Activity.class);
            main_intent.putExtra("名稱", Student);
            main_intent.putExtra("學號", Student_num);
            startActivity(main_intent);

            Enter_ID.instance.finish();

            finish();
        }
    }
    @Override
    protected  void onPause(){
        super.onPause();
        SharedPreferences pref = getSharedPreferences("身分", MODE_PRIVATE);
        pref.edit()
                .putString("稱謂", ID)
                .putString("名稱", Student)
                .putString("學號", Student_num)
                .commit();
    }
}
