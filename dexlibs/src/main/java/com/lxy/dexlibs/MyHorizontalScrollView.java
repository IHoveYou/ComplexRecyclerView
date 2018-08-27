package com.lxy.dexlibs;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by LXY on 2018/8/23.
 */

public class MyHorizontalScrollView extends HorizontalScrollView {
    private LinearLayout linearLayout;
    private View child;
    private int dowX;
    private int upX;
    private int btnWidth;
    private int start = 0;
    private int widthPixels;
    MoveClickListener moveClickListener;
    boolean isRollOut = false;

    public MyHorizontalScrollView(Context context) {
        super(context);
        widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
    }


    @Override
    public void addView(View child) {
        this.child = child;
        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(child);
//        linearLayout.setLayoutParams(this.getLayoutParams());
        super.addView(linearLayout);
    }

    public void addBtn(int btnLayout, OnClickListener onClickListener) {
        View view = LayoutInflater.from(getContext()).inflate(btnLayout, this, false);
        if (onClickListener != null) {
            view.setOnClickListener(onClickListener);
        }
        linearLayout.addView(view);
    }

    public void removeAllViews() {
        linearLayout.removeAllViews();
        linearLayout.addView(child);
    }

    private boolean isDown = false;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                if (!isDown) {
                    isDown = true;
                    start = (int) getScaleX();
                    dowX = (int) ev.getX();
                }

                if (moveClickListener != null && dowX > ev.getX()) {
                    moveClickListener.onMoveClickListener(MyHorizontalScrollView.this, (int) (dowX - ev.getX()));
                }
                break;
            case MotionEvent.ACTION_UP:
                isDown = false;
                upX = (int) ev.getX();
                btnWidth = linearLayout.getWidth() - widthPixels;
                int move = Math.abs(upX - dowX);
//                Log.e("move", "x" + move);
//                Log.e("move", "btnWidth" + btnWidth);
                if (move > btnWidth / 3) {
                    if (upX > dowX) {//往右移动
                        this.post(new Runnable() {
                            @Override
                            public void run() {
                                smoothScrollTo(0, 0);
                            }
                        });
                        if (moveClickListener != null) {
                            isRollOut = false;
                            moveClickListener.onUpClickListener(MyHorizontalScrollView.this, false);
                        }
                    } else {//left移动
                        this.post(new Runnable() {
                            @Override
                            public void run() {
                                smoothScrollTo(widthPixels, btnWidth);
                            }
                        });
                        if (moveClickListener != null) {
                            isRollOut = true;
                            moveClickListener.onUpClickListener(MyHorizontalScrollView.this, true);
                        }
                    }
                } else {
                    this.post(new Runnable() {
                        @Override
                        public void run() {
                            smoothScrollTo(isRollOut ? linearLayout.getWidth() : 0, 0);
                        }
                    });
                    if (moveClickListener != null)
                        moveClickListener.onUpClickListener(MyHorizontalScrollView.this, isRollOut);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void fling(int velocityX) {//滑动惯性设为0
        super.fling(0);

    }

    public void setMoveClickListener() {

    }

    public interface MoveClickListener {
        void onMoveClickListener(MyHorizontalScrollView myHorizontalScrollView, int moveX);

        void onUpClickListener(MyHorizontalScrollView myHorizontalScrollView, boolean isRollOut);
    }

    public MoveClickListener getMoveClickListener() {
        return moveClickListener;
    }

    public void setMoveClickListener(MoveClickListener moveClickListener) {
        this.moveClickListener = moveClickListener;
    }

    public boolean isRollOut() {
        return isRollOut;
    }

    public void setRollOut(boolean rollOut) {
        isRollOut = rollOut;
        MyHorizontalScrollView.this.smoothScrollTo(isRollOut ? linearLayout.getWidth() : 0, 0);
    }
}
