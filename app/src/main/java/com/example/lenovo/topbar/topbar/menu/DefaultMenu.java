package com.example.lenovo.topbar.topbar.menu;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.topbar.R;
import com.example.lenovo.topbar.topbar.utils.CommonListAdapter;
import com.example.lenovo.topbar.topbar.utils.CommonListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author lenovo
 * @date 2018/3/28
 */
public class DefaultMenu implements MenuBar.Menu {

    private Context mContext;
    private ListView lvContent;
    private List<MenuBean> mData = new ArrayList<>();

    public DefaultMenu(Context context) {
        this.mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.popupwindow_bar_menu;
    }

    @Override
    public void initView(View view) {

        lvContent = view.findViewById(R.id.lv_content);
        lvContent.setAdapter(new CommonListAdapter<MenuBean>(mContext,
                R.layout.listitem_popupwindow_bar_menu, this.mData) {
            @Override
            public void convert(CommonListViewHolder helper, MenuBean item) {
                TextView tvTitle = helper.getView(R.id.tv_title);
                tvTitle.setGravity(item.gravity);
                tvTitle.setTextSize(dip2px(mContext, item.textSize));
                helper.setTextViewText(R.id.tv_title, item.text);
                helper.getConvertView().setClickable(item.clickAble);
                ((TextView) helper.getView(R.id.tv_title)).setTextColor(mContext.getResources().getColor(item.textColor));
            }
        });
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnMenuClickListener != null) {
                    mOnMenuClickListener.onMenuClick(parent, view, position, id, mData.get(position).tag);
                }
            }
        });
    }

    /**
     * 设置菜单数据
     *
     * @param menuData
     */
    public void setMenuData(List<MenuBean> menuData) {
        if (menuData == null) {
            return;
        }
        mData.addAll(menuData);
    }


    private OnMenuClickListener mOnMenuClickListener;

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.mOnMenuClickListener = onMenuClickListener;
    }

    public interface OnMenuClickListener {
        /**
         * 菜单点击回掉
         *
         * @param parent
         * @param view
         * @param position
         * @param id
         * @param tag
         */
        void onMenuClick(AdapterView<?> parent, View view, int position, long id, String tag);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static class MenuBean {
        public String tag;
        public String text;
        public int textColor;
        public int textSize;
        public int gravity;
        public boolean clickAble;
    }
}
