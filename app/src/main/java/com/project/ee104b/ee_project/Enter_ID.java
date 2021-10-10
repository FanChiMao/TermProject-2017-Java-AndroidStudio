package com.project.ee104b.ee_project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class Enter_ID extends Activity {

    public static Enter_ID instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_id);
        instance = this;
    }
    public void Enter_ID_bt(View v) {
        RadioGroup RG_ID = (RadioGroup) findViewById(R.id.RG_ID);
        Intent main_intent;
        if (RG_ID.getCheckedRadioButtonId() == R.id.ID_Teac) {
            main_intent = new Intent(Enter_ID.this, Teac_name.class);
            startActivity(main_intent);
        }
        if (RG_ID.getCheckedRadioButtonId() == R.id.ID_Stud) {
            main_intent = new Intent(Enter_ID.this, Student_name.class);
            startActivity(main_intent);
        }
    }

}
