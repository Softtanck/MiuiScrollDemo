package com.softtanck.miuiscrolldemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

/**
 * @Descrption : TODO:
 * @CreateDate :【MiuiScrollDemo】 - 2015/7/7.
 * @Author : Tanck.
 * @Version : 1.0
 */
public class MyScrollView extends ScrollView {

    private View[] views;

    private int[] sizes;
    private int newHeight;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("Tanck", "onMeasure");
        sizes = new int[3];

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d("Tanck", "onLayout");
        if (0 < ((LinearLayout) getChildAt(0)).getChildCount()) {
            views = new View[((LinearLayout) getChildAt(0)).getChildCount()];
            for (int i = 0; i < ((LinearLayout) getChildAt(0)).getChildCount(); i++) {
                views[i] = ((LinearLayout) getChildAt(0)).getChildAt(i);
            }
        }
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        resizeScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    private void resizeScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (isTouchEvent) {
            if (0 > deltaY && newHeight < 300) {//下拉
                RefreshChildView(deltaY);
            }
        }
    }

    /**
     * 刷新视图
     *
     * @param deltaY
     */
    private void RefreshChildView(int deltaY) {

        for (int i = 0; i < views.length; i++) {
            int resize = 1 + i * 5;
//            float resize = 3.0f - (float) (views.length - i) * 0.2f;
            Log.d("Tanck", "deltaY:" + resize);
            int reheight = ((TextView) views[i]).getLayoutParams().height;
            newHeight = reheight + resize;
            ((TextView) views[i]).getLayoutParams().height = newHeight;
            ((TextView) views[i]).requestLayout();
//            ViewHelper.setScaleX(views[i], resize);
//            ViewHelper.setScaleY(views[i], resize);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (newHeight >= 0) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_UP:
                    for (int i = 0; i < views.length; i++) {
                        ((TextView) views[i]).getLayoutParams().height = 100;
                        ((TextView) views[i]).requestLayout();
                    }

                    newHeight = 0;
                    break;
            }
        }

        return super.onTouchEvent(ev);
    }
}
