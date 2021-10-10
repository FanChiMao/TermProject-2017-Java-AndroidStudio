package com.project.ee104b.ee_project;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static android.widget.Toast.makeText;


/**
 * A simple {@link Fragment} subclass.
 */
public class fg_teac_question extends Fragment {

    private static final String[] TITLE_q= new String[]{"Subject", "SUM(Question_Number)", "Latest_Update"};
    private ListView lv;
    private SimpleCursorAdapter adapter;
    private Cursor cur;
    private String choice_sub;
    private Toast toast;

    public fg_teac_question() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teac_question, container, false);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        show_sub();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                Sub_to_Chapter(position);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                deleteSub(position);
                return false;
            }
        });

    }

    private void show_sub(){
        Question_DBHelper DBH = new Question_DBHelper(getActivity());
        cur = DBH.Search_Subject();
        lv = (ListView)getView().findViewById(R.id.Subject_lv);
        adapter  = new SimpleCursorAdapter(getActivity(),R.layout.st_sub_lv,cur,TITLE_q, new int[] {R.id.subject, R.id.all_q, R.id.latestupdate}, 0);
        lv.setDivider(null);
        lv.setAdapter(adapter);
    }

    private void Sub_to_Chapter(int position){
        cur.moveToPosition(position);
        choice_sub = cur.getString(cur.getColumnIndex(TITLE_q[0]));
        Intent it = new Intent(getActivity(), Teac_chapter.class);
        it.putExtra("科目", choice_sub);
        startActivity(it);
    }
    public void onResume(){
        super.onResume();
        show_sub();
    }
    private void deleteSub(int position){

        Question_DBHelper DB = new Question_DBHelper(getActivity());
        final Cursor cursor = DB.Search_Subject();
        cursor.moveToPosition(position);
        choice_sub = cursor.getString(cursor.getColumnIndex(TITLE_q[0]));

        AlertDialog.Builder bdr = new AlertDialog.Builder(getActivity());
        bdr.setTitle("確定要刪除所有"+"「"+choice_sub+"」"+"嗎?")
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Question_DBHelper DB = new Question_DBHelper(getActivity());
                        final Cursor cursor_subject = DB.ThirdCursor(choice_sub);
                        if (cursor_subject.moveToFirst())
                            do{
                                DB.Delete(cursor_subject.getInt(0));
                            }
                            while (cursor_subject.moveToNext()) ;
                        show_sub();
                        makeTextAndShow(getActivity(), "刪除成功~!", Toast.LENGTH_SHORT);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_menu_delete)
                .show();
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
