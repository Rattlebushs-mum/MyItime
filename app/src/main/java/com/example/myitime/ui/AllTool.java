package com.example.myitime.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.File;

public class AllTool {


    public static Bitmap openImage(String path){
        File file=new File(path);
        Bitmap bitmap = null;
        if(file.exists()){
            bitmap = BitmapFactory.decodeStream(AllTool.class.getResourceAsStream(path));
        }
        return bitmap;
    }
    public static Drawable bittodra(Bitmap bitmap){
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

}
