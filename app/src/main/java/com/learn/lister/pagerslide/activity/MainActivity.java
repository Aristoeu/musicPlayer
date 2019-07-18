package com.learn.lister.pagerslide.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.app.NotificationCompat;
import androidx.core.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learn.lister.pagerslide.R;
import com.learn.lister.pagerslide.adapter.PagerSlideAdapter;
import com.learn.lister.pagerslide.fragment.BaseFragment;
import com.learn.lister.pagerslide.fragment.Fragment1;
import com.learn.lister.pagerslide.fragment.Fragment2;
import com.learn.lister.pagerslide.fragment.Fragment3;
import com.learn.lister.pagerslide.fragment.Fragment4;
import com.learn.lister.pagerslide.utils.MusicUtils;
import com.learn.lister.pagerslide.utils.Song;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.page_0) TextView text0;
    @BindView(R.id.page_1) TextView text1;
    @BindView(R.id.page_2) TextView text2;
    @BindView(R.id.page_3) TextView text3;
    @BindView(R.id.main_tab_line) ImageView tab_line;
    @BindView(R.id.main_pager) ViewPager mViewPager;

    private int screenWidth;
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private PagerSlideAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initWidth();
        setListener();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }
sendChatMsg(getCurrentFocus());
    }
    public void sendChatMsg(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "chat")
                .setContentTitle("收到一条聊天消息")
                .setContentText("今天中午吃什么？")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.actionbar_music_normal)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.actionbar_music_normal))
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    private void initData() {
        mFragmentList.add(new Fragment1());
        mFragmentList.add(new Fragment2());
        mFragmentList.add(new Fragment3());
        mFragmentList.add(new Fragment4());
        adapter = new PagerSlideAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        text0.setTextColor(Color.BLUE);
    }



    private void setListener() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_line.getLayoutParams();
                lp.leftMargin = screenWidth/4*position + positionOffsetPixels/4;
                tab_line.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        text0.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        text1.setTextColor(Color.BLUE);
                        break;
                    case 2:
                        text2.setTextColor(Color.BLUE);
                        break;
                    case 3:
                        text3.setTextColor(Color.BLUE);
                        //sendChatMsg(getCurrentFocus());
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        text0.setOnClickListener(this);
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);

    }

    private void resetTextView() {
        text0.setTextColor(Color.BLACK);
        text1.setTextColor(Color.BLACK);
        text2.setTextColor(Color.BLACK);
        text3.setTextColor(Color.BLACK);
    }

    private void initWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_line.getLayoutParams();
        lp.width = screenWidth / 4;
        tab_line.setLayoutParams(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_0:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.page_1:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.page_2:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.page_3:
                mViewPager.setCurrentItem(3);
                break;
        }
    }

    public class MyAdapter extends BaseAdapter {
        private Context context;
        private List<Song> list;

        public MyAdapter(List<Song> list) {
            this.context = MainActivity.this;
            this.list = list;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                //引入布局
                view = View.inflate(context, R.layout.item_music_listview, null);
                //实例化对象
                holder.song = (TextView) view.findViewById(R.id.item_mymusic_song);
                holder.singer = (TextView) view.findViewById(R.id.item_mymusic_singer);
                holder.duration = (TextView) view.findViewById(R.id.item_mymusic_duration);
                holder.position = (TextView) view.findViewById(R.id.item_mymusic_postion);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            //给控件赋值
            holder.song.setText(list.get(i).song.toString());
            holder.singer.setText(list.get(i).singer.toString());
            //时间需要转换一下
            int duration = list.get(i).duration;
            String time = MusicUtils.formatTime(duration);
            holder.duration.setText(time);
            holder.position.setText(i + 1 + "");

            return view;
        }

        class ViewHolder {
            TextView song;//歌曲名
            TextView singer;//歌手
            TextView duration;//时长
            TextView position;//序号

        }

    }

}

