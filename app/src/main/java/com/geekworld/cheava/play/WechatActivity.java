package com.geekworld.cheava.play;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created by wangzh on 2016/4/29.
 */
public class WechatActivity extends BaseActivity{
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    private List<Msg> msgList = new ArrayList<Msg>();
    private Msg msg;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues values;
    private Cursor cursor;
    private String last_id;

    public class ViewHolder extends View{
        public ViewHolder(Context context){
            super(context);
        }
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
    }

    public class DatabaseHelper extends SQLiteOpenHelper{
        private Context mContext;

        public static final String CREATE_HISTORY = "create table history ("
                + "id integer primary key autoincrement, "
                + "time string, "
                + "msg string)";

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
            super(context,name,factory,version);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            //db.execSQL(CREATE_HISTORY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("drop table if exists History");
            onCreate(db);
        }
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wechat);

        dbHelper = new DatabaseHelper(this,"History.db",null,1);
        values = new ContentValues();
        db = dbHelper.getWritableDatabase();
        try {
            db.execSQL(dbHelper.CREATE_HISTORY);
        }catch (Exception e){
            Log.i("wzh","The table exists.");
        }
        cursor = db.query("history",null,null,null,null,null,null);

        inputText = (EditText)findViewById(R.id.input_text);
        String inputTemp = loadTempMsg();
        if(!TextUtils.isEmpty(inputTemp)){
            inputText.setText(inputTemp);
            inputText.setSelection(inputTemp.length());
        }

        send = (Button)findViewById(R.id.send);

        adapter = new MsgAdapter(WechatActivity.this,R.layout.msg_item,msgList);
        msgListView = (ListView)findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    sendMsg(content);
                    inputText.setText("");
                    String respon = CommandParse(content);
                    if(!"".equals(respon)){
                        recMsg(respon);
                    }
                }
            }
        });

    }

    public class MsgAdapter extends ArrayAdapter<Msg> {
        private int resourceId;
        public MsgAdapter(Context context, int textViewResourceId, List<Msg> objects)
        {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Msg msg = getItem(position);
            View view;
            ViewHolder viewHolder;
            if(convertView == null){
                view = LayoutInflater.from(getContext()).inflate(resourceId,null);
                viewHolder = new ViewHolder(getContext());
                viewHolder.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
                viewHolder.rightLayout = (LinearLayout)view.findViewById(R.id.right_layout);
                viewHolder.leftMsg = (TextView)view.findViewById(R.id.left_msg);
                viewHolder.rightMsg = (TextView)view.findViewById(R.id.right_msg);
                view.setTag(viewHolder);
            }else{
                view = convertView;
                viewHolder = (ViewHolder)view.getTag();
            }
            if(msg.getType() == Msg.TYPE_RECEIVED){
                viewHolder.leftLayout.setVisibility(View.VISIBLE);
                viewHolder.rightLayout.setVisibility(View.GONE);
                viewHolder.leftMsg.setText(msg.getContent());
            }
            else if(msg.getType() == Msg.TYPE_SENT){
                viewHolder.rightLayout.setVisibility(View.VISIBLE);
                viewHolder.leftLayout.setVisibility(View.GONE);
                viewHolder.rightMsg.setText(msg.getContent());
            }
            return view;
        }

    }
    public class Msg{
        public static final int TYPE_RECEIVED = 0;
        public static final int TYPE_SENT = 1;
        private String content;
        private int type;
        public Msg(String content,int type){
            this.content = content;
            this.type = type;
        }

        public String getContent(){
            return content;
        }

        public int getType(){
            return type;
        }
    }

    protected  void onDestroy(){
        super.onDestroy();
        String inputTemp = inputText.getText().toString();
        save(inputTemp);
        cursor.close();
    }

    public void save(String inputText){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("inputTemp",Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer != null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String loadTempMsg(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput("inputTemp");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine()) != null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return  content.toString();
    }

    public void sendMsg(String content){
        msg = new Msg(content,Msg.TYPE_SENT);
        msgList.add(msg);
        adapter.notifyDataSetChanged();
        msgListView.setSelection(msgList.size());
    }

    public void recMsg(String content){
        msg = new Msg(content,Msg.TYPE_RECEIVED);
        msgList.add(msg);
        adapter.notifyDataSetChanged();
        msgListView.setSelection(msgList.size());
    }

    public void sendNotice(){
        NotificationManager noticeManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder  builder = new Notification.Builder(WechatActivity.this);
        builder.setTicker("你有一条新消息")
                .setContentTitle("一个坏消息")
                .setContentText("你的银行卡密码被盗了")
                .setSmallIcon(R.drawable.stop)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.wechat))
                .build();
        Notification notification = builder.build();
        noticeManager.notify(1,notification);

    }

    public String CommandParse(String content){
        switch (content){

            case "offline":
                Intent intent = new Intent("com.geekworld.cheava.FORCE_OFFLINE");
                sendBroadcast(intent);
                break;

            case "time-touch":
                SharedPreferences.Editor editor = getSharedPreferences("storedTime",MODE_PRIVATE).edit();
                String time = DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()).toString();
                editor.putString("time",time);
                editor.commit();
                Log.d("wzh","SharedPreference保存的时间为：" + time);
                return "Touch OK!";

            case "time-get":
                SharedPreferences pref = getSharedPreferences("storedTime",MODE_PRIVATE);
                String storedtime = pref.getString("time","");
                Log.d("wzh","SharedPreference读取的时间为：" + storedtime);
                return storedtime;

            case "delete-db":
                cursor.close();
                db.execSQL("drop table if exists history");
                getApplicationContext().deleteDatabase("History");
                return "delete db OK!";

            case "update-data":
                cursor.moveToLast();
                last_id = cursor.getString(cursor.getColumnIndex("id"));
                values.put("msg","Force update");
                db.update("history",values,"id = ?",new String[]{ last_id } );
                return "update data OK!";

            case "delete-data":
                cursor.moveToLast();
                last_id = cursor.getString(cursor.getColumnIndex("id"));
                db.delete("history","id = ?",new String[]{ last_id } );
                return "delete data OK!";

            case "query-data":
                cursor.moveToLast();
                String last_msg = cursor.getString(cursor.getColumnIndex("msg"));
                return last_msg;

            case "send-notice":
                sendNotice();
                break;

            default:
                db = dbHelper.getWritableDatabase();
                values.put("time",DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()).toString());
                values.put("msg",content);
                db.insert("history",null,values);
                values.clear();
                return "insert data OK!";
        }
        return "";
    }
}




