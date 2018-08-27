package com.lxy.complexrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxy.dexlibs.ComplexRecyclerViewAdapter;

import java.util.List;

/**
 * Created by LXY on 2018/8/23.
 */

public class Demo extends ComplexRecyclerViewAdapter {


    public Demo(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    protected View onBindLayout(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.item_home, parent, false);
    }

    @Override
    protected void onBindViewData(Object data, ComplexRecyclerViewAdapter.ComplexViewHolder holder, int position) {
        holder.setText(R.id.textview, data.toString());
    }
}
