package com.geekworld.cheava.play;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mTextView2_0;
    private Intent intent ;
    private Timer timer = new Timer();
    private ImageView mImageView;
    private String[] data = {"鸣人","佐助","杨过","小龙女","福尔摩斯","华生","毕福剑","苍老师",
            "鸣人","佐助","杨过","小龙女","福尔摩斯","华生","毕福剑","苍老师"};
    private List<Person> personList = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.layout_second);
        mTextView2_0 = (TextView)findViewById(R.id.textView2_0);
        mImageView = (ImageView)findViewById(R.id.imageView);
        mImageView.setOnClickListener(this);

        //initPerson();
        PersonAdapter adapter = new PersonAdapter(SecondActivity.this,R.layout.person_item,personList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person person = personList.get(position);
                Toast.makeText(SecondActivity.this,person.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        intent =  getIntent();
        timer.schedule(task,3000);
        mTextView2_0.setOnClickListener(this);
    }

    @Override
    public void onBackPressed(){
        sendIntent();
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

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.imageView:
                mImageView.setImageResource(R.drawable.cat);
                break;
            case R.id.textView2_0:
                sendIntent();
                break;
            default:
                break;
        }
    }

    public void sendIntent(){
        Intent intent_back = new Intent();
        intent_back.putExtra("data_return","我胡汉三又回来啦！");
        setResult(RESULT_OK,intent_back);
        finish();
    }

/*

    private void initPerson(){
        Person mingren = new Person("鸣人",R.drawable.mingren);
        personList.add(mingren);
        Person zuozhu = new Person("佐助",R.drawable.zuozhu);
        personList.add(zuozhu);
        Person yangguo = new Person("杨过",R.drawable.yangguo);
        personList.add(yangguo);
        Person xialongnv = new Person("小龙女",R.drawable.xialongnv);
        personList.add(xialongnv);
        Person fu = new Person("福尔摩斯",R.drawable.fu);
        personList.add(fu);
        Person hua = new Person("华生",R.drawable.hua);
        personList.add(hua);
        Person bi = new Person("毕福剑",R.drawable.stop);
        personList.add(bi);
        Person cang = new Person("苍老师",R.drawable.stop);
        personList.add(cang);

    }
*/

    public class PersonAdapter extends ArrayAdapter<Person>{
        private int resourceId;
        public PersonAdapter(Context context,int textViewResourceId,List<Person> object){
            super(context,textViewResourceId,object);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Person person = getItem(position);
            View view ;
            if(convertView == null){
                view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            }else{
                view = convertView;
            }
            ImageView personImage = (ImageView)view.findViewById(R.id.person_image);
            TextView personName = (TextView)view.findViewById(R.id.person_name);
            personName.setText(person.getName());
            personImage.setImageResource(person.getImageId());
            return view;
        }
    }
    public class Person{
        private String name;
        private int imageId;
        public Person(String name , int imageId){
            this.name = name;
            this.imageId = imageId;
        }

        public String getName() {
            return name;
        }

        public int getImageId(){
            return imageId;
        }
    }

}
