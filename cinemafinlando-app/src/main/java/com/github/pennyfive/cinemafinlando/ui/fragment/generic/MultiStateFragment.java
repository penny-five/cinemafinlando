package com.github.pennyfive.cinemafinlando.ui.fragment.generic;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.pennyfive.cinemafinlando.R;

/**
 * Base class for Fragments that have multiple states, each requiring its own UI, and should be able to switch between those states easily (e.g
 * . a Fragment that shows different UI when loading the data or when the loading failed). This base class handles the heavy lifting, including
 * transitions between different states.
 */
public abstract class MultiStateFragment extends Fragment {
    protected static final int STATE_LOADING = 0;
    protected static final int STATE_CONTENT = 1;
    protected static final int STATE_ERROR = 2;

    private FrameLayout rootLayout;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootLayout = new FrameLayout(getActivity());
        return rootLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setState(getStartupState());
    }

    protected abstract View createStateView(int state);

    protected abstract int getStartupState();

    protected final void setState(int state) {
        View stateView = createStateView(state);
        replaceStateView(stateView);
        onStateChanged(state, stateView);
    }

    protected void onStateChanged(int state, View view) {

    }

    private void replaceStateView(View view) {
        if (rootLayout.getChildCount() > 0) {
            final View old = rootLayout.getChildAt(rootLayout.getChildCount() - 1);
            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.state_in);
            rootLayout.addView(view);
            set.setTarget(view);
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.removeView(old);
                }
            });
            set.start();
        } else {
            rootLayout.addView(view);
            AnimatorSet set = new AnimatorSet();
            set.start();
        }
    }
}
