package com.project.ee104b.ee_project;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by YiKai on 2017/11/15.
 */

public class ScoreCursorAdapter extends SimpleCursorAdapter {
    ScoreCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to){
        super(context, layout ,c ,from , to);
    }
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        updateProgressbar(view, cursor);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void updateProgressbar(View view, Cursor cursor) {

        float c = cursor.getInt(cursor.getColumnIndex("SUM(Correct_Number)"));
        float all = cursor.getInt(cursor.getColumnIndex("SUM(Question_Number)"));
        DecimalFormat df = new DecimalFormat("0.0");
        String CR = df.format(c*100/all);

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        TextView Correct_rate = (TextView) view.findViewById(R.id.f_all_q);
        progressBar.setMax(cursor.getInt(cursor.getColumnIndex("SUM(Question_Number)")));
        progressBar.setProgress(cursor.getInt(cursor.getColumnIndex("SUM(Correct_Number)")));
        Correct_rate.setText(CR+"%");
    }
}
