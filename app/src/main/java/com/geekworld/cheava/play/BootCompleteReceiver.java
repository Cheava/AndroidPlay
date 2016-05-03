package com.geekworld.cheava.play;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 16-5-2.
 */
public class BootCompleteReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context,"Boot Complete",Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(context,MainActivity.class);
        //非activity的途径启动activity时要新起一个栈装入启动的activity()
        //因为此时必然不存在一个activity的栈
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
