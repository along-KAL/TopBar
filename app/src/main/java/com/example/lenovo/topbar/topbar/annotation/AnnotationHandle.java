package com.example.lenovo.topbar.topbar.annotation;

import android.app.Activity;
import android.text.Editable;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author along
 * @date 2018/3/22
 */
public class AnnotationHandle {

    /**
     * 获取普通bar注解
     *
     * @param obj
     * @return
     */
    public static NormalBarAttr getNormalBarAttr(Object obj) {
        List<Object> annotation = getAnnotation(obj, NormalBarAttr.class);
        if (annotation.isEmpty()) {
            return null;
        }
        return (NormalBarAttr) annotation.get(0);
    }

    /**
     * 获取搜索bar注解
     *
     * @param obj
     * @return
     */
    public static SearchBarAttr getSearchBarAttr(Object obj) {
        List<Object> annotation = getAnnotation(obj, SearchBarAttr.class);
        if (annotation == null || annotation.isEmpty()) {
            return null;
        }
        return (SearchBarAttr) annotation.get(0);
    }

    /**
     * 获取topBar bind注解
     *
     * @param obj
     * @return
     */
    public static TopViewBind getTopViewBind(Object obj) {
        List<Object> annotation = getAnnotation(obj, TopViewBind.class);
        if (annotation.isEmpty()) {
            return null;
        }
        return (TopViewBind) annotation.get(0);
    }

    /**
     * 获取topBar click注解
     *
     * @param obj
     * @return
     */
    public static List<LeftClick> getLeftClick(Object obj) {
        List<Object> annotation = getAnnotation(obj, LeftClick.class);
        if (annotation.isEmpty()) {
            return null;
        }
        List<LeftClick> topViewClicks = new ArrayList<>();
        for (Object o : annotation) {
            topViewClicks.add((LeftClick) o);
        }
        return topViewClicks;
    }

    /**
     * 获取topBar click注解
     *
     * @param obj
     * @return
     */
    public static List<RightClick> getRightClick(Object obj) {
        List<Object> annotation = getAnnotation(obj, RightClick.class);
        if (annotation.isEmpty()) {
            return null;
        }
        List<RightClick> topViewClicks = new ArrayList<>();
        for (Object o : annotation) {
            topViewClicks.add((RightClick) o);
        }
        return topViewClicks;
    }

    public static List<Object> getAnnotation(Object obj, Class annClazz) {
        android.support.v4.app.Fragment fragment = null;
        Activity activity = null;
        if (obj instanceof Activity) {
            activity = (Activity) obj;
        } else if (obj instanceof android.support.v4.app.Fragment) {
            fragment = (android.support.v4.app.Fragment) obj;
        } else {
            return null;
        }
        if (activity != null) {
            return getActivityAnn(activity, annClazz);
        } else if (fragment != null) {
            return getFragmentAnn(fragment, annClazz);
        }
        return null;
    }

    public static List<Object> getActivityAnn(Activity activity, Class annClazz) {
        Class clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Object> list = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object annotation = field.getAnnotation(annClazz);
            if (annotation != null) {
                list.add(annotation);
            }
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            Object annotation = method.getAnnotation(annClazz);
            if (annotation != null) {
                list.add(annotation);
            }
        }
        return list;
    }

    public static List<Object> getFragmentAnn(android.support.v4.app.Fragment fragment, Class annClazz) {
        Class clazz = fragment.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Object> list = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object annotation = field.getAnnotation(annClazz);
            if (annotation != null) {
                list.add(annotation);
            }
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            Object annotation = method.getAnnotation(annClazz);
            if (annotation != null) {
                list.add(annotation);
            }
        }
        return list;
    }

    /**
     * 给view赋值
     *
     * @param obj
     * @return
     */
    public static boolean attachField(Object obj) {
        android.support.v4.app.Fragment fragment = null;
        Activity activity = null;
        if (obj instanceof Activity) {
            activity = (Activity) obj;
        } else if (obj instanceof android.support.v4.app.Fragment) {
            fragment = (android.support.v4.app.Fragment) obj;
        } else {
            return false;
        }
        Field field = null;
        if (activity != null) {
            field = getField(activity, TopViewBind.class);
            TopViewBind topViewBind = getTopViewBind(activity);
            if (topViewBind == null) {
                return false;
            }
            int id = topViewBind.id();
            if (id != -1) {
                try {
                    field.set(activity, activity.findViewById(id));
                    return true;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        } else if (fragment != null) {
            field = getField(fragment, TopViewBind.class);
            TopViewBind topViewBind = getTopViewBind(fragment);
            View view = fragment.getView();
            int id = topViewBind.id();
            if (id != -1) {
                try {
                    field.set(fragment, view.findViewById(id));
                    return true;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return false;
        }
        return false;
    }


    /**
     * 通过注解获取字段
     * 注意：(只获取第一个使用该注解的字段)
     *
     * @param obj
     * @param annClazz
     * @return
     */
    public static Field getField(Object obj, Class annClazz) {
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object annotation = field.getAnnotation(annClazz);
            if (annotation != null) {
                return field;
            }
        }
        return null;
    }

    /**
     * 通过注解获取方法
     *
     * @param obj
     * @param annClazz
     * @return
     */
    public static List<Method> getMethod(Object obj, Class annClazz) {
        Class clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> tempMethod = new ArrayList<>();
        for (Method method : methods) {
            method.setAccessible(true);
            Object annotation = method.getAnnotation(annClazz);
            if (annotation != null) {
                tempMethod.add(method);
            }
        }
        return tempMethod;
    }

    /**
     * 调用点击方法
     *
     * @param obj
     * @param view
     */
    public static void onClickInvoke(Object obj, Class annClazz, View view) {
        List<Method> methods = getMethod(obj, annClazz);
        for (Method method : methods) {
            try {
                method.invoke(obj, view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用EditText事件方法
     *
     * @param obj
     */
    public static void onTextChangeInvoke(Object obj, EditTextEvent.TEXT_CHANGE textChange, Editable s, CharSequence str, int start, int count, int after) {
        List<Method> methods = getMethod(obj, EditTextEvent.class);
        for (Method method : methods) {
            EditTextEvent annotation = method.getAnnotation(EditTextEvent.class);
            if (textChange != annotation.textChange()) {
                continue;
            }
            switch (annotation.textChange()) {
                case BEFORE_TEXT_CHANGE:
                    try {
                        method.invoke(obj, str, start, count, after);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                case ON_TEXT_CHANGE:
                    try {
                        method.invoke(obj, str, start, count, after);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                case AFTER_TEXT_CHANGE:
                    try {
                        method.invoke(obj, s);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                case SEARCH_REQUEST:
                    try {
                        method.invoke(obj, s);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }


}
