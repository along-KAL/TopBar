package com.example.lenovo.topbar.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.topbar.R;
import com.example.lenovo.topbar.topbar.annotation.AnnotationHandle;
import com.example.lenovo.topbar.topbar.annotation.LeftClick;
import com.example.lenovo.topbar.topbar.annotation.NormalBarAttr;
import com.example.lenovo.topbar.topbar.annotation.RightClick;
import com.example.lenovo.topbar.topbar.menu.MenuBar;
import com.example.lenovo.topbar.topbar.utils.TextImageView;

import java.util.Map;

/**
 * USE
 * <p>
 * 代码：
 * onCreate() --> AnnotationHandle.attachField(this);
 *
 * @TopViewBind(id = R.id.tl_top_bar)
 * @NormalBarAttr(backgroundColor = R.color.colorPrimaryDark,
 * leftImg = R.drawable.ic_launcher_background
 * , leftText = ""
 * , leftTextColor = Color.WHITE
 * , leftTextSize = 0
 * , leftVisible = View.VISIBLE
 * , titleText = "title"
 * , titleTextColor = Color.BLUE
 * , titleTextSize = 12
 * , titleImg = R.drawable.ic_launcher_foreground
 * , titleVisible = View.VISIBLE
 * , rightImg = 0
 * , rightText = "right"
 * , rightTextColor = Color.WHITE
 * , rightTextSize = 10
 * , rightVisible = View.VISIBLE
 * )
 * private TopBar mTopBar;
 * <p>
 * 布局：
 * <com.example.lenovo.topbar.topbar.TopBar
 * android:id="@+id/tl_top_bar"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"/>
 */

/**
 * Description
 *
 * @author along
 * @date 2018/3/22
 */
public class NormalBar extends MenuBar implements View.OnClickListener {

    private Context mContext;
    private ViewGroup mParent;
    public View mView;
    private NormalBarAttr mNormalBarAttr;
    public TextImageView mLeft;
    public TextImageView mRight;
    public TextImageView mTitle;

    public NormalBar() {

    }

    public void init() {
        mNormalBarAttr = AnnotationHandle.getNormalBarAttr(mContext);
        mView = LayoutInflater.from(mContext).inflate(R.layout.layout_normal_bar, mParent, false);
        initView();
    }

    private void initView() {
        mLeft = mView.findViewById(R.id.tiv_left);
        mRight = mView.findViewById(R.id.tiv_right);
        mTitle = mView.findViewById(R.id.tiv_title);
        mLeft.setOnClickListener(this);
        mRight.setOnClickListener(this);

        if (mNormalBarAttr != null) {
            mView.setBackgroundColor(mNormalBarAttr.backgroundColor());

            mLeft.setImage(mNormalBarAttr.leftImg());
            mLeft.setText(mNormalBarAttr.leftText());
            mLeft.setTextColor(mNormalBarAttr.leftTextColor());
            mLeft.setTextSize(mNormalBarAttr.leftTextSize());
            mLeft.setVisibility(mNormalBarAttr.leftVisible());

            mRight.setImage(mNormalBarAttr.rightImg());
            mRight.setText(mNormalBarAttr.rightText());
            mRight.setTextColor(mNormalBarAttr.rightTextColor());
            mRight.setTextSize(mNormalBarAttr.rightTextSize());
            mRight.setVisibility(mNormalBarAttr.rightVisible());

            mTitle.setImage(mNormalBarAttr.titleImg());
            mTitle.setText(mNormalBarAttr.titleText());
            mTitle.setTextColor(mNormalBarAttr.titleTextColor());
            mTitle.setTextSize(mNormalBarAttr.titleTextSize());
            mTitle.setVisibility(mNormalBarAttr.titleVisible());
        }
    }

    @Override
    public void attachTopBar(Context context, ViewGroup topBar) {
        mContext = context;
        mParent = topBar;
        init();
    }

    @Override
    public View getTopBarView() {
        return mView;
    }

    @Override
    public void getViewMap(Map<Integer, View> map) {
        map.put(R.id.tiv_title, mView.findViewById(R.id.tiv_title));
    }

    @Override
    public void attachMenu(final View view, final MenuBar.Menu menu) {
        final MenuPop menuPop = new MenuPop(mContext, menu);
        mViewMenuMap.put(view, menuPop);
    }

    @Override
    public void setViewByTypedArray(TypedArray typedArray) {
        int resourceId = typedArray.getResourceId(R.styleable.BarView_leftImage, -1);
        if (resourceId != -1) {
            mLeft.setImage(resourceId);
        }
        mLeft.setText(typedArray.getString(R.styleable.BarView_leftText));
        mLeft.setTextColor(typedArray.getResourceId(R.styleable.BarView_leftTextColor, Color.WHITE));
        mLeft.setTextSize(typedArray.getDimension(R.styleable.BarView_leftTextSize, dip2px(mContext, 10)));
        mLeft.setVisibility(typedArray.getInt(R.styleable.BarView_leftVisible, View.VISIBLE));
        int resourceId2 = typedArray.getResourceId(R.styleable.BarView_rightImage, -1);
        if (resourceId2 != -1) {
            mRight.setImage(resourceId2);
        }
        mRight.setText(typedArray.getString(R.styleable.BarView_rightText));
        mRight.setTextColor(typedArray.getResourceId(R.styleable.BarView_rightTextColor, Color.WHITE));
        mRight.setTextSize(typedArray.getDimension(R.styleable.BarView_rightTextSize, dip2px(mContext, 10)));
        mRight.setVisibility(typedArray.getInt(R.styleable.BarView_rightVisible, View.VISIBLE));

        int resourceId3 = typedArray.getResourceId(R.styleable.BarView_titleImage, -1);
        if (resourceId3 != -1) {
            mTitle.setImage(resourceId3);
        }
        mTitle.setText(typedArray.getString(R.styleable.BarView_titleText));
        mTitle.setTextColor(typedArray.getResourceId(R.styleable.BarView_titleTextColor, Color.WHITE));
        mTitle.setTextSize(typedArray.getDimension(R.styleable.BarView_titleTextSize, dip2px(mContext, 10)));
        mTitle.setVisibility(typedArray.getInt(R.styleable.BarView_titleVisible, View.VISIBLE));
    }

    private void handleLeftClick(View v) {
        AnnotationHandle.onClickInvoke(mContext, LeftClick.class, v);
    }

    private void handleRightClick(View v) {
        AnnotationHandle.onClickInvoke(mContext, RightClick.class, v);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.tiv_left) {
            handleLeftClick(v);
        } else if (id == R.id.tiv_right) {
            handleRightClick(v);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
