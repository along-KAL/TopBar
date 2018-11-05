package com.example.lenovo.topbar.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lenovo.topbar.R;
import com.example.lenovo.topbar.topbar.annotation.AnnotationHandle;
import com.example.lenovo.topbar.topbar.annotation.LeftClick;
import com.example.lenovo.topbar.topbar.annotation.RightClick;
import com.example.lenovo.topbar.topbar.annotation.SearchBarAttr;
import com.example.lenovo.topbar.topbar.menu.MenuBar;
import com.example.lenovo.topbar.topbar.utils.TextImageView;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static com.example.lenovo.topbar.topbar.annotation.EditTextEvent.TEXT_CHANGE.AFTER_TEXT_CHANGE;
import static com.example.lenovo.topbar.topbar.annotation.EditTextEvent.TEXT_CHANGE.BEFORE_TEXT_CHANGE;
import static com.example.lenovo.topbar.topbar.annotation.EditTextEvent.TEXT_CHANGE.ON_TEXT_CHANGE;
import static com.example.lenovo.topbar.topbar.annotation.EditTextEvent.TEXT_CHANGE.SEARCH_REQUEST;
/**
 * USE
 * <p>
 * 代码：
 * onCreate() --> AnnotationHandle.attachField(this);
 *
 * @TopViewBind(id = R.id.tl_top_bar)
 * @SearchBarAttr(backgroundColor = R.color.colorPrimaryDark,
 * leftImg = R.drawable.ic_launcher_background
 * , leftText = ""
 * , leftTextColor = Color.WHITE
 * , leftTextSize = 0
 * , leftVisible = View.VISIBLE
 * , hintText = "hint"
 * , hintTextColor = Color.BLUE
 * , editTextBackGround = R.drawable.ic_launcher_background
 * , drawableLeftImg = R.drawable.ic_launcher_foreground
 * , drawableRightImg = -1
 * , editTextVisible = View.VISIBLE
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
 * @date 2018/3/26
 */
public class SearchBar extends MenuBar implements View.OnClickListener, TextWatcher {

    private Context mContext;
    private ViewGroup mParent;
    public View mView;
    public TextImageView mLeft;
    public TextImageView mRight;
    public EditText mEtSearch;
    private SearchBarAttr mSearchBarAttr;
    private ScheduledThreadPoolExecutor mTimer;
    private Task mTask;
    private ScheduledFuture<?> schedule;
    private Editable mEditable;

    public SearchBar() {

    }

    public void init() {
        mSearchBarAttr = AnnotationHandle.getSearchBarAttr(mContext);
        mView = LayoutInflater.from(mContext).inflate(R.layout.layout_search_bar, mParent, false);
        initView();
        initTimer();
    }

    private void initView() {
        mLeft = mView.findViewById(R.id.tiv_left);
        mRight = mView.findViewById(R.id.tiv_right);
        mEtSearch = mView.findViewById(R.id.et_search);
        mLeft.setOnClickListener(this);
        mRight.setOnClickListener(this);
        mEtSearch.addTextChangedListener(this);
        if (mSearchBarAttr != null) {
            mView.setBackgroundColor(mSearchBarAttr.backgroundColor());

            mLeft.setImage(mSearchBarAttr.leftImg());
            mLeft.setText(mSearchBarAttr.leftText());
            mLeft.setTextColor(mSearchBarAttr.leftTextColor());
            mLeft.setTextSize(mSearchBarAttr.leftTextSize());
            mLeft.setVisibility(mSearchBarAttr.leftVisible());

            mRight.setImage(mSearchBarAttr.rightImg());
            mRight.setText(mSearchBarAttr.rightText());
            mRight.setTextColor(mSearchBarAttr.rightTextColor());
            mRight.setTextSize(mSearchBarAttr.rightTextSize());
            mRight.setVisibility(mSearchBarAttr.rightVisible());

            mEtSearch.setHint(mSearchBarAttr.hintText());
            mEtSearch.setHintTextColor(mSearchBarAttr.hintTextColor());
            mEtSearch.setBackgroundResource(mSearchBarAttr.editTextBackGround());
            mEtSearch.setVisibility(mSearchBarAttr.editTextVisible());

            if (!(mSearchBarAttr.drawableLeftImg() <= 0)) {
                Drawable drawableL = mContext.getResources().getDrawable(mSearchBarAttr.drawableLeftImg());
                drawableL.setBounds(0, 0, drawableL.getIntrinsicWidth(), drawableL.getIntrinsicHeight());
                mEtSearch.setCompoundDrawables(drawableL, mEtSearch.getCompoundDrawables()[1]
                        , mEtSearch.getCompoundDrawables()[2], mEtSearch.getCompoundDrawables()[3]);
            }

            if (!(mSearchBarAttr.drawableRightImg() <= 0)) {
                Drawable drawableR = mContext.getResources().getDrawable(mSearchBarAttr.drawableRightImg());
                drawableR.setBounds(0, 0, drawableR.getIntrinsicWidth(), drawableR.getIntrinsicHeight());
                mEtSearch.setCompoundDrawables(mEtSearch.getCompoundDrawables()[0], mEtSearch.getCompoundDrawables()[1]
                        , drawableR, mEtSearch.getCompoundDrawables()[3]);
            }
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

    }

    @Override
    public void attachMenu(final View view, final MenuBar.Menu menu) {
        final MenuPop menuPop = new MenuPop(mContext, menu);
        mViewMenuMap.put(view, menuPop);
    }

    @Override
    public void setViewByTypedArray(TypedArray typedArray) {
        if (mSearchBarAttr != null || typedArray == null) {
            return;
        }
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

        mEtSearch.setHint(typedArray.getString(R.styleable.BarView_hintText));
        mEtSearch.setHintTextColor(typedArray.getColor(R.styleable.BarView_hintTextColor, Color.BLACK));
        mEtSearch.setBackgroundResource(typedArray.getResourceId(R.styleable.BarView_searchViewBackground, R.color.cardview_dark_background));
        mEtSearch.setVisibility(typedArray.getInt(R.styleable.BarView_rightVisible, View.VISIBLE));


        int resourceId3 = typedArray.getResourceId(R.styleable.BarView_searchViewDrawableLeft, -1);
        if (resourceId3 != -1) {
            Drawable drawableL = mContext.getResources().getDrawable(resourceId3);
            drawableL.setBounds(0, 0, drawableL.getIntrinsicWidth(), drawableL.getIntrinsicHeight());
            mEtSearch.setCompoundDrawables(drawableL, mEtSearch.getCompoundDrawables()[1]
                    , mEtSearch.getCompoundDrawables()[2], mEtSearch.getCompoundDrawables()[3]);
        }
        int resourceId4 = typedArray.getResourceId(R.styleable.BarView_searchViewDrawableRight, -1);
        if (resourceId4 != -1) {
            Drawable drawableR = mContext.getResources().getDrawable(resourceId4);
            drawableR.setBounds(0, 0, drawableR.getIntrinsicWidth(), drawableR.getIntrinsicHeight());
            mEtSearch.setCompoundDrawables(mEtSearch.getCompoundDrawables()[0], mEtSearch.getCompoundDrawables()[1]
                    , drawableR, mEtSearch.getCompoundDrawables()[3]);
        }
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

    private void initTimer() {
        mTask = new Task();
        if (mTimer == null) {
            mTimer = new ScheduledThreadPoolExecutor(10, new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable run) {
                    return new Thread(run, "SearchBar" + "-Thread-SearchBar");
                }
            });
        }
    }

    public class Task implements Runnable {
        @Override
        public void run() {
            AnnotationHandle.onTextChangeInvoke(mContext, SEARCH_REQUEST, mEditable, "", 0, 0, 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        AnnotationHandle.onTextChangeInvoke(mContext, BEFORE_TEXT_CHANGE, null, s, start, count, after);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        AnnotationHandle.onTextChangeInvoke(mContext, ON_TEXT_CHANGE, null, s, start, before, count);
    }

    @Override
    public void afterTextChanged(Editable e) {
        mEditable = e;
        if (schedule != null) {
            schedule.cancel(true);
        }
        schedule = mTimer.schedule(mTask, 1, TimeUnit.SECONDS);
        AnnotationHandle.onTextChangeInvoke(mContext, AFTER_TEXT_CHANGE, e, "", 0, 0, 0);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
