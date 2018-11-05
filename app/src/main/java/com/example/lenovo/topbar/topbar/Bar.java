package com.example.lenovo.topbar.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.topbar.topbar.menu.MenuBar;

import java.util.Map;

/**
 * Description
 *
 * @author along
 * @date 2018/3/22
 */
public interface Bar {
    /**
     * 关联TopBar
     *
     * @param context
     * @param topBar
     * @return
     */
    void attachTopBar(Context context, ViewGroup topBar);

    /**
     * 获取bar布局
     *
     * @return
     */
    View getTopBarView();

    /**
     * 获取view集合
     *
     * @param map
     */
    void getViewMap(Map<Integer, View> map);

    /**
     * view关联菜单
     *
     * @param view
     * @param menu
     */
    void attachMenu(View view, MenuBar.Menu menu);


    /**
     * 设置布局文件中的属性
     * @param typedArray
     */
    void setViewByTypedArray(TypedArray typedArray);
}
