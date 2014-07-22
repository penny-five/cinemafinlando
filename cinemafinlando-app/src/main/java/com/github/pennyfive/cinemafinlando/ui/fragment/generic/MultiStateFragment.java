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
    private static final String BUNDLE_KEY_STATE = "state";
    private static final int STATE_NOT_SET = -1;
    protected static final int STATE_LOADING = 0;
    protected static final int STATE_CONTENT = 1;
    protected static final int STATE_ERROR = 2;

    private FrameLayout rootLayout;
    private int state = STATE_NOT_SET;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            state = savedInstanceState.getInt(BUNDLE_KEY_STATE, STATE_NOT_SET);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_KEY_STATE, state);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootLayout = new FrameLayout(getActivity());
        rootLayout.setId(android.R.id.content);
        if (state == STATE_NOT_SET) {
            setState(getStartupState());
        } else {
            changeState();
        }
        return rootLayout;
    }

    protected abstract View onCreateStateView(int state);

    /**
     * Called before creating the first state <i>unless</i> {@link #setState(int)} was already called by the subclass.
     *
     * @return
     */
    protected abstract int getStartupState();

    protected final void setState(int state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        if (rootLayout != null) {
            changeState();
        }
    }

    private void changeState() {
        View stateView = onCreateStateView(state);
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
        }
    }
}
