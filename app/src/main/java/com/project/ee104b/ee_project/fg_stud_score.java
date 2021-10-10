package com.project.ee104b.ee_project;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class fg_stud_score extends Fragment {

    private static final String[] TITLE_q= new String[]{"Subject", "SUM(Question_Number)", "SUM(Correct_Number)"};
    private ListView lv;
    private SimpleCursorAdapter adapter;
    private Cursor cur;

    private TextView Stu_name;
    private TextView stu_num;

    public fg_stud_score() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stud_score, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        setname();
        show_score();

    }
    private void setname(){
        Stu_name = (TextView) getView().findViewById(R.id.stu_name);
        stu_num = (TextView) getView().findViewById(R.id.stu_num);
        Stu_name.setText(getActivity().getIntent().getStringExtra("名稱"));
        stu_num.setText(getActivity().getIntent().getStringExtra("學號"));
    }
    private void show_score(){
        Question_DBHelper DBH = new Question_DBHelper(getActivity());
        cur = DBH.Search_Subject();
        lv = (ListView)getView().findViewById(R.id.Sub_score);
        adapter  = new ScoreCursorAdapter(getActivity(),R.layout.sub_sc_lv,cur,TITLE_q, new int[] {R.id.subject, R.id.all_q, R.id.t_all_q});
        lv.setAdapter(adapter);
    }
}
