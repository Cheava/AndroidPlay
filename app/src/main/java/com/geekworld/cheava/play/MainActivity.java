package com.geekworld.cheava.play;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView,mTextView01, mTextView02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 10086"));
                startActivity(intent);
            }
        });

        mTextView01 = (TextView) findViewById(R.id.myTextView01);
        mTextView01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.geekworld.cheava.play.ACTION_START");
                intent.putExtra("extra_data","呵呵哒，么么哒");
                intent.addCategory("com.geekworld.cheava.play.TEST_CATEGORY");
                startActivity(intent);
            }
        });
        //Resources resources = getBaseContext().getResources();
        //Drawable HippoDrawable = resources.getDrawable(R.drawable.abc_list_selector_disabled_holo_light);
        //mTextView01.setBackgroundDrawable(HippoDrawable);

        mTextView02 = (TextView) findViewById(R.id.textView2);
        CharSequence str_2 = getString(R.string.textView2);
        String str_3 = "\n\n我是打酱油的╮(╯▽╰)╭";
        mTextView02.setText(str_2 + str_3  );
        mTextView02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.zhihu.com"));
                startActivity(intent);
            }
        });



        Button button1 = (Button)findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"你的手机将在三秒启动自爆程序 ", Toast.LENGTH_SHORT).show();
                TimerTask task = new TimerTask(){
                    public void run(){
                        finish();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 3000);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.cat:
                Toast.makeText(this,"喵~~~~~~~",Toast.LENGTH_SHORT).show();
                break;
            case R.id.dog:
                Toast.makeText(this,"汪~~~~~~~~.",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
