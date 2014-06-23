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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.R;

/**
 * Layout that auto-collapses its child (has to be instance of TextView) if it takes too much vertical space. Tapping the layout
 * switches between expanded/collapsed states.
 */
public class ExpandableTextLayout extends LinearLayout implements OnClickListener {
    private static final int STATE_UNMEASURED = 0;
    private static final int STATE_COLLAPSIBLE = 1;
    private static final int STATE_UNCOLLAPSIBLE = 2;

    // Could use custom XML attributes to set these.
    private static final int LINES_WHEN_COLLAPSED = 6;
    private static final int LINES_WHEN_SHOULD_COLLAPSE = 8;

    private ImageView iconView;
    private TextView textView;

    private int state = STATE_UNMEASURED;
    private boolean isCollapsed = true;

    public ExpandableTextLayout(Context context) {
        super(context);
        initialize();
    }

    public ExpandableTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ExpandableTextLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.list_selector);
        setOnClickListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!(getChildCount() == 1 && getChildAt(0) instanceof TextView)) {
            throw new IllegalStateException("Didn't set child properly");
        } else {
            textView = (TextView) getChildAt(0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (state == STATE_UNMEASURED) {
            measureIfCollapsible(widthMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void measureIfCollapsible(int widthMeasureSpec) {
        state = shouldCollapseTextView(widthMeasureSpec) ? STATE_COLLAPSIBLE : STATE_UNCOLLAPSIBLE;
        if (state == STATE_COLLAPSIBLE) {
            makeCollapsible();
        }
        setClickable(state == STATE_COLLAPSIBLE);
    }

    private void makeCollapsible() {
        iconView = new ImageView(getContext());
        iconView.setAlpha(0.5f); // Expand/collapse icons are currently too bright without reduced alpha.
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        addView(iconView, params);

        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), 0); // Remove bottom padding when icon view is shown.

        collapse();
    }

    private boolean shouldCollapseTextView(int widthMeasureSpec) {
        textView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE, MeasureSpec.AT_MOST));
        return textView.getLineCount() > LINES_WHEN_SHOULD_COLLAPSE;
    }

    @Override
    public void onClick(View v) {
        if (isCollapsed) {
            expand();
        } else {
            collapse();
        }
    }

    private void collapse() {
        textView.setMaxLines(LINES_WHEN_COLLAPSED);
        iconView.setImageResource(R.drawable.ic_expand_small);
        isCollapsed = true;
        requestLayout();
    }

    private void expand() {
        textView.setMaxLines(Integer.MAX_VALUE);
        iconView.setImageResource(R.drawable.ic_collapse_small);
        isCollapsed = false;
        requestLayout();
    }
}
