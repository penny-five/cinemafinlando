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

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.MenuItem;
import android.view.View;

import com.github.pennyfive.finnkino.FinnkinoApplication.InjectUtils;
import com.github.pennyfive.finnkino.R;

import butterknife.InjectView;

public class EventListActivity extends Activity implements DrawerListener {
    @InjectView(R.id.drawer_layout) DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_showing);
        InjectUtils.inject(this);

        getFragmentManager().beginTransaction().replace(R.id.content, new EventListFragment()).commit();
        getFragmentManager().beginTransaction().replace(R.id.drawer_content, new NavigationFragment()).commit();

        initializeDrawerToggle();
    }

    private void initializeDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawer, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(this);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        drawerToggle.onDrawerSlide(drawerView, slideOffset);
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        drawerToggle.onDrawerOpened(drawerView);
        getActionBar().setTitle(R.string.app_name);
        invalidateOptionsMenu();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        drawerToggle.onDrawerClosed(drawerView);
        getActionBar().setTitle(R.string.app_name);
        invalidateOptionsMenu();
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        drawerToggle.onDrawerStateChanged(newState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
