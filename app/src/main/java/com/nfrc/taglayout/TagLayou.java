package com.nfrc.taglayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangl on 2018/10/24.
 */

public class TagLayou extends ViewGroup{


    private List<List<View>> mChildViews = new ArrayList<>();

    public TagLayou(Context context) {
        this(context,null);
    }

    public TagLayou(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TagLayou(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        mChildViews.clear();

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);


        int  childCount = getChildCount();


        //一行宽度
        int lineWidth = getPaddingLeft();


        ArrayList<View> childViews = new ArrayList<>();
        mChildViews.add(childViews);

        //高度不一致
        int maxHeight = 0;

        for (int i = 0;i < childCount;i++){

            //获取子View
            View childView = getChildAt(i);
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);

            //获取宽度
            if (lineWidth + childView.getMeasuredWidth() > width){
                height += childView.getMeasuredHeight();
                lineWidth = getPaddingLeft();

                childViews = new ArrayList<>();
                mChildViews.add(childViews);

            }else {
                childViews.add(childView);
                lineWidth += childView.getMeasuredWidth();
                maxHeight = Math.max(childView.getMeasuredHeight(),maxHeight);
            }

        }

        height += maxHeight;

        //根据子view计算
        setMeasuredDimension(width,height);
    }







    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int  childCount = getChildCount();
        int left,top = getPaddingTop(),right,bottom;
        for (List<View> childViews : mChildViews) {
            left = getPaddingLeft();
            for (View childView : childViews) {
                right = left+childView.getMeasuredWidth();
                bottom = top+childView.getMeasuredHeight();
                childView.layout(left,top,right,bottom);
                left += childView.getWidth();
            }
            top += childViews.get(0).getMeasuredHeight();
        }


    }







}
