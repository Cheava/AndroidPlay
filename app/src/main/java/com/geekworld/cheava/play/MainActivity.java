package com.geekworld.cheava.play;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView01, mTextView02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView01 = (TextView) findViewById(R.id.myTextView01);
        Resources resources = getBaseContext().getResources();
        Drawable HippoDrawable = resources.getDrawable(R.drawable.abc_list_selector_disabled_holo_light);
        mTextView01.setBackgroundDrawable(HippoDrawable);

        mTextView02 = (TextView) findViewById(R.id.textView2);
        CharSequence str_2 = getString(R.string.textView2);
        String str_3 = ", Second hello !";
        mTextView02.setText(str_2 + str_3  );
        Button button1 = (Button)findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"It's interesting! ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
