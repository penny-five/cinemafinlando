/*
 * Copyright 2014 Joonas Lehtonen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pennyfive.cinemafinlando.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.ui.UiUtils;


/**
 * Layout that auto-collapses its child if it takes too much vertical space. Tapping the layout
 * expands/collapses the child.
 */
public class ExpandableLayout extends FrameLayout implements OnClickListener {
    private static final int STATE_UNMEASURED = 0;
    private static final int STATE_COLLAPSIBLE = 1;
    private static final int STATE_UNCOLLAPSIBLE = 2;

    // Could use custom attributes to set these.
    private static final int COLLAPSED_HEIGHT_IN_DIP = 100;
    private static final int COLLAPSE_THRESHOLD_IN_DIP = 150;

    private ImageView iconView;
    private View childView;

    private int state = STATE_UNMEASURED;
    private boolean isCollapsed = true;

    public ExpandableLayout(Context context) {
        super(context);
        initialize();
    }

    public ExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ExpandableLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        setForeground(getResources().getDrawable(R.drawable.list_selector));
        setOnClickListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (getChildCount() != 1) {
            throw new IllegalStateException("Invalid child count: " + getChildCount());
        } else {
            childView = getChildAt(0);
        }

        iconView = new ImageView(getContext());
        iconView.setImageResource(android.R.drawable.arrow_down_float);
        int gravity = Gravity.BOTTOM | Gravity.RIGHT;
        addView(iconView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, gravity));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (state == STATE_UNMEASURED) {
            measureIfCollapsible(widthMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void measureIfCollapsible(int widthMeasureSpec) {
        state = shouldCollapseChild(widthMeasureSpec) ? STATE_COLLAPSIBLE : STATE_UNCOLLAPSIBLE;
        if (state == STATE_COLLAPSIBLE) {
            collapse();
        } else {
            // If child shouldn't be collapsed let it reserve the space it needs and hide the arrow icon.
            childView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            iconView.setVisibility(View.GONE);
        }
        setClickable(state == STATE_COLLAPSIBLE);
    }

    private boolean shouldCollapseChild(int widthMeasureSpec) {
        childView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE, MeasureSpec.AT_MOST));
        return childView.getMeasuredHeight() > getCollapseThresholdInPx();
    }

    private int getCollapsedHeightInPx() {
        return UiUtils.pixelsFromDip(getContext(), COLLAPSED_HEIGHT_IN_DIP);
    }

    private int getCollapseThresholdInPx() {
        return UiUtils.pixelsFromDip(getContext(), COLLAPSE_THRESHOLD_IN_DIP);
    }

    @Override
    public void onClick(View v) {
        if (isCollapsed) {
            expand();
        } else {
            collapse();
        }
        requestLayout();
    }

    private void collapse() {
        childView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, getCollapsedHeightInPx()));
        iconView.setImageResource(android.R.drawable.arrow_down_float);
        isCollapsed = true;
    }

    private void expand() {
        childView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        iconView.setImageResource(android.R.drawable.arrow_up_float);
        isCollapsed = false;
    }
}
