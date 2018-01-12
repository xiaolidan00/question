package com.lidan.xiao.danquestion.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.lidan.xiao.danquestion.R;
import com.lidan.xiao.danquestion.activity.SearchActivity;

/**
 * Created by Administrator on 2018/1/7.
 */

public class SearchFragment extends Fragment {
    private View rootView;
    private TextView sv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_search,container,false);
        this.rootView=rootView;
        sv=rootView.findViewById(R.id.tv_sv);
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
