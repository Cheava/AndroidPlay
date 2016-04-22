package com.geekworld.cheava.play;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;


public class SecondActivity extends AppCompatActivity {
    private TextView mTextView2_0;
    private Intent intent ;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_second);
        mTextView2_0 = (TextView)findViewById(R.id.textView2_0);
        intent =  getIntent();
        timer.schedule(task,3000);
        mTextView2_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent();
                intent_back.putExtra("data_return","我胡汉三又回来啦！");
                setResult(RESULT_OK,intent_back);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent_back = new Intent();
        intent_back.putExtra("data_return","我胡汉三又回来啦！");
        setResult(RESULT_OK,intent_back);
        finish();
    }

    //在Timer中设置handler的message
    TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    //handle接收到message信息后，修改textview(避免发生UI操作的线程不安全错误)
    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                    mTextView2_0.setText(intent.getStringExtra("extra_data"));
            }
        }
    };
}
