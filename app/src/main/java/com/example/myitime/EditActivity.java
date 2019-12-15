package com.example.myitime;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.data.EveItem;
import com.data.ItemDataSource;
import com.data.SetBg;
import com.data.SetbgDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditActivity extends AppCompatActivity {
    private SetbgDataSource setbgDataSource;
    private SetBg setBg;
    //视图
    private EditText et_title,et_remark;
    private View mv1,mv2,mv3,mv4,mvTitleBar;//下面四个设置，和titlebar
    private ImageView miv1,miv2,miv3,miv4;//下面四个设置里面的图片
    private TextView mtvt1,mtvt2,mtvt3,mtvt4,mtvc1,mtvc2,mtvc3,mtvc4;//下面四个设置里面的标题和说明
    private ImageView mivBack,mivSure;////titlebar的里面返回按键和确定按键
    private View mview_date,mview_time,mview_repeat,mview_call_camera;//日历里面的日历和时间，重复的view,调用底层的dialog
    private TextView mtv_week,mtv_month,mtv_year,mtv_diy;    //重复内的textview

    //数据
    int myear,mmonth,mday,mhour,mmin;//年月日时分
    private String imagePath=null;//设置图片的路径

    int id;
    private EveItem item;
    private ArrayList<EveItem> list;
    private ItemDataSource itemDataSource;
    private ArrayList<Integer> picIds=new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setbgDataSource=new SetbgDataSource(getApplicationContext());
        setBg=setbgDataSource.load();
        id = getIntent().getIntExtra("id",0);
        itemDataSource=new ItemDataSource(getApplicationContext());
        list=itemDataSource.load();
        item=list.get(id);
        myear=item.getYear();
        mmonth=item.getMonth();
        mday=item.getDay();
        mhour=item.getHour();
        mmin=item.getMinu();
        initPics();
        mvTitleBar=(View)findViewById(R.id.layout_set_title_bar);
        LinearLayout linearLayout=findViewById(R.id.new_top_layout);
        mivBack=mvTitleBar.findViewById(R.id.iv_back);
        mivSure=mvTitleBar.findViewById(R.id.iv_sure);
        et_remark=this.findViewById(R.id.et_remark);
        et_title=this.findViewById(R.id.et_title);
        et_remark.setText(item.getRemark());
        et_title.setText(item.getTitle());
        mvTitleBar.setBackgroundColor(Color.rgb(setBg.getCred(),setBg.getCgreen(),setBg.getCblue()));
        linearLayout.setBackgroundColor(Color.rgb(setBg.getCred(),setBg.getCgreen(),setBg.getCblue()));

        mv1=(View)findViewById(R.id.new_set1);
        miv1=mv1.findViewById(R.id.item_iv);
        miv1.setImageResource(R.drawable.clock);
        mtvt1=mv1.findViewById(R.id.item_title);
        mtvt1.setText("日期");
        mtvc1=mv1.findViewById(R.id.item_content);
        mtvc1.setText(myear+"年"+mmonth+"月"+mday+"日"+mhour+"时"+mmin+"分");

        mv2=(View)findViewById(R.id.new_set2);
        miv2=mv2.findViewById(R.id.item_iv);
        miv2.setImageResource(R.drawable.recycle);
        mtvt2=mv2.findViewById(R.id.item_title);
        mtvt2.setText("重复");
        mtvc2=mv2.findViewById(R.id.item_content);
        mtvc2.setText("无");


        mv3=(View)findViewById(R.id.new_set3);
        miv3=mv3.findViewById(R.id.item_iv);
        miv3.setImageResource(R.drawable.image);
        mtvt3=mv3.findViewById(R.id.item_title);
        mtvt3.setText("图片");
        mtvc3=mv3.findViewById(R.id.item_content);
        mtvc3.setText("");


        mv4=(View)findViewById(R.id.new_set4);
        miv4=mv4.findViewById(R.id.item_iv);
        miv4.setImageResource(R.drawable.labadd);
        mtvt4=mv4.findViewById(R.id.item_title);
        mtvt4.setText("添加新标签");
        mtvc4=mv4.findViewById(R.id.item_content);
        mtvc4.setText("");


        //左上角的返回按键
        mivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditActivity.this.finish();
            }
        });
        //右上角的确定按键
        mivSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里还差插入 save和load
            if((et_title.getText().length()!=0)){
                if(imagePath!=null){
                    item.setImagePath(imagePath);
                }
                item.setDay(mday);
                item.setHour(mhour);
                item.setMinu(mmin);
                item.setMonth(mmonth);
                item.setYear(myear);
                item.setTitle(et_title.getText().toString());
                item.setRemark(et_remark.getText().toString());
                itemDataSource.setGoods(list);
                itemDataSource.save();
                Intent intent=new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(EditActivity.this,"您未填写完整标题与时间",Toast.LENGTH_SHORT).show();
            }
            }
        });
//选择日历的view
        mv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mview_date = LayoutInflater.from(EditActivity.this).inflate(R.layout.dialog_date, null);
                final DatePicker endDate = (DatePicker) mview_date.findViewById(R.id.ed);
                final AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setView(mview_date);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mview_time = LayoutInflater.from(EditActivity.this).inflate(R.layout.dialog_time, null);
                        final TimePicker endTime = (TimePicker) mview_time.findViewById(R.id.et);
                        mmonth = endDate.getMonth() + 1;
                        myear = endDate.getYear();
                        mday=endDate.getDayOfMonth();
                        //需要修改，用全局变量。
                        builder.setView(mview_time);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    mhour=endTime.getHour();
                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    mmin=endTime.getMinute();
                                }
                                mtvc1.setText(myear+"年"+mmonth+"月"+mday+"日"+mhour+"时"+mmin+"分");
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog2 = builder.create();
                        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog2.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        dialog2.show();
                        dialog2.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog dialog = builder.create();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                dialog.show();
            }
        });

//选择重复的view
        mv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mview_repeat = LayoutInflater.from(EditActivity.this).inflate(R.layout.dialog_repeat, null);
                //添加定义textview
                mtv_week=mview_repeat.findViewById(R.id.re_week);
                mtv_month=mview_repeat.findViewById(R.id.re_month);
                mtv_year=mview_repeat.findViewById(R.id.re_year);
                mtv_diy=mview_repeat.findViewById(R.id.re_diy);
                final AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setView(mview_repeat).setTitle("选择周期");
                AlertDialog dialog = builder.create();
                dialog.show();
                mtv_week.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
        mv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                mview_call_camera = LayoutInflater.from(EditActivity.this).inflate(R.layout.dialog_buttom, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setView(mview_call_camera);
                final Dialog dialog=builder.create();
                dialog.show();
                TextView vcam,vtu;
                vcam=mview_call_camera.findViewById(R.id.btm_dialog_camera);
                vtu=mview_call_camera.findViewById(R.id.btm_dialog_tuku);
                vcam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(EditActivity.this, Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(EditActivity.this,new String[]{Manifest.permission.CAMERA},902);
                            }
                        }
                        else {
                        }
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //系统常量， 启动相机的关键
                        startActivityForResult(openCameraIntent, 902); // 参数常量为自定义的request code, 在取返回结果时有用
                    }
                });
                vtu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        if (ContextCompat.checkSelfPermission(EditActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(EditActivity.this, new
                                    String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            //打开系统相册
                            openAlbum();
                        }
                    }
                });
            }
        });

    }


    //下列函数为 打开图库所需函数：
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setAction(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 900);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 900 && resultCode == RESULT_OK && null != data) {
            if (Build.VERSION.SDK_INT >= 19) {
                imagePath=handleImageOnKitkat(data);
            } else {
                imagePath=handleImageBeforeKitkat(data);
            }
        }
        else if (requestCode==902&& resultCode == RESULT_OK) {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            String savePath =saveImageToGallery(bm);
        }
    }
    @TargetApi(19)
    private String handleImageOnKitkat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content:" +
                        "//downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是File类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;//返回图片路径
    }

    private String handleImageBeforeKitkat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        return imagePath;
    }
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //相机部分：

    public String saveImageToGallery(Bitmap bmp) {
        //生成路径
        String root =getApplicationContext().getExternalFilesDir("Userpic").getAbsolutePath();
        File appDir = new File(root);

        //文件名为时间
        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(timeStamp));
        String fileName = sd + ".jpg";

        //获取文件
        File file = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            //通知系统相册刷新
            EditActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(file.getPath()))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return root+File.separator+fileName;
    }

    void initPics(){
        picIds.add(R.drawable.cloud1);
        picIds.add(R.drawable.cloud2);
        picIds.add(R.drawable.cloud3);
        picIds.add(R.drawable.cloud4);
        picIds.add(R.drawable.cloud5);
    }
    private Integer resign(){
        int temp=(int)(Math.random()*picIds.size());
        return picIds.get(temp);
    }
}
