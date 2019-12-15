package com.example.myitime.ui.time;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.data.EveItem;
import com.data.SetBg;
import com.data.SetbgDataSource;
import com.example.myitime.R;
import com.example.myitime.ShowActivity;
import java.util.List;

public class ItemAdapter extends ArrayAdapter<EveItem> {
    private int resourceId;
    private Context mcontext;
    private SetbgDataSource setbgDataSource;
    private SetBg setBg;
    public ItemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<EveItem> objects) {
        super(context, resource, objects);
        this.mcontext=context;
        resourceId = resource;
        setbgDataSource=new SetbgDataSource(context);
        setBg=setbgDataSource.load();

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(this.getContext());
        View item = mInflater.inflate(this.resourceId, null);
        LinearLayout block=item.findViewById(R.id.block_color);
        ImageView img = (ImageView) item.findViewById(R.id.item_iv);
        TextView title = (TextView) item.findViewById(R.id.item_title);
        TextView time = (TextView) item.findViewById(R.id.item_content);
        block.setBackgroundColor(Color.rgb(setBg.getCred(),setBg.getCgreen(),setBg.getCblue()));
        EveItem mitem = this.getItem(position);
        Glide.with(mcontext).load(mitem.getImagePath()).into(img);
//        Picasso.with(mcontext).load(mitem.getImagePath()).into(img);
        title.setText(mitem.getTitle());
        time.setText(mitem.getYear() + "年" + mitem.getMonth() + "月" + mitem.getDay() + "日");

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, ShowActivity.class);
                intent.putExtra("id",position);
                mcontext.startActivity(intent);
            }
        });

        return item;
    }

}