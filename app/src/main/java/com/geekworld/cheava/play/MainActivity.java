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

import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView0,mTextView1, mTextView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mTextView0 = (TextView) findViewById(R.id.textView0);
        mTextView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.geekworld.cheava.play.ACTION_START");
                intent.putExtra("extra_data","神龙：谁找我？(o-ωｑ)).oO 困，揉眼睛…… ");
                intent.addCategory("com.geekworld.cheava.play.TEST_CATEGORY");
                startActivityForResult(intent,1);
            }
        });
        //Resources resources = getBaseContext().getResources();
        //Drawable HippoDrawable = resources.getDrawable(R.drawable.abc_list_selector_disabled_holo_light);
        //mTextView01.setBackgroundDrawable(HippoDrawable);

        mTextView1 = (TextView) findViewById(R.id.textView1);
        mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 10086"));
                startActivity(intent);
            }
        });

        mTextView2 = (TextView) findViewById(R.id.textView2);
        CharSequence str_2 = getString(R.string.textView2);
        String str_3 = "\n\n我是打酱油的╮(╯▽╰)╭";
        mTextView2.setText(str_2 + str_3  );
        mTextView2.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(getApplicationContext(),"嘣！~~~~~ ", Toast.LENGTH_SHORT).show();
                TimerTask task = new TimerTask(){
                    public void run(){
                        finish();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 2400);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String returnedData = data.getStringExtra("data_return");
                    mTextView0.setText(returnedData);
                }
        }
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
