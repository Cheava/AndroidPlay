package com.geekworld.cheava.play;

import android.app.Activity;
import android.os.Bundle;

import java.security.AccessController;

/**
 * Created by Administrator on 16-5-3.
 */
public class BaseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
