package com.example.lenovo.topbar.topbar.menu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.lenovo.topbar.topbar.Bar;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 *
 * @author along
 * @date 2018/3/27
 */
public abstract class MenuBar implements Bar, View.OnClickListener {

    public Map<View, MenuPop> mViewMenuMap = new HashMap<>();

    public interface Menu {
        /**
         * 获取菜单ID
         *
         * @return
         */
        int getLayoutId();

        /**
         * 初始化菜单View
         *
         * @param view
         */
        void initView(View view);

    }


    public class MenuPop extends PopupWindow {
        public Menu mMenu;
        protected View mContentView;
        protected Context mContext;

        public void setMenuView(Menu menu) {
            mMenu = menu;
        }

        @Override
        public View getContentView() {
            return mContentView;
        }

        public MenuPop(Context context, Menu menu) {
            mContext = context;
            mMenu = menu;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mContentView = inflater.inflate(mMenu.getLayoutId(), null);
            setContentView(mContentView);
            setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            setFocusable(true);
            setOutsideTouchable(true);
            update();
            ColorDrawable dw = new ColorDrawable(0x0000000000);
            setBackgroundDrawable(dw);
            mMenu.initView(getContentView());
        }

        public void show(View view, int marginTop) {
            showAtLocation(view, Gravity.RIGHT | Gravity.TOP, 0, marginTop);
        }
    }

    @Override
    public void onClick(View v) {
        MenuPop menuPop = mViewMenuMap.get(v);
        if (menuPop != null) {
            menuPop.show(v, v.getHeight());
        }
    }
}
