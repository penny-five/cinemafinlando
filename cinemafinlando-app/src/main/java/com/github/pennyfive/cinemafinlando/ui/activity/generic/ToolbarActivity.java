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

package com.github.pennyfive.cinemafinlando.ui.activity.generic;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.ui.UiUtils;

/**
 *
 */
public class ToolbarActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(toolbar);

        frame = (FrameLayout) findViewById(R.id.toolbar_activity_frame);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, frame, false);
        setFrameView(view, null);
    }

    @Override
    public void setContentView(View view) {
        setFrameView(view, null);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        setFrameView(view, params);
    }

    private void setFrameView(View view, LayoutParams params) {
        frame.removeAllViews();
        if (params == null) {
            params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        frame.addView(view, params);
    }

    public void setToolbarOverlay(boolean isOverlay) {
        int topPadding = isOverlay ? 0 : UiUtils.pixelsFromDip(this, R.dimen.abc_action_bar_default_height_material);
        frame.setPadding(0, topPadding, 0, 0);
    }
}
