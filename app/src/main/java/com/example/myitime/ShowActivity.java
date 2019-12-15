package com.example.myitime;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.data.EveItem;
import com.data.ItemDataSource;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    private ImageView mbtndelete,mbtnback,mbtnedit;

    private ImageView imageView;
    private View actionbar;
    int id;
    ItemDataSource itemDataSource;
    ArrayList<EveItem> list;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        id=getIntent().getIntExtra("id",0);
        itemDataSource=new ItemDataSource(getApplicationContext());
        list=itemDataSource.load();
        imageView=findViewById(R.id.inner_show_main_layout);
        actionbar=findViewById(R.id.show_title);
        mbtndelete=actionbar.findViewById(R.id.iv_delete);
        mbtnback=actionbar.findViewById(R.id.iv_back);
        mbtnedit=actionbar.findViewById(R.id.iv_sure);
        Glide.with(getApplicationContext()).load(list.get(id).getImagePath()).into(imageView);
        mbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowActivity.this.finish();
            }
        });
        mbtnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowActivity.this,EditActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        mbtndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.remove(id);
                itemDataSource.setGoods(list);
                itemDataSource.save();
                ShowActivity.this.finish();
            }
        });
    }

}

