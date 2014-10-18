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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.pennyfive.cinemafinlando.R;

/**
 * Base class for Activities that use {@link android.support.v4.widget.DrawerLayout}.
 */
public abstract class DrawerActivity extends ToolbarActivity implements DrawerListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        initializeDrawerToggle();
    }

    private void initializeDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                getToolbar(),
                R.string.drawer_open,
                R.string.drawer_close
        );

        drawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
        drawer.setDrawerListener(this);
    }

    protected void setContentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.content, fragment)
                .commit();
    }

    protected final void setDrawerFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_content, fragment).commit();
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
        invalidateOptionsMenu();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        drawerToggle.onDrawerClosed(drawerView);
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
