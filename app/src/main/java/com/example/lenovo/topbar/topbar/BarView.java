package com.example.lenovo.topbar.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.lenovo.topbar.R;

/**
 * Description
 *
 * @author along
 * @date 2018/3/22
 */
public class BarView extends RelativeLayout {

    /**
     * 普通bar
     */
    public static final int NORMAL_TYPE = 0;
    /**
     * 带菜单bar
     */
    public static final int SEARCH_TYPE = 1;

    private Bar mBarView;
    private Context mContext;
    private int mType;
    private String mCustomMenu;

    public BarView(Context context) {
        super(context);
        init(context, null);
    }

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BarView);
        mType = ta.getInt(R.styleable.BarView_bar_type, 0);
        mCustomMenu = ta.getString(R.styleable.BarView_customer_menu);
        init(context, ta);
        ta.recycle();
    }

    public BarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    private void init(Context context, TypedArray typedArray) {
        mContext = context;
        initBar(typedArray);
    }

    private void initBar(TypedArray typedArray) {
        if (!TextUtils.isEmpty(mCustomMenu)) {
            try {
                Class<?> clazz = Class.forName(mCustomMenu);
                mBarView = (Bar) clazz.newInstance();
                mBarView.attachTopBar(mContext, this);
                this.addView(mBarView.getTopBarView());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return;
        }
        switch (mType) {
            case NORMAL_TYPE:
                mBarView = new NormalBar();
                break;
            case SEARCH_TYPE:
                mBarView = new SearchBar();
                break;
            default:
                break;
        }
        mBarView.attachTopBar(mContext, this);
        this.addView(mBarView.getTopBarView());
        mBarView.setViewByTypedArray(typedArray);
    }

    public Bar getBarView() {
        return mBarView;
    }

    private void setBarView(Bar barView) {
        mBarView = barView;
    }

    public View getView(int id) {
        return findViewById(id);
    }
}
