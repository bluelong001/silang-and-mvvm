package me.study.silang.component;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MyLinearNoScrollLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled = true;

    public MyLinearNoScrollLayoutManager(Context context) {
        super(context);
    }

    public MyLinearNoScrollLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearNoScrollLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }


    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }

}
