package com.geekworld.cheava.play;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
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


import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzh on 2016/4/29.
 */
public class WechatActivity extends Activity{
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    private List<Msg> msgList = new ArrayList<Msg>();
    private Msg msg;

    public class ViewHolder extends View{
        public ViewHolder(){
            super();
        }
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wechat);
        //getSupportActionBar().hide();
        //initMsgs();
        inputText = (EditText)findViewById(R.id.input_text);
        send = (Button)findViewById(R.id.send);

        adapter = new MsgAdapter(WechatActivity.this,R.layout.msg_item,msgList);
        msgListView = (ListView)findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    msg = new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");
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
                viewHolder = new ViewHolder();
                viewHolder.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
                viewHolder.rightLayout = (LinearLayout)view.findViewById(R.id.right_layout);
                viewHolder.leftMsg = (TextView)view.findViewById(R.id.left_msg);
                viewHolder.rightMsg = (TextView)view.findViewById(R.id.riht_msg);
                view.setTag(viewHolder);
            }else{
                view = convertView;
                viewHolder = (viewHolder)view.getTag();
            }
            if(msg.getType() == Msg.TYPE_RECEIVED){
                viewHolder.leftLayout.setVisibility(View.VISIBLE);
                viewHolder.rightLayout.setVisibility(View.GONE);
                viewHolder.leftMsg.getContext();
            }
            else if(msg.getType() == Msg.TYPE_SENT){
                viewHolder.rightLayout.setVisibility(View.VISIBLE);
                viewHolder.leftLayout.setVisibility(View.GONE);
                viewHolder.rightMsg.getContext();
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
}




