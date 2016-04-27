package com.geekworld.cheava.play;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

/**
 * Created by wangzh on 2016/4/25.
 */
public class TitleActivity extends LinearLayout implements View.OnClickListener{
    private Button titleBack;
    private Button titleEdit;

    public TitleActivity(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);
        Button titleBack = (Button)findViewById(R.id.title_back);
        Button titleEdit = (Button)findViewById(R.id.title_edit);
        titleBack.setOnClickListener(this);
        titleEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
            switch(v.getId()) {
                case R.id.title_back:
                    ((Activity) getContext()).finish();
                    break;
                case R.id.title_edit:
                    showPopupMenu(v);
                    break;
                default:
                    break;
            }
        }

    public void showPopupMenu(View view) {
        //参数View 是设置当前菜单显示的相对于View组件位置，具体位置系统会处理
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        //加载menu布局
        popupMenu.getMenuInflater().inflate(R.layout.popupmenu, popupMenu.getMenu());
        //设置menu中的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //设置popupMenu消失的点击事件,并非点击非Menu区域时的menu消失事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }
}
