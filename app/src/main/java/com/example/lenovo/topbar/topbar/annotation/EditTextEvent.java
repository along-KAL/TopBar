package com.example.lenovo.topbar.topbar.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description
 *
 * @author along
 * @date 2018/3/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EditTextEvent {
    /**
     * mEtSearch.addTextChangedListener(this);事件
     */
    enum TEXT_CHANGE {
        BEFORE_TEXT_CHANGE,
        ON_TEXT_CHANGE,
        AFTER_TEXT_CHANGE,
        /**
         * 搜索时防止重复请求接口
         */
        SEARCH_REQUEST;
    }

    public TEXT_CHANGE textChange();
}
