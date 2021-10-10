package com.project.ee104b.ee_project;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class fg_teac_room extends Fragment {


    private Button start_test;
    private SimpleCursorAdapter CAdapter;
    private Spinner class_spinner;
    private String[] Class_name = new String[]{"Class"};
    private StudentData_DBHelper DBH;
    private Cursor cursor;
    private EditText En_class;
    private String class_name;

    public fg_teac_room() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teac_room, container, false);


    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        En_class = (EditText)getView().findViewById(R.id.En_class);
        Set_spinner();

        if (class_spinner==null){
        }
        else {
            class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cursor.moveToPosition(position);
                    class_name = cursor.getString(cursor.getColumnIndex("Class"));
                    En_class.setText(class_name);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        start_test = (Button) getView().findViewById(R.id.start_test);
        start_test.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), choose_quse.class);
                class_name=En_class.getText().toString();
                it.putExtra("班級", class_name);
                startActivity(it);
            }
        });
    }
    private  void Set_spinner(){

        DBH = new StudentData_DBHelper(getActivity());
        cursor=DBH.Search_Class();

        if(cursor.getCount()==0){
        }
        else {
            CAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, cursor, Class_name, new int[]{android.R.id.text1}, 0);
            CAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            class_spinner = (Spinner) getView().findViewById(R.id.class_sp);
            class_spinner.setAdapter(CAdapter);
        }
    }
}
