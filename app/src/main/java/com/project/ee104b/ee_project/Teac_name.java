package com.project.ee104b.ee_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class Teac_name extends AppCompatActivity {

    EditText name;
    String Teacher;
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teac_name);

        name = (EditText)findViewById(R.id.teac_name);
    }
    public void enter_name(View v){
        if("".equals(name.getText().toString().trim())){
            return;
        }
        else {
            Teacher = name.getText().toString().trim();
            ID = "Teacher";
            Intent main_intent = new Intent(Teac_name.this, Main_Teac_Activity.class);
            main_intent.putExtra("名稱", Teacher);
            startActivity(main_intent);

            Enter_ID.instance.finish();

            finish();
        }
    }
    @Override

    protected  void onPause(){
        super.onPause();
        SharedPreferences pref = getSharedPreferences("身分", MODE_APPEND);
        pref.edit()
                .putString("稱謂", ID)
                .putString("名稱", Teacher)
                .commit();
    }
}
