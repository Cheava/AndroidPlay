package com.geekworld.cheava.play;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextView0,mTextView1, mTextView2;
    private EditText mEditText;
    private CheckBox mCheckBox;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

        mTextView0 = (TextView) findViewById(R.id.textView0);
        mTextView0.setOnClickListener(this);
/*        mTextView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.geekworld.cheava.play.ACTION_START");
                intent.putExtra("extra_data","神龙：谁找我？(o-ωｑ)).oO 困，揉眼睛…… ");
                intent.addCategory("com.geekworld.cheava.play.TEST_CATEGORY");
                startActivityForResult(intent,1);
            }
        });*/
        //Resources resources = getBaseContext().getResources();
        //Drawable HippoDrawable = resources.getDrawable(R.drawable.abc_list_selector_disabled_holo_light);
        //mTextView01.setBackgroundDrawable(HippoDrawable);

        mTextView1 = (TextView) findViewById(R.id.textView1);
        mTextView1.setOnClickListener(this);
/*
        mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 10086"));
                startActivity(intent);
            }
        });
*/

        mTextView2 = (TextView) findViewById(R.id.textView2);
        mTextView2.setOnClickListener(this);
        if(getIntent() != null) {
            if(getIntent().hasExtra("this_id")) {
                mTextView2.setText(getIntent().getStringExtra("this_id"));
            }
        }

/*        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.zhihu.com"));
                startActivity(intent);
            }
        });*/


        mEditText = (EditText)findViewById(R.id.editText);
        mCheckBox = (CheckBox)findViewById(R.id.checkBox);

        if(savedInstanceState != null){
            String tempData = savedInstanceState.getString("data_store");
            boolean isCheck = savedInstanceState.getBoolean("status_check");
            mEditText.setText(tempData);
            if(isCheck) {
                mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else {
                mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }

        mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
/*        button.setOnClickListener(new View.OnClickListener() {
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
        });*/

        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
/*        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("this_id","我是二师兄");
                startActivity(intent);
            }
        });*/
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

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        String tempData = mEditText.getText().toString();
        boolean isCheck = mCheckBox.isChecked();
        outState.putString("data_store",tempData);
        outState.putBoolean("status_check",isCheck);

        }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button:
                exitComfirm();
                break;

            case R.id.button1:
                intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("this_id","大家好，我是二师兄[猪]");
                startActivity(intent);
                break;

            case R.id.textView0:
                intent = new Intent("com.geekworld.cheava.play.ACTION_START");
                intent.putExtra("extra_data","神龙：谁找我？(o-ωｑ)).oO 困，揉眼睛…… ");
                intent.addCategory("com.geekworld.cheava.play.TEST_CATEGORY");
                startActivityForResult(intent,1);
                break;

            case R.id.textView1:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 10086"));
                startActivity(intent);
                break;

            case R.id.textView2:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.zhihu.com"));
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    public void exitComfirm(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("警告！");
        dialog.setMessage("确定自爆？");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "你的手机将在三秒启动自爆程序 ", Toast.LENGTH_SHORT).show();
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
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }

    public void onBackPressed(){
        exitComfirm();
    }
}



