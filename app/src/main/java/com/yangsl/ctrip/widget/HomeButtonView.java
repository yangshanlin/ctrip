package com.yangsl.ctrip.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yangsl.ctrip.R;

public class HomeButtonView extends FrameLayout {
	private ScaleAnimation animation_down;
	private ScaleAnimation animation_up;
	private boolean attri_doAnim;
	private TextView textview;
	private int state = 0;
	private HomeClickListener listener;

	public HomeButtonView(Context paramContext) {
		this(paramContext, null);
	}

	/**
	 * 加入响应事件
	 * 
	 * @param clickListener
	 */
	public void setOnHomeClick(HomeClickListener clickListener) {
		this.listener = clickListener;
	}

	public interface HomeClickListener {
		public void onclick(HomeButtonView homebtn);
	}

	public HomeButtonView(Context paramContext, AttributeSet paramAttributeSet) {
		this(paramContext, paramAttributeSet, 0);
	}

	public HomeButtonView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.home_button);
		if (localTypedArray != null) {
			this.attri_doAnim = localTypedArray.getBoolean(R.styleable.home_button_doAnim, true);
			localTypedArray.recycle();
		}

		loadAnim();
	}

	private void loadAnim() {
		ScaleAnimation localScaleAnimation1 = new ScaleAnimation(1.0F, 0.95F, 1.0F, 0.95F, 1, 0.5F, 1, 0.5F);
		localScaleAnimation1.setFillAfter(true);
		localScaleAnimation1.setDuration(200L);
		this.animation_down = localScaleAnimation1;
		ScaleAnimation localScaleAnimation2 = new ScaleAnimation(0.95F, 1.0F, 0.95F, 1.0F, 1, 0.5F, 1, 0.5F);
		localScaleAnimation2.setFillAfter(true);
		localScaleAnimation2.setDuration(200L);
		this.animation_up = localScaleAnimation2;
		this.animation_up.setAnimationListener(new AnimaListen(this));
	}

	private void start_AnimDown() {
		invalidate();
		startAnimation(this.animation_down);
	}

	private void start_AnimUp() {
		invalidate();
		startAnimation(this.animation_up);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float start = 1.0f;
		float end = 0.95f;
		Animation scaleAnimation = new ScaleAnimation(start, end, start, end, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		Animation endAnimation = new ScaleAnimation(end, start, end, start, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(200);
		scaleAnimation.setFillAfter(true);
		endAnimation.setDuration(200);
		endAnimation.setFillAfter(true);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.startAnimation(scaleAnimation);
			state = 1;
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			this.startAnimation(endAnimation);
			state = 0;
			invalidate();
			if (listener != null) {
				listener.onclick(this);
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			this.startAnimation(endAnimation);
			state = 0;
			invalidate();
			break;
		}
		return true;
	}

	class AnimaListen implements Animation.AnimationListener {

		AnimaListen(HomeButtonView paramHomeButtonView) {

		}

		public void onAnimationEnd(Animation arg0) {

		}

		public void onAnimationRepeat(Animation paramAnimation) {
		}

		public void onAnimationStart(Animation paramAnimation) {
		}

	}

}