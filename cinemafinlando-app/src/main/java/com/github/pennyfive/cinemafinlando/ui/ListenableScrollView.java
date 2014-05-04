package com.github.pennyfive.cinemafinlando.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * {@link ScrollView} that supports adding listener for scroll events.
 */
public class ListenableScrollView extends ScrollView {

    public interface OnScrollListener {
        void onScroll(int position);
    }

    private OnScrollListener listener;

    public ListenableScrollView(Context context) {
        super(context);
    }

    public ListenableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListenableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.onScroll(t);
        }
    }
}
