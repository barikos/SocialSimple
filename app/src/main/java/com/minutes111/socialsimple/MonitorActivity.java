package com.minutes111.socialsimple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MonitorActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        mTextView = (TextView) findViewById(R.id.tv_monitor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String text = getIntent().getStringExtra(Const.ATTR_INTENT);
        mTextView.setText(text);
    }
}
