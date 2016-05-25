package com.geekworld.cheava.play;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextView0,mTextView1, mTextView2;
    private EditText mEditText;
    private CheckBox mCheckBox;
    private Intent intent;
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private Button mLogin;
    private Button mCamera;
    private ImageView mCameraView;
    private Uri imageUri;
    private static final int TAKE_PHOTO = 3;
    private static final int CROP_PHOTO = 4;

    //Receiver的回调函数
    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo !=null && networkInfo.isAvailable()) {
                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏标题栏
        getSupportActionBar().hide();
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

        //将接收action的IntentFilter与Receiver绑定
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);

        //各控件实例化以及注册点击事件
        mTextView0 = (TextView) findViewById(R.id.textView0);
        mTextView0.setOnClickListener(this);

        mTextView1 = (TextView) findViewById(R.id.textView1);
        mTextView1.setOnClickListener(this);

        mTextView2 = (TextView) findViewById(R.id.textView2);
        mTextView2.setOnClickListener(this);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);

        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);

        mCamera = (Button)findViewById(R.id.camera_capture);
        mCamera.setOnClickListener(this);
        mCamera.setOnLongClickListener(new cameraLongClick());
        mCameraView = (ImageView)findViewById(R.id.camera_view);

        if(getIntent() != null) {
            if(getIntent().hasExtra("this_id")) {
                mTextView2.setText(getIntent().getStringExtra("this_id"));
            }
        }

        //密码框的显示以及状态保存
        mEditText = (EditText)findViewById(R.id.editText);
        mCheckBox = (CheckBox)findViewById(R.id.checkBox);
        mLogin = (Button)findViewById(R.id.login);
        mLogin.setOnClickListener(this);
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

    }

    //启动其他活动后，针对该活动结束时返回值的回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String returnedData = data.getStringExtra("data_return");
                    mTextView0.setText(returnedData);
                }
                break;
            case 2:
                if(mEditText.getText().toString() != ""){
                    mEditText.setText("");
                }
                break;

            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra("scale",true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,CROP_PHOTO);
                }
                break;

            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                   mCameraView.setImageURI(imageUri);
                }
                break;
            default:
                break;
        }
    }

    //菜单界面的显示以及点击事件注册
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

    //保存当前活动的必要信息
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        String tempData = mEditText.getText().toString();
        boolean isCheck = mCheckBox.isChecked();
        outState.putString("data_store",tempData);
        outState.putBoolean("status_check",isCheck);

        }

    //统一处理点击事件
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.login:
                String passwd = mEditText.getText().toString();
                if(passwd.equals("sbjiushiwo")){
                    Intent intent = new Intent(MainActivity.this,WechatActivity.class);
                    startActivityForResult(intent,2);
                }else{
                    Toast.makeText(getApplicationContext(),"进入的姿势不对，要不再试试？",
                            Toast.LENGTH_SHORT).show();
                }
                break;
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
                intent = new Intent(MainActivity.this,WechatActivity.class);
                //intent.setData(Uri.parse("tel: 10086"));
                startActivity(intent);
                break;

            case R.id.textView2:
                intent = new Intent(MainActivity.this,ContactsActivity.class);
                //intent.setData(Uri.parse("http://www.zhihu.com"));
                startActivity(intent);
                break;

            case R.id.camera_capture:
                imageUri = createFile("tempImage.jpg");
                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);

            default:
                break;
        }
    }

    public class cameraLongClick implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view){
            imageUri = createFile("outputImage.jpg");
            intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            intent.putExtra("crop",true);
            intent.putExtra("scale",true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(intent,CROP_PHOTO);
            return true;
        }
    }

    public Uri createFile(String img){
        File outputImage = new File(Environment.getExternalStorageDirectory(),img);
        try {
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        Uri tempUri = Uri.fromFile(outputImage);
        return tempUri;
    }

    //退出此活动时的确认窗口
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

    //按下返回键时的回调函数
    public void onBackPressed(){
        exitComfirm();
    }

    //销毁活动时反注册Receiver
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
}



