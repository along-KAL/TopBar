package com.example.lenovo.topbar.topbar.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.topbar.R;

/**
 * Description
 *
 * @author along
 * @date 2018/3/23
 */
public class TextImageView extends FrameLayout {

    public static final int TEXT_TYPE = 0;
    public static final int IMAGE_TYPE = 1;

    private Context mContext;
    private int mType;
    private String mText;
    private int mImage;
    private int mTextColor;
    private float mTextSize;
    private TextView mTvText;
    private ImageView mIvImage;
    private int mLeftImage;
    private int mRightImage;

    public TextImageView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public TextImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextImageView);
        mType = ta.getInt(R.styleable.TextImageView_type, 0);
        mText = ta.getString(R.styleable.TextImageView_text);
        mImage = ta.getResourceId(R.styleable.TextImageView_image, 0);
        mLeftImage = ta.getResourceId(R.styleable.TextImageView_drawableLeft, 0);
        mRightImage = ta.getResourceId(R.styleable.TextImageView_drawableRight, 0);
        mTextColor = ta.getColor(R.styleable.TextImageView_textColor, Color.parseColor("#FF000000"));
        mTextSize = ta.getDimension(R.styleable.TextImageView_textSize, dip2px(context, 10));
        ta.recycle();
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_text_image, this, true);
        mTvText = view.findViewById(R.id.tv_text);
        mIvImage = view.findViewById(R.id.iv_image);
    }

    private void setView() {
        if (!TextUtils.isEmpty(mText)) {
            mTvText.setVisibility(VISIBLE);
            mTvText.setText(mText);
            mTvText.setTextSize(mTextSize);
            mTvText.setTextColor(mTextColor);
            if (mLeftImage > 0) {
                Drawable drawableR = mContext.getResources().getDrawable(mLeftImage);
                drawableR.setBounds(0, 0, drawableR.getIntrinsicWidth(), drawableR.getIntrinsicHeight());
                mTvText.setCompoundDrawables(mTvText.getCompoundDrawables()[0], mTvText.getCompoundDrawables()[1]
                        , drawableR, mTvText.getCompoundDrawables()[3]);
            }
            if (mRightImage > 0) {
                Drawable drawableR = mContext.getResources().getDrawable(mRightImage);
                drawableR.setBounds(0, 0, drawableR.getIntrinsicWidth(), drawableR.getIntrinsicHeight());
                mTvText.setCompoundDrawables(mTvText.getCompoundDrawables()[0], mTvText.getCompoundDrawables()[1]
                        , drawableR, mTvText.getCompoundDrawables()[3]);
            }
        } else if (mImage > 0) {
            mIvImage.setVisibility(VISIBLE);
            mIvImage.setImageResource(mImage);
        } else {
            switch (mType) {
                case TEXT_TYPE:
                    mTvText.setVisibility(VISIBLE);
                    mTvText.setText(mText);
                    mTvText.setTextSize(mTextSize);
                    mTvText.setTextColor(mTextColor);
                    break;
                case IMAGE_TYPE:
                    mIvImage.setVisibility(VISIBLE);
                    mIvImage.setImageResource(mImage);
                    break;
                default:
                    break;
            }
        }
    }

    public void setText(String mText) {
        this.mText = mText;
        setView();
    }

    public void setImage(int mImage) {
        this.mImage = mImage;
        setView();
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        setView();
    }

    public void setTextSize(float mTextSize) {
        this.mTextSize = dip2px(mContext, mTextSize);
        setView();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
