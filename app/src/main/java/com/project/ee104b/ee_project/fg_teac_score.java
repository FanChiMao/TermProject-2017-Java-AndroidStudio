package com.project.ee104b.ee_project;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


import static android.content.Context.MODE_APPEND;


/**
 * A simple {@link Fragment} subclass.
 */
public class fg_teac_score extends Fragment {

    private TextView tea_name;

    private SimpleCursorAdapter adapter;
    private ListView lv;
    private Cursor judgeByClass;
    public fg_teac_score() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teac_sorce, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        tea_name = (TextView) getView().findViewById(R.id.tea_name);
        lv=(ListView)getView().findViewById(R.id.lv);
        show_class();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                judgeByClass.moveToPosition(position);
                String class_name = judgeByClass.getString(judgeByClass.getColumnIndex("Class"));
                Intent it =new Intent(getActivity(), class_people.class);
                it.putExtra("班級", class_name);
                startActivity(it);
            }
        });

    }

    private void show_class(){
        final StudentData_DBHelper DB = new StudentData_DBHelper(getActivity());
        judgeByClass = DB.judgeByClass();
        judgeByClass.moveToFirst();
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.class_name, judgeByClass,
                new String[]{"Class","SUM(Student_Number)"},new int[]{R.id.class_name,R.id.people},0);
        lv.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences pref = getActivity().getSharedPreferences("身分", MODE_APPEND);
        tea_name.setText(pref.getString("名稱", null) + " 教授");

    }
    public void onPause() {
        super.onPause();
        SharedPreferences pref = getActivity().getPreferences(0);
        pref.edit()
                .putString("稱謂", "老師")
                .putString("名稱", tea_name.getText().toString())
                .commit();
    }

}
