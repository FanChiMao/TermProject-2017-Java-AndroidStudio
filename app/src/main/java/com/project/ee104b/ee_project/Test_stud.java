package com.project.ee104b.ee_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;

import static android.widget.Toast.*;


public class Test_stud extends AppCompatActivity {

    private Question_DBHelper DBH;
    private Cursor cur;
    private String tmp,count,question,Class_name,Student,Id,Score,CorrectOrNot;
    private String[] Ques_Data,Score_Data;
    private Socket clientSocket;
    private ScrollView SV;
    private Button start_button;
    private TextView Sat_txv,ques_txv,ans_a,ans_b,ans_c,ans_d;
    private int num=0;
    private CheckBox CB_A,CB_B,CB_C,CB_D;
    private Thread t;
    private Toast toast;
    public static Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_stud);

        setTitle("測驗中");

        check_ID();
        Ques_Data=new String[20];
        Score_Data=new String[3];
        Sat_txv = (TextView)findViewById(R.id.Sat_txv);
        ques_txv = (TextView)findViewById(R.id.ques_txv);
        ans_a = (TextView)findViewById(R.id.ans_a);
        ans_b = (TextView)findViewById(R.id.ans_b);
        ans_c = (TextView)findViewById(R.id.ans_c);
        ans_d = (TextView)findViewById(R.id.ans_d);
        CB_A = (CheckBox)findViewById(R.id.CB_A);
        CB_B = (CheckBox)findViewById(R.id.CB_B);
        CB_C = (CheckBox)findViewById(R.id.CB_C);
        CB_D = (CheckBox)findViewById(R.id.CB_D);
        SV = (ScrollView)findViewById(R.id.SV);
        start_button = (Button)findViewById(R.id.start_button) ;
        start_button.setVisibility(View.GONE);



        //set_Data();

        t = new Thread(readData);
        t.start();
    }
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
                //一般用戶使用(房主不用這段)
                if(count.equals("1")) {
                    clientSocket.close();
                    mHandler.post(outlink);
                    finish();
                }
                count = null;

                if (clientSocket.isConnected()==true){
                    mHandler.post(checklink);
                }

                //連線後
                while(clientSocket.isConnected()){

                    //取得網路訊息
                    tmp = br.readLine();

                    //非空訊息則顯示新訊息
                    if(tmp!=null){
                        Ques_Data[num]=tmp;
                        num++;
                        if (num==10){
                            mHandler.post(updateText);
                            num=0;
                        }
                        else{}
                    }else {}
                }
            }catch (IOException e){
            }
        }
    };
    private Runnable outlink = new Runnable() {
        @Override
        public void run() {
            toast.makeText(Test_stud.this,"考試房間未開",Toast.LENGTH_SHORT).show();
        }
    };
    private Runnable checklink = new Runnable() {
        @Override
        public void run() {
            Sat_txv.setText("已完成連線");
        }
    };
    private Runnable updateText = new Runnable() {
        @Override
        public void run() {
            Sat_txv.setText("已接收訊息");
            start_button.setVisibility(View.VISIBLE);
        }
    };

    public void Start_test(View v){
        start_button.setVisibility(View.GONE);
        SV.setVisibility(View.VISIBLE);
        ques_txv.setText(Ques_Data[3]);
        ans_a.setText(Ques_Data[4]);
        ans_b.setText(Ques_Data[5]);
        ans_c.setText(Ques_Data[6]);
        ans_d.setText(Ques_Data[7]);

        new CountDownTimer((Integer.parseInt(Ques_Data[9])+1)*1000,1000){

            @Override
            public void onFinish() {
                SV.setVisibility(View.GONE);
                Check_ans();
                Add_Data();
                send_Data();
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
            @Override
            public void onTick(long millisUntilFinished) {
                Sat_txv.setText("剩餘時間 : "+millisUntilFinished/1000+"秒");
            }
        }.start();
    }
    private void Check_ans(){

        String final_ans = "null";
        if (CB_A.isChecked()==true && CB_B.isChecked()==false && CB_C.isChecked()==false && CB_D.isChecked()==false)
            final_ans=ans_a.getText().toString();
        else if (CB_B.isChecked() && CB_A.isChecked()==false &&  CB_C.isChecked()==false && CB_D.isChecked()==false)
            final_ans=ans_b.getText().toString();
        else if (CB_C.isChecked()==true && CB_A.isChecked()==false && CB_B.isChecked()==false && CB_D.isChecked()==false)
            final_ans=ans_c.getText().toString();
        else if (CB_D.isChecked()==true && CB_A.isChecked()==false && CB_B.isChecked()==false && CB_C.isChecked()==false)
            final_ans=ans_d.getText().toString();
        else
            final_ans = "null";

        if (final_ans.equals(Ques_Data[8])){
            Sat_txv.setText("作答結束!\n\n正確答案!\n\n\n\n再次點擊螢幕離開房間");
            Ques_Data[10]="1";
        }
        else {
            Sat_txv.setText("作答結束!\n\n你答錯了!\n\n\n\n再次點擊螢幕離開房間");
            Ques_Data[10]="0";
        }
    }
    private void Add_Data(){

        DBH = new Question_DBHelper(this);
        @SuppressLint("SimpleDateFormat") java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");//大小寫是固定的,一變會等於另一個它本身定義的數字
        Date date = new Date(System.currentTimeMillis());
        String latesttime = simpleDateFormat.format(date);
        DBH.addData(Ques_Data[0], Ques_Data[1], 1, Integer.parseInt(Ques_Data[10]), Ques_Data[2], Ques_Data[3], Ques_Data[4],
                Ques_Data[5], Ques_Data[6],Ques_Data[7],Ques_Data[8],Integer.parseInt(Ques_Data[9]),
                1,latesttime);
    }
    private void send_Data(){
        Score_Data[0]=Student;
        Score_Data[1]="@@"+Id;
        Score_Data[2]="@@"+Ques_Data[10];

        if(clientSocket.isConnected()) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                        bw.write(Arrays.toString(Score_Data));
                        bw.newLine();
                        bw.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    protected void check_ID(){
        SharedPreferences pref = getSharedPreferences("身分", MODE_APPEND);
        Student = pref.getString("名稱", null);
        Id = pref.getString("學號", null);
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
