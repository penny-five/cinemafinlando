package com.github.pennyfive.finnkino.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * For displaying event promo images in fixed aspect ratio.
 */
public class LandscapeEventImageView extends ImageView {
    /**
     * Promo images are always returned in this aspect ratio.
     */
    private static final float ASPECT_RATIO = 2.68f;

    public LandscapeEventImageView(Context context) {
        super(context);
    }

    public LandscapeEventImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LandscapeEventImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (width / ASPECT_RATIO), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
