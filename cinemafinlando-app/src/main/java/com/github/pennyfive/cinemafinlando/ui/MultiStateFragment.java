package com.github.pennyfive.cinemafinlando.ui;

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
 * Base class for Fragments that have multiple states, each requiring its own UI, and should be able to switch between those states easily (e.g .
 * a Fragment that shows different UI when loading the data or when the loading failed. This base class handles the heavy lifting, including
 * transitions between different states).
 */
public abstract class MultiStateFragment extends Fragment {
    private FrameLayout rootLayout;
    private int currentState;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootLayout = new FrameLayout(getActivity());
        return rootLayout;
    }

    @Override
    public final void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onStateLayoutReady(savedInstanceState);
        if (rootLayout.getChildCount() == 0) {
            throw new IllegalStateException("Subclass didn't set state in #onStateLayoutReady");
        }
    }

    /**
     * Called when subclass should set the first state with {@link #switchToState(android.view.View, Object)}.
     *
     * @param savedInstanceState
     */
    protected abstract void onStateLayoutReady(Bundle savedInstanceState);

    protected final void switchToState(View stateView, Object tag) {
        stateView.setTag(tag);
        if (rootLayout.getChildCount() > 0) {
            final View old = rootLayout.getChildAt(0);
            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.state_in);
            rootLayout.addView(stateView);
            set.setTarget(stateView);
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.removeView(old);
                    onStateViewDestroyed(old, old.getTag());
                }
            });
            onAnimateStateChange(old, old.getTag(), stateView, tag, set);
            set.start();
        } else {
            rootLayout.addView(stateView);
        }
    }

    protected void onAnimateStateChange(View oldView, Object oldTag, View newView, Object newTag, AnimatorSet set) {

    }

    protected void onStateViewDestroyed(View view, Object tag) {
        // default implementation does nothing
    }
}
