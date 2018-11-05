package com.example.lenovo.topbar.topbar.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import static java.util.Collections.addAll;


public abstract class CommonListAdapter<T> extends BaseAdapter {
    private static boolean mEnableRecycler = true;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas;
    private int mLayoutId;

    public CommonListAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    public void enableRecycler(boolean enable) {
        mEnableRecycler = enable;
    }

    public void syncData(List<T> datas) {
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T t = mDatas.get(position);
        CommonListViewHolder holder = CommonListViewHolder.get(mContext, convertView, parent, mLayoutId, position, mEnableRecycler);
        convert(holder, t);
        return holder.getConvertView();
    }

    protected abstract void convert(CommonListViewHolder holder, T t);

    public void replaceAll(List<T> list) {
        if (list != null) {
            mDatas.clear();
            addAll(list);
            notifyDataSetChanged();
        }
    }
}
