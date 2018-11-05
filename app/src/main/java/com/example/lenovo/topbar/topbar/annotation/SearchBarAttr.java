package com.example.lenovo.topbar.topbar.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description
 *
 * @author along
 * @date 2018/3/22
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SearchBarAttr {
    public int backgroundColor();

    public int leftImg();

    public String leftText();

    public int leftTextSize();

    public int leftTextColor();

    public int leftVisible();


    public String hintText();

    public int hintTextColor();

    public int editTextBackGround();

    public int drawableLeftImg();

    public int drawableRightImg();

    public int editTextVisible();


    public int rightImg();

    public String rightText();

    public int rightTextSize();

    public int rightTextColor();

    public int rightVisible();

}
