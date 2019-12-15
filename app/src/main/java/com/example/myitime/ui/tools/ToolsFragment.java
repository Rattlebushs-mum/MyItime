package com.example.myitime.ui.tools;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.data.SetBg;
import com.data.SetbgDataSource;
import com.example.myitime.R;

public class ToolsFragment extends Fragment {

    private SeekBar msb;
    private Button mbtnsure,mbtncancel;
    private SetbgDataSource setbgDataSource;
    private SetBg setBg;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_tools, container, false);
        msb=root.findViewById(R.id.sb_color);
        mbtnsure=root.findViewById(R.id.btn_sure);
        mbtncancel=root.findViewById(R.id.btn_cancel);
        setbgDataSource=new SetbgDataSource(root.getContext());
        setBg=setbgDataSource.load();
        msb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int cred,cgreen,cblue;

//            Bitmap bitmap = Bitmap.createBitmap(1000, 50, Bitmap.Config.ARGB_8888);
//            bitmap.eraseColor(Color.parseColor("#FF0000"));
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                cblue=(progress%32)*8;
                progress=progress/32;
                cgreen=(progress%32)*8;
                progress=progress/32;
                cred=(progress%32)*8;
//                temp="#"+Integer.toHexString(cred)+Integer.toHexString(cgreen)+Integer.toHexString(cblue);
//                Log.d("----seekbar-----",temp);
//                bitmap.eraseColor(Color.rgb(cred,cgreen,cblue));
//                Drawable drawable = new BitmapDrawable(bitmap);
                setBg.setCred(cred);
                setBg.setCgreen(cgreen);
                setBg.setCblue(cblue);
                seekBar.setBackgroundColor(Color.rgb(cred,cgreen,cblue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                setBg.setCred(cred);
                setBg.setCgreen(cgreen);
                setBg.setCblue(cblue);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mbtnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setbgDataSource.save();
                AppCompatActivity MainActivity = (AppCompatActivity) getActivity();
                Toolbar mToolBar = (Toolbar) MainActivity.findViewById(R.id.toolbar);
                mToolBar.setBackgroundColor(Color.rgb(setBg.getCred(),setBg.getCgreen(),setBg.getCblue()));
            }
        });
        return root;
    }
}