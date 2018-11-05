package com.example.lenovo.topbar.topbar.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
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
public @interface NormalBarAttr {
    public int backgroundColor();

    public int leftImg();

    public String leftText();

    public int leftTextSize();

    public int leftTextColor();

    public int leftVisible();


    public int titleImg();

    public String titleText();

    public int titleTextSize();

    public int titleTextColor();

    public int titleVisible();


    public int rightImg();

    public String rightText();

    public int rightTextSize();

    public int rightTextColor();

    public int rightVisible();

}
