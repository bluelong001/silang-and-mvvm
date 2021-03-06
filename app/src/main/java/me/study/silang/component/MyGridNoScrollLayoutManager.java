package me.study.silang.component;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.GridLayoutManager;

public class MyGridNoScrollLayoutManager extends GridLayoutManager {

    private boolean isScrollEnabled = true;


    public MyGridNoScrollLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyGridNoScrollLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public MyGridNoScrollLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }


    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }


    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }

}
