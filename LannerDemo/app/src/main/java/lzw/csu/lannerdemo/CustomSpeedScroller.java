package lzw.csu.lannerdemo;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Allen_Binan on 2016/4/6.
 */
public class CustomSpeedScroller extends Scroller {

    private int mDuration = 600;
    public CustomSpeedScroller(Context context) {
        super(context);
    }

    public CustomSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public CustomSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setmDuration(int time) {
        mDuration = time;
    }

    public int getmDuration() {
        return mDuration;
    }
}
