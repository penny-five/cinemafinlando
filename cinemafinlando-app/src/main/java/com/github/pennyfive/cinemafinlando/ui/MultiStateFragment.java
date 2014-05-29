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
 * Base class for Fragments that have multiple states, each requiring its own UI, and should be able to switch between those states easily (e.g
 * . a Fragment that shows different UI when loading the data or when the loading failed). This base class handles the heavy lifting, including
 * transitions between different states.
 */
public abstract class MultiStateFragment extends Fragment {
    private FrameLayout rootLayout;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootLayout = new FrameLayout(getActivity());
        return rootLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onStateLayoutReady(savedInstanceState);
        if (rootLayout.getChildCount() == 0) {
            throw new IllegalStateException("Subclass didn't set state in #onStateLayoutReady");
        }
    }

    /**
     * Called when subclass should set the first state with {@link #switchView(android.view.View)}.
     *
     * @param savedInstanceState
     */
    protected abstract void onStateLayoutReady(Bundle savedInstanceState);

    protected final void switchView(View view) {
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
