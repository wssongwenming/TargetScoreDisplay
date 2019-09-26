package com.huasun.core.util.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * author:songwenming
 * Date:2018/12/1
 * Description:
 */
public class SwipeRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout
{
    public SwipeRefreshLayout(Context context)
    {
        super(context);
    }

    public SwipeRefreshLayout(Context context,AttributeSet attrs)
    {
        super(context,attrs);
    }

    @Override
    public boolean canChildScrollUp()
    {
        View target=getChildAt(0);
        if(target instanceof AbsListView)
        {
            final AbsListView absListView=(AbsListView)target;
            return absListView.getChildCount()>0
                    &&(absListView.getFirstVisiblePosition()>0||absListView.getChildAt(0)
                    .getTop()<absListView.getPaddingTop());
        }
        else
            return ViewCompat.canScrollVertically(target,-1);
    }
}

