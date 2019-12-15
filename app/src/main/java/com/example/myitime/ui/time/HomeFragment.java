package com.example.myitime.ui.time;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.data.EveItem;
import com.data.ItemDataSource;
import com.example.myitime.InnerShowActivity;
import com.example.myitime.R;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {
    private ListView mListView;
    private ItemAdapter itemAdapter;
    private ItemDataSource itemDataSource;
    private ArrayList<EveItem> items;
    private com.youth.banner.Banner banner;
    private MyImageLoader mMyImageLoader;
    private ArrayList<String> imagepath;
    private ArrayList<String> imagetitle;
    private View root;
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_time, container, false);
        itemDataSource=new ItemDataSource(root.getContext());
        items=itemDataSource.load();
        itemAdapter=new ItemAdapter(root.getContext(),R.layout.item_main,items);
        banner=root.findViewById(R.id.main_banner);
        if(items.size()==0){
            banner.setVisibility(View.INVISIBLE);
        }else{
            banner.setVisibility(View.VISIBLE);
        }
        imagetitle=new ArrayList<String>();
        imagepath=new ArrayList<String>();
        mMyImageLoader=new MyImageLoader();
        initbanner();
        initView();
        banner.isAutoPlay(true);
        banner.start();
        mListView=root.findViewById(R.id.main_list_view);
        mListView.setAdapter(itemAdapter);
        itemAdapter.setNotifyOnChange(true);
        mListView.setDivider(new ColorDrawable(Color.WHITE));
        mListView.setDividerHeight(20);
//        MyThread myThread=new MyThread();
//        myThread.start();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x11) {
                    banner.setBannerTitles(imagetitle);
                }
            }
        };
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    ArrayList<String> title = new ArrayList<String>();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(items!=null){
                        for (EveItem a : items) {
                            title.add(a.getTitle() + setTime(a.getYear() + "-" + a.getMonth() + "-" + a.getDay() + " " + a.getHour() + ":" + a.getMonth() + ":" + "00"));
                        }
                        imagetitle = title;
                        Message message=new Message();
                        message.what = 0x11;
                        handler.sendMessage(message);
                    }

                }
            }
        }).start();


        return root;
    }


    private void initbanner(){
        for(EveItem a:items){
            imagepath.add(a.getImagePath());
            imagetitle.add(a.getTitle()+"\n"+setTime(a.getYear()+"-"+a.getMonth()+"-"+a.getDay()+" "+a.getHour()+":"+a.getMonth()+":"+"00"));
            //修改imagetitle里面的每一个Sstring使用date然后开个线程去修改所有
            //即我们只要修改数据，然后刷新
            //给个定时器每秒刷新
        }
    }
    private void initView() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(mMyImageLoader);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        banner.setBannerAnimation(Transformer.ZoomOutSlide);
        //轮播图片的文字
        banner.setBannerTitles(imagetitle);
        //设置轮播间隔时间
        banner.setDelayTime(1500);
        //设置是否为自动轮播，默认是true
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载地址
        banner.setImages(imagepath)
        //轮播图的监听
         .setOnBannerListener(new OnBannerListener() {
             @Override
             public void OnBannerClick(int position) {
                 Intent intent=new Intent(root.getContext(), InnerShowActivity.class);
                 intent.putExtra("id",position);
                 startActivity(intent);
             }
         });
//                //开始调用的方法，启动轮播图。
//               .start();

    }


    private class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        items=itemDataSource.load();
        itemAdapter.clear();
        itemAdapter.addAll(items);
        mListView.setAdapter(itemAdapter);
        ArrayList<String> path=new ArrayList<String>();
        ArrayList<String> title=new ArrayList<String>();
        for(EveItem a:items) {
            path.add(a.getImagePath());
            title.add(a.getTitle() + "\n" + setTime(a.getYear() + "-" + a.getMonth() + "-" + a.getDay() + " " + a.getHour() + ":" + a.getMonth() + ":" + "00"));
        }
        if (items.size()!=0){
            banner.setVisibility(View.VISIBLE);
        }else{
            banner.setVisibility(View.INVISIBLE);
        }
        imagepath.clear();
        imagepath.addAll(path);
        banner.update(path,title);
    }

//    private class MyThread extends Thread{
//        private Boolean beAlive = true;
//        public void stopThread() {
//            beAlive = false;
//        }
//        //重写run方法
//        @Override
//        public void run() {
//            //任务内容....
//            while(beAlive){
//                ArrayList<String> title=new ArrayList<String>();
//                for(EveItem a:items){
//                    title.add(a.getTitle()+setTime(a.getYear()+"-"+a.getMonth()+"-"+a.getDay()+" "+a.getHour()+":"+a.getMonth()+":"+"00"));
//                }
//                imagetitle=title;
//                try {
//                    sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    @SuppressLint("SetTextI18n")
    public static String setTime(String failureTime) {

        String temp=null;
        @SuppressLint("SimpleDateFormat")
        // ★★★格式("yyyy-MM-dd HH:mm:ss")一定要写对★★★   不然会出现时间错乱
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // Date nowTime = new Date(System.currentTimeMillis()); //获取当前时间的另一种
            long nowTime = System.currentTimeMillis(); //获取当前时间
            Date overTime = simpleDateFormat.parse(failureTime); //利用SimpleDateFormat来把字符串日期转换为Date对象类型
            long a = overTime.getTime() - nowTime; // 计算总的时间差（毫秒级别）
            if (a > 0) { //如果大于零 说明时间有剩余

                // 总时间（a）
                // 总天数( day*(1000 * 60 * 60 * 24) )
                // 总小时数（ hour * (1000 * 60 * 60) ）
                // 总分钟数（ minute * (1000 * 60) ）

                // 总时间 中有几天
                long day = a / (1000 * 60 * 60 * 24);
                // 总时间 去除天数后，还剩多少小时
                long hour = (a - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                // 总时间 去除天数与小时后，还剩多少分钟
                long minute = (a - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                // 总时间 去除天数小时与分钟后，还剩多少秒
                long second = (a - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                //刷新ui显示
                temp=("剩余:" + day + "天" + hour + "时" + minute + "分" + second + "秒");
            }else {
                a=Math.abs(a);
                long day = a / (1000 * 60 * 60 * 24);
                // 总时间 去除天数后，还剩多少小时
                long hour = (a - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                // 总时间 去除天数与小时后，还剩多少分钟
                long minute = (a - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
                // 总时间 去除天数小时与分钟后，还剩多少秒
                long second = (a - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                //刷新ui显示
               temp=("已经过了:" + day + "天" + hour + "时" + minute + "分" + second + "秒");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
}
