package com.project.ee104b.ee_project;

import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

public class Test extends AppCompatActivity {

    private Question_DBHelper DBH;
    private Cursor cur;
    private String tmp,count,question,Class_name,Student,Id,Score,CorrectOrNot;
    private String[] Ques_Data,Student_Data;
    private Socket clientSocket;
    private int people;
    private Button send_data;
    private TextView Sat_txv;
    public static Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setTitle("測驗中。。。");

        final Intent it = getIntent();
        question = it.getStringExtra("題目");
        Class_name = it.getStringExtra("班級");

        Sat_txv=(TextView)findViewById(R.id.Sat_txv);
        send_data=(Button)findViewById(R.id.send_data);

        Student_Data=new String[3];

        DBH = new Question_DBHelper(this);
        cur = DBH.FindByQuestion(question);
        cur.moveToFirst();
        set_Data();

        Thread t = new Thread(readData);
        t.start();


    }
    private void set_Data(){
        Ques_Data=new String[]{cur.getString(cur.getColumnIndex("Subject")),
                cur.getString(cur.getColumnIndex("Chapter")),
                cur.getString(cur.getColumnIndex("Question_Title")),
                cur.getString(cur.getColumnIndex("Question_Context")),
                cur.getString(cur.getColumnIndex("Answer_Context_A")),
                cur.getString(cur.getColumnIndex("Answer_Context_B")),
                cur.getString(cur.getColumnIndex("Answer_Context_C")),
                cur.getString(cur.getColumnIndex("Answer_Context_D")),
                cur.getString(cur.getColumnIndex("Correct_Answer")),
                cur.getString(cur.getColumnIndex("Time_Setting"))};
    }

    public void Trans_Question(View v){
        if(clientSocket.isConnected()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                        for (int i=0;i<10;i++) {
                            bw.write(Ques_Data[i]);
                            bw.newLine();
                            bw.flush();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else;

        Sat_txv.setText("題目已發送\n\n等待分數回傳");
        send_data.setVisibility(View.GONE);
    }
    //取得網路資料
    private Runnable readData = new Runnable() {
        @Override
        public void run() {
            //server端IP
            InetAddress serverIp;
            try{
                serverIp = InetAddress.getByName("140.138.179.70");
                int serverport = 5050;
                clientSocket = new Socket(serverIp,serverport);

                //取得網路輸入串流
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                count = br.readLine();

                count = null;
                people = 0;

                if (clientSocket.isConnected()==true){
                    mHandler.post(checklink);
                }

                //連線後
                while(clientSocket.isConnected()){

                    //取得網路訊息
                    tmp = br.readLine();

                    //非空訊息則顯示新訊息
                    if(tmp!=null){
                        tmp=tmp.replaceFirst("\\[", "").replaceFirst("\\]", "");
                        Student_Data=tmp.split(", @@");
                         AddStudentdata();
                         mHandler.post(updateText);
                    }else {}
                }
            }catch (IOException e){
            }
        }
    };
    private Runnable checklink = new Runnable() {
        @Override
        public void run() {
            Sat_txv.setText("已建置考試房間");
        }
    };
    private Runnable updateText = new Runnable() {
        @Override
        public void run() {
            people++;
            Sat_txv.setText("已接收"+people+"筆資料"+"\n\n"+"點擊螢幕以結束考試");
            Sat_txv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            });
        }
    };
    private void AddStudentdata(){
        final  StudentData_DBHelper DB = new StudentData_DBHelper(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//大小寫是固定的,一變會等於另一個它本身定義的數字
        Date date = new Date(System.currentTimeMillis());
        String latesttime = simpleDateFormat.format(date);

        Student = Student_Data[0];
        Id=Student_Data[1];
        Score=cur.getString(cur.getColumnIndex("Score"));
        CorrectOrNot=Student_Data[2];
        Cursor FirstAttendOrNot = null;
        FirstAttendOrNot = DB.Search_Attend(Class_name, Id);
        FirstAttendOrNot.moveToFirst();

        if (FirstAttendOrNot.getCount() == 0) {//表示這個人從未來過，是新的一筆資料，用addData
            DB.FirstGetLatestData(Class_name,Student,Id,Integer.parseInt(Score),latesttime, Integer.parseInt(CorrectOrNot));
        } else {//這個人已有在資料表，用update即可
            DB.SecondGetLatestData(Class_name,Student,Id,Integer.parseInt(Score),latesttime, Integer.parseInt(CorrectOrNot),FirstAttendOrNot.getInt(0));
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
