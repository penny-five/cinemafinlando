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

package com.github.pennyfive.cinemafinlando.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.ui.view.CustomTypefaceTextView.CustomTypeface;

/**
 * Base class for Activities that use {@link android.support.v4.widget.DrawerLayout}.
 */
public abstract class DrawerActivity extends FragmentActivity implements DrawerListener {
    private static final String BUNDLE_ACTION_BAR_TITLE = "ab title";

    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;

    private String actionBarTitle;
    private int navigationMode = ActionBar.NAVIGATION_MODE_STANDARD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        initializeDrawerToggle();
        if (savedInstanceState != null) {
            setActionBarTitle(savedInstanceState.getString(BUNDLE_ACTION_BAR_TITLE));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_ACTION_BAR_TITLE, actionBarTitle);
    }

    private void initializeDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                R.drawable.cinemafinlando_ic_navigation_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        );
        drawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
        drawer.setDrawerListener(this);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    protected void setContentFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    protected final void setDrawerFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_content, fragment).commit();
    }

    protected final void setActionBarTitle(String title) {
        actionBarTitle = title;
        if (drawer == null || !drawer.isDrawerOpen(Gravity.START)) {
            updateActionBarTitle(title);
        }
    }

    protected final void setActionBarNavigationMode(int mode) {
        navigationMode = mode;
        if (drawer == null || !drawer.isDrawerOpen(Gravity.START)) {
            getActionBar().setNavigationMode(mode);
        }
    }

    private void updateActionBarTitle(String title) {
        getActionBar().setTitle(CustomTypeface.ROBOTO_LIGHT.wrap(this, title));
    }

    protected void updateNavigationMode(int mode) {
        getActionBar().setNavigationMode(mode);
        getActionBar().setDisplayShowTitleEnabled(mode == ActionBar.NAVIGATION_MODE_STANDARD);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
        updateNavigationMode(navigationMode);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        drawerToggle.onDrawerSlide(drawerView, slideOffset);
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        drawerToggle.onDrawerOpened(drawerView);
        updateNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        updateActionBarTitle(getString(R.string.app_name));
        invalidateOptionsMenu();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        drawerToggle.onDrawerClosed(drawerView);
        updateNavigationMode(navigationMode);
        updateActionBarTitle(actionBarTitle);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (drawer.isDrawerOpen(Gravity.START)) {
            // Hide contextual menu items when drawer is open, as advised in Design Guidelines.
            hideMenuItems(menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private static void hideMenuItems(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
    }

    protected void closeDrawer() {
        drawer.closeDrawer(Gravity.START);
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
