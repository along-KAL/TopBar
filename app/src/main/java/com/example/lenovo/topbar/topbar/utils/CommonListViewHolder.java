package com.example.lenovo.topbar.topbar.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * @author along
 */
public class CommonListViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public CommonListViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static CommonListViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position, boolean enableRecycler) {
        if (enableRecycler == false || convertView == null) {
            return new CommonListViewHolder(context, parent, layoutId, position);
        } else {
            CommonListViewHolder holder = (CommonListViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }


    /**
     * 通过viewId获取控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public CommonListViewHolder setTextViewText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public CommonListViewHolder setTextViewText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getPosition() {
        return mPosition;
    }
}
