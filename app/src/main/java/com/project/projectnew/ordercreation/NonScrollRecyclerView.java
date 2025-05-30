package com.project.projectnew.ordercreation;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;

public class NonScrollRecyclerView extends RecyclerView {
    public NonScrollRecyclerView(Context context) {
        super(context);
    }

    public NonScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandedHeightSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandedHeightSpec);
    }
}
