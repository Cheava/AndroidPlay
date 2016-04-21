package com.geekworld.cheava.play;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView01, mTextView02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView01 = (TextView) findViewById(R.id.myTextView01);
        Resources resources = getBaseContext().getResources();
        Drawable HippoDrawable = resources.getDrawable(R.drawable.abc_item_background_holo_dark);
        mTextView01.setBackgroundDrawable(HippoDrawable);

        mTextView02 = (TextView) findViewById(R.id.textView2);
        CharSequence str_2 = getString(R.string.textView2);
        String str_3 = ", Second hello !";
        mTextView02.setText(str_2 + str_3  );

    }
}
