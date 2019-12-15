package com.example.myitime;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.data.EveItem;
import com.data.ItemDataSource;
import com.example.myitime.ui.AllTool;
import com.example.myitime.ui.time.HomeFragment;
import com.youth.banner.loader.ImageLoader;

public class InnerShowActivity extends AppCompatActivity {
    private ImageView linearLayout;
    private TextView title,time,remark;
    private EveItem eveItem;
    private ImageView btn_back;
    private ItemDataSource itemDataSource;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_show);
        getSupportActionBar().hide();
        int id=getIntent().getIntExtra("id",0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        itemDataSource=new ItemDataSource(getApplicationContext());
        eveItem=itemDataSource.load().get(id);
        linearLayout=findViewById(R.id.inner_show_main_layout);
        title=findViewById(R.id.tv_show_title);
        time=findViewById(R.id.tv_show_time);
        remark=findViewById(R.id.tv_show_remarks);
        btn_back=findViewById(R.id.iv_back);
        title.setText(eveItem.getTitle());
        remark.setText(eveItem.getRemark());
        time.setText(HomeFragment.setTime( eveItem.getYear() + "-" +  eveItem.getMonth() + "-" +  eveItem.getDay() + " " +  eveItem.getHour() + ":" +  eveItem.getMonth() + ":" + "00"));
        Glide.with(getApplicationContext()).load(eveItem.getImagePath()).into(linearLayout);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x12) {
                    time.setText(HomeFragment.setTime( eveItem.getYear() + "-" +  eveItem.getMonth() + "-" +  eveItem.getDay() + " " +  eveItem.getHour() + ":" +  eveItem.getMonth() + ":" + "00"));
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                        Message message=new Message();
                        message.what = 0x12;
                        handler.sendMessage(message);
                    }
                }
        }).start();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InnerShowActivity.this.finish();
            }
        });
    }

}
