package com.learn.lister.pagerslide.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learn.lister.pagerslide.R;
import com.learn.lister.pagerslide.activity.LocalMusicActivity;
import com.learn.lister.pagerslide.activity.MainActivity;

/**
 * Created by Lister on 2017-05-18.
 */

public class Fragment1 extends BaseFragment {

    View view;
    TextView textView;
    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment1, null);
        return view;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment1,null);
        textView=view.findViewById(R.id.localMusic);

        return view;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LocalMusicActivity.class);
                startActivity(intent);
            }
        });
    }
}
