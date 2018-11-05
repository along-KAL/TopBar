package com.example.lenovo.topbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;

import com.example.lenovo.topbar.topbar.NormalBar;
import com.example.lenovo.topbar.topbar.SearchBar;
import com.example.lenovo.topbar.topbar.BarView;
import com.example.lenovo.topbar.topbar.annotation.AnnotationHandle;
import com.example.lenovo.topbar.topbar.annotation.EditTextEvent;
import com.example.lenovo.topbar.topbar.annotation.LeftClick;
import com.example.lenovo.topbar.topbar.annotation.RightClick;
import com.example.lenovo.topbar.topbar.annotation.SearchBarAttr;
import com.example.lenovo.topbar.topbar.annotation.TopViewBind;
import com.example.lenovo.topbar.topbar.menu.DefaultMenu;

import static com.example.lenovo.topbar.topbar.annotation.EditTextEvent.TEXT_CHANGE.AFTER_TEXT_CHANGE;
import static com.example.lenovo.topbar.topbar.annotation.EditTextEvent.TEXT_CHANGE.BEFORE_TEXT_CHANGE;
import static com.example.lenovo.topbar.topbar.annotation.EditTextEvent.TEXT_CHANGE.ON_TEXT_CHANGE;
import static com.example.lenovo.topbar.topbar.annotation.EditTextEvent.TEXT_CHANGE.SEARCH_REQUEST;

/**
 * @author along
 */
public class MainActivity extends AppCompatActivity {
    //@TopViewBind(id = R.id.tl_top_bar)
    //private BarView mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AnnotationHandle.attachField(this);
        //NormalBar barView = (NormalBar) mTopBar.getBarView();
        //barView.attachMenu(barView.mLeft, new DefaultMenu(this));
        //barView.attachMenu(barView.mRight, new DefaultMenu(this));
    }

    @LeftClick()
    public void onLeft(View v) {
        Log.d("event", "onLeft: " + v);
    }

    @RightClick()
    public void onRight(View v) {
        Log.d("event", "right1: " + v);
    }

    @EditTextEvent(textChange = BEFORE_TEXT_CHANGE)
    public void textCB(CharSequence s, int start, int count, int after) {
        //Log.d("event", "textCB: " + s);
    }

    @EditTextEvent(textChange = ON_TEXT_CHANGE)
    public void textOC(CharSequence s, int start, int before, int count) {
        //Log.d("event", "textOC: " + s);
    }

    @EditTextEvent(textChange = AFTER_TEXT_CHANGE)
    public void textAC(Editable s) {
        //Log.d("event", "textAC: " + s);
    }

    @EditTextEvent(textChange = SEARCH_REQUEST)
    public void textSearch(Editable s) {
        Log.d("event", "textAC: " + s);
    }
}
