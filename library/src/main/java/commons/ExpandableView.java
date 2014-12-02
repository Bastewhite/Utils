package commons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

public class ExpandableView extends LinearLayout {

    protected View mHandle;
    protected View mContent;

    private boolean mExpanded = false;
    private int mMinCollapsedHeight = 1;
    private int mContentHeight = 0;
    private long mAnimationDuration = 0;

    private OnExpandListener mListener;

    public ExpandableView(Context context) {
        this(context, null);
        setOrientation(VERTICAL);
    }

    public ExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);

        mListener = new OnExpandListenerAdapter();

        mAnimationDuration = 500;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mHandle = getChildAt(0);
        if (mHandle == null) {
            throw new IllegalArgumentException(
                    "The handle attribute is must refer to an existing child.");
        }

        if (mHandle instanceof OnExpandListener){
            setOnExpandListener((OnExpandListener) mHandle);
        }

        mContent = getChildAt(1);
        if (mContent == null) {
            throw new IllegalArgumentException(
                    "The content attribute must refer to an existing child.");
        }

        android.view.ViewGroup.LayoutParams lp = mContent.getLayoutParams();
        lp.height = mMinCollapsedHeight;
        mContent.setLayoutParams(lp);

        mHandle.setOnClickListener(new Toggler());
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Measure how high content wants to be
        mContent.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
        if (mExpanded && mContentHeight != mContent.getMeasuredHeight()){
            Animation a = new ExpandAnimation(mContentHeight, mContent.getMeasuredHeight());
            a.setDuration(mAnimationDuration);
            mContent.startAnimation(a);
        }

        mContentHeight = mContent.getMeasuredHeight();
        try {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    public void setOnExpandListener(OnExpandListener listener) {
        mListener = listener;
    }

    public void setAnimationDuration(int animationDuration) {
        mAnimationDuration = animationDuration;
    }

    public void setIsExpanded(boolean expanded){
        mExpanded = expanded;

        android.view.ViewGroup.LayoutParams lp = mContent.getLayoutParams();
        lp.height = mExpanded ? mContentHeight : mMinCollapsedHeight;
        mContent.setLayoutParams(lp);

        if (expanded){
            mListener.onExpand(mHandle, mContent);
        } else {
            mListener.onCollapse(mHandle, mContent);
        }
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public void toggle(){
        Animation a;
        if (mExpanded) {
            a = new ExpandAnimation(mContentHeight, mMinCollapsedHeight);
            mListener.onCollapse(mHandle, mContent);
        } else {
            a = new ExpandAnimation(mMinCollapsedHeight, mContentHeight);
            mListener.onExpand(mHandle, mContent);
        }
        a.setDuration(mAnimationDuration);
        mContent.startAnimation(a);
        mExpanded = !mExpanded;
    }

    private class Toggler implements OnClickListener {
        public void onClick(View v) {
            toggle();
        }
    }

    private class ExpandAnimation extends Animation {
        private final int mStartHeight;
        private final int mDeltaHeight;

        public ExpandAnimation(int startHeight, int endHeight) {
            mStartHeight = startHeight;
            mDeltaHeight = endHeight - startHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            android.view.ViewGroup.LayoutParams lp = mContent.getLayoutParams();
            lp.height = (int) (mStartHeight + mDeltaHeight * interpolatedTime);
            mContent.setLayoutParams(lp);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    public interface OnExpandListener {
        public void onExpand(View handle, View content);
        public void onCollapse(View handle, View content);
    }

    public class OnExpandListenerAdapter implements OnExpandListener {
        public void onCollapse(View handle, View content) {}
        public void onExpand(View handle, View content) {}
    }
}