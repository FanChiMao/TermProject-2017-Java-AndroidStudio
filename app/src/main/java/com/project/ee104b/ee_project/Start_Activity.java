package com.project.ee104b.ee_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Start_Activity extends Activity {
    private String ID;
    private String Name;
    private String St_ID;
    private Intent main_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);  //執行歡迎畫面

        new Handler().postDelayed(new Runnable() {
            @Override
            //開始main_activity
            public void run() {
                check_ID();
            }
        }, 2000);  //此UI執行兩秒
    }
    protected void check_ID(){
        SharedPreferences pref = getSharedPreferences("身分", MODE_APPEND);
        ID = pref.getString("稱謂", null);
        Name = pref.getString("名稱", null);
        St_ID = pref.getString("學號", null);

        if(ID == null){
            main_intent = new Intent(Start_Activity.this, Enter_ID.class);
            startActivity(main_intent);
            finish();
        }
        else{
            if (ID.equals("Teacher")) {
                main_intent = new Intent(Start_Activity.this, Main_Teac_Activity.class);
                main_intent.putExtra("名稱", Name);
                startActivity(main_intent);
                finish();
            }
            else {
                main_intent = new Intent(Start_Activity.this, Main_Stud_Activity.class);
                main_intent.putExtra("名稱", Name);
                main_intent.putExtra("學號", St_ID);
                startActivity(main_intent);
                finish();
            }
        }
    }
}
