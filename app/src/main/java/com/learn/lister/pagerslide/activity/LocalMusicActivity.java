package com.learn.lister.pagerslide.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.learn.lister.pagerslide.R;
import com.learn.lister.pagerslide.utils.MusicUtils;
import com.learn.lister.pagerslide.utils.Song;

import java.util.ArrayList;
import java.util.List;

public class LocalMusicActivity extends AppCompatActivity {

    private ListView mListView;
    private List<Song> list;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        if (ContextCompat.checkSelfPermission(LocalMusicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LocalMusicActivity.this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE }, 2);
        }else
            initView();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initView();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
            default:
        }
    }
    /**
     * 初始化view
     */
    private void initView() {
        mListView = (ListView) findViewById(R.id.main_listview);
        list = new ArrayList<>();
        //把扫描到的音乐赋值给list
        Cursor cursor = this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        //assert cursor != null;
        //Toast.makeText(this,cursor.toString(),Toast.LENGTH_LONG).show();
        list = MusicUtils.getMusicData(this);
        if (list==null)
            Toast.makeText(this,"null",Toast.LENGTH_LONG).show();
        //else Toast.makeText(this,list.get(79).path,Toast.LENGTH_LONG).show();

        adapter = new MyAdapter(LocalMusicActivity.this, list);
        mListView.setAdapter(adapter);
    }

    public class MyAdapter extends BaseAdapter {
        private Context context;
        private List<Song> list;

        public MyAdapter(LocalMusicActivity mainActivity, List<Song> list) {
            this.context = mainActivity;
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
