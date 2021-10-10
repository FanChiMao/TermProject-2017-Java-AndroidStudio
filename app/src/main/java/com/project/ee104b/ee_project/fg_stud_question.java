package com.project.ee104b.ee_project;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static android.widget.Toast.makeText;


/**
 * A simple {@link Fragment} subclass.
 */
public class fg_stud_question extends Fragment {
    private static final String[] TITLE_q = new String[]{"Subject", "SUM(Question_Number)", "Latest_Update"};
    private ListView lv;
    private SimpleCursorAdapter adapter;
    private Cursor cur;
    private Intent it;
    private String choice_sub;

    public fg_stud_question() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stud_question, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        show_sub();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                Sub_to_Chapter(position);
            }
        });

    }

    private void show_sub() {
        Question_DBHelper DBH = new Question_DBHelper(getActivity());
        cur = DBH.Search_Subject();
        lv = (ListView) getView().findViewById(R.id.Subject_lv);
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.st_sub_lv, cur, TITLE_q, new int[]{R.id.subject, R.id.all_q, R.id.latestupdate}, 0);
        lv.setAdapter(adapter);
    }

    private void Sub_to_Chapter(int position) {
        cur.moveToPosition(position);
        choice_sub = cur.getString(cur.getColumnIndex(TITLE_q[0]));
        it = new Intent(getActivity(), Stud_chapter.class);
        it.putExtra("科目", choice_sub);
        startActivity(it);
    }

}
