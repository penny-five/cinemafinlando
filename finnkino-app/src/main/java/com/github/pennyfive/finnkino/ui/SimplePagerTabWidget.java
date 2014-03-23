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

package com.github.pennyfive.finnkino.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.pennyfive.finnkino.R;

/**
 * Simple tab widget to be used with {@link android.support.v4.view.ViewPager}.
 */
public class SimplePagerTabWidget extends LinearLayout implements OnClickListener, OnPageChangeListener {
    private ViewPager pager;

    public SimplePagerTabWidget(Context context) {
        super(context);
    }

    public SimplePagerTabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimplePagerTabWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPager(ViewPager pager) {
        this.pager = pager;
        PagerAdapter adapter = pager.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            addTab(i);
        }
        setSelection(pager.getCurrentItem());
        pager.setOnPageChangeListener(this);
    }

    private void addTab(int position) {
        ImageView view = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.simple_tab_item, null);
        int resid = 0;
        // TODO remove hardcoded icon ids
        switch (position) {
            case 0:
                resid = R.drawable.ic_event_synopsis;
                break;
            case 1:
                resid = R.drawable.ic_event_showtimes;
                break;
            case 2:
                resid = R.drawable.ic_event_information;
                break;
            case 3:
                resid = R.drawable.ic_event_media;
                break;
        }

        view.setOnClickListener(this);
        view.setTag(getChildCount());

        view.setImageResource(resid);

        /* I should be less lazy and create icons that already have proper alpha and color */
        view.setAlpha(0.8f);
        view.setColorFilter(new PorterDuffColorFilter(Color.BLACK, Mode.SRC_IN));

        addView(view, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        setWeightSum(getChildCount());
    }

    @Override
    public final void onClick(View v) {
        int position = (int) v.getTag();
        setSelection(position);
        pager.setCurrentItem(position);
    }

    private void setSelection(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setSelected(position == i);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setSelection(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
