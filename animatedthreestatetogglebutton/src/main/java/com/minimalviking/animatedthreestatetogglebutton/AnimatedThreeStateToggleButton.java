package com.minimalviking.animatedthreestatetogglebutton;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by Jacek Olszewski on 05/02/16.
 */
public class AnimatedThreeStateToggleButton extends TextView {
	private static final int DEFAULT_ANIM_LENGTH = 200;
	private int bgColor1;
	private int bgColor2;
	private int bgColor0;
	private int borderColor1;
	private int borderColor2;
	private int borderColor0;
	private String text1;
	private String text2;
	private String text0;
	private int state = 0;
	private int cornerRadius;
	private int textColor0;
	private int textColor1;
	private int textColor2;
	private int borderWidth;
	private int bgColorStart;
	private int bgColorFinish;
	private int borderColorStart;
	private int borderColorFinish;
	private int textColorStart;
	private int textColorFinish;
	ValueAnimator animator;

	public AnimatedThreeStateToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public AnimatedThreeStateToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public AnimatedThreeStateToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs);
	}

	private void init(AttributeSet attrs) {
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.atstb, 0, 0);

		bgColor0 = a.getColor(R.styleable.atstb_color_bg0, Color.TRANSPARENT);
		bgColor1 = a.getColor(R.styleable.atstb_color_bg1, Color.TRANSPARENT);
		bgColor2 = a.getColor(R.styleable.atstb_color_bg2, Color.TRANSPARENT);

		borderColor0 = a.getColor(R.styleable.atstb_color_border0, Color.TRANSPARENT);
		borderColor1 = a.getColor(R.styleable.atstb_color_border1, Color.TRANSPARENT);
		borderColor2 = a.getColor(R.styleable.atstb_color_border2, Color.TRANSPARENT);

		textColor0 = a.getColor(R.styleable.atstb_color_text0, Color.BLACK);
		textColor1 = a.getColor(R.styleable.atstb_color_text1, Color.BLACK);
		textColor2 = a.getColor(R.styleable.atstb_color_text2, Color.BLACK);

		text0 = a.getString(R.styleable.atstb_text0);
		text1 = a.getString(R.styleable.atstb_text1);
		text2 = a.getString(R.styleable.atstb_text2);

		cornerRadius = dpToPx(a.getInteger(R.styleable.atstb_corner_radius, 0), getContext());

		borderWidth = a.getInt(R.styleable.atstb_border_width, -1);
		if (borderWidth != -1) {
			borderPaint.setStyle(Paint.Style.STROKE);
			borderPaint.setStrokeWidth(dpToPx(borderWidth, getContext()));
		}
		setState(0);

		animator = ValueAnimator.ofFloat(0, 1);
		animator.setDuration(a.getInt(R.styleable.atstb_animation_length, DEFAULT_ANIM_LENGTH));
	}

	public void setState(int setTo) {
		state = setTo;

		switch (setTo) {
			case 0:
				setText(text0);
				bgPaint.setColor(bgColor0);
				borderPaint.setColor(borderColor0);
				textColor = textColor0;
				break;
			case 1:
				setText(text1);
				bgPaint.setColor(bgColor1);
				borderPaint.setColor(borderColor1);
				textColor = textColor1;
				break;
			case 2:
				setText(text2);
				bgPaint.setColor(bgColor2);
				borderPaint.setColor(borderColor2);
				textColor = textColor2;
				break;
		}
		invalidate();
	}

	public int getState() {
		return state;
	}

	public void animateTo(int setTo) {
		if (state == setTo) {
			return;
		}

		animator.cancel();
		animator.start();

		bgColorStart = bgPaint.getColor();
		borderColorStart = borderPaint.getColor();
		textColorStart = getCurrentTextColor();

		switch (setTo) {
			case 0:
				bgColorFinish = bgColor0;
				borderColorFinish = borderColor0;
				textColorFinish = textColor0;
				setText(text0);
				break;
			case 1:
				bgColorFinish = bgColor1;
				borderColorFinish = borderColor1;
				textColorFinish = textColor1;
				setText(text1);
				break;
			case 2:
				bgColorFinish = bgColor2;
				borderColorFinish = borderColor2;
				textColorFinish = textColor2;
				setText(text2);
				break;
		}

		state = setTo;

		invalidate();
	}

	Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	int textColor;

	ArgbEvaluator argbEvaluator = new ArgbEvaluator();

	@Override
	protected void onDraw(Canvas canvas) {
		if (animator.isRunning()) {
			float fraction = animator.getAnimatedFraction();
			invalidate();
			bgPaint.setColor((Integer) argbEvaluator.evaluate(fraction, bgColorStart, bgColorFinish));
			borderPaint.setColor((Integer) argbEvaluator.evaluate(fraction, borderColorStart, borderColorFinish));
			textColor = (int) argbEvaluator.evaluate(fraction, textColorStart, textColorFinish);
		}

		canvas.drawRoundRect(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), cornerRadius, cornerRadius, bgPaint);
		canvas.drawRoundRect(new RectF(0+borderWidth/2, 0 + borderWidth/2, canvas.getWidth()- borderWidth/2, canvas.getHeight() - borderWidth/2), cornerRadius, cornerRadius, borderPaint);
		setTextColor(textColor);

		super.onDraw(canvas);
	}

	private static int dpToPx(int dp, Context context) {
		float resultPix = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
		return (int) resultPix;
	}
}
