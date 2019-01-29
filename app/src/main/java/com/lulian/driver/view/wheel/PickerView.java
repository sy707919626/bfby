package com.lulian.driver.view.wheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lulian.driver.R;
import com.lulian.driver.entity.server.resulte.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class PickerView extends View
{

	public static final String TAG = "PickerView";
	public static final float MARGIN_ALPHA = 2.8f;
	public static final float SPEED = 2;

	private List<City> mDataList;
	private int mCurrentSelected;
	private Paint mPaint;

	private float mMaxTextSize = 50;
	private float mMinTextSize = 40;

	private float mMaxTextAlpha = 255;
	private float mMinTextAlpha = 120;

	private int mSelectTextColor = 0xFFF48B00;
	private int mUnSelectTextColor = 0x8B8B7A;
	private int mSelectTextSize = 50;
	private int mUnSelectTextSize = 40;
	
	private int mViewHeight;
	private int mViewWidth;

	private float mLastDownY;
	/**
	 * �����ľ���
	 */
	private float mMoveLen = 0;
	private boolean isInit = false;
	private onSelectListener mSelectListener;
	private Timer timer;
	private MyTimerTask mTask;

	Handler updateHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			if (Math.abs(mMoveLen) < SPEED)
			{
				mMoveLen = 0;
				if (mTask != null)
				{
					mTask.cancel();
					mTask = null;
					performSelect();
				}
			} else
				mMoveLen = mMoveLen - mMoveLen / Math.abs(mMoveLen) * SPEED;
			invalidate();
		}

	};

	public PickerView(Context context)
	{
		this(context,null);
	}

	public PickerView(Context context, AttributeSet attrs)
	{
		this(context, attrs,0);
	}
	
	public PickerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.PickerView);
		mSelectTextSize = (int) typeArray.getDimension(R.styleable.PickerView_select_text_size, mSelectTextSize);
		mUnSelectTextSize = (int) typeArray.getDimension(R.styleable.PickerView_unselect_text_size, mUnSelectTextSize);
		mSelectTextColor = typeArray.getColor(R.styleable.PickerView_select_text_color, mSelectTextColor);
		mUnSelectTextColor = typeArray.getColor(R.styleable.PickerView_unselect_text_color, mUnSelectTextColor);
		init();
	}

	public void setOnSelectListener(onSelectListener listener)
	{
		mSelectListener = listener;
	}

	private void performSelect()
	{
		if (mSelectListener != null)
			mSelectListener.onSelect(mDataList.get(mCurrentSelected).getName());
	}

	public void setData(List<City> datas)
	{
		mDataList = datas;
		mCurrentSelected = datas.size() / 2;
		invalidate();
	}

	public void setSelected(int selected)
	{
		mCurrentSelected = selected;
		int distance = mDataList.size() / 2 - mCurrentSelected;
		if (distance < 0)
			for (int i = 0; i < -distance; i++)
			{
				moveHeadToTail();
				mCurrentSelected--;
			}
		else if (distance > 0)
			for (int i = 0; i < distance; i++)
			{
				moveTailToHead();
				mCurrentSelected++;
			}
		invalidate();
	}

	public void setSelected(String mSelectItem)
	{
		for (int i = 0; i < mDataList.size(); i++)
			if (mDataList.get(i).equals(mSelectItem))
			{
				setSelected(i);
				break;
			}
	}

	private void moveHeadToTail()
	{
		String head = mDataList.get(0).getName();
		mDataList.remove(0);
		mDataList.add(new City(head));
	}

	private void moveTailToHead()
	{
		String tail = mDataList.get(mDataList.size() - 1).getName();
		mDataList.remove(mDataList.size() - 1);
		mDataList.add(0, new City(tail));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mViewHeight = getMeasuredHeight();
		mViewWidth = getMeasuredWidth();
		mMaxTextSize = mViewHeight / 4.0f;
		mMinTextSize = mMaxTextSize / 2f;
		isInit = true;
		invalidate();
	}

	private void init()
	{
		timer = new Timer();
		mDataList = new ArrayList<City>();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Style.FILL);
		mPaint.setTextAlign(Align.CENTER);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if (isInit)
			drawData(canvas);
	}

	private void drawData(Canvas canvas)
	{
		mPaint.setTextSize(mSelectTextSize);
		mPaint.setColor(mSelectTextColor);
		float x = (float) (mViewWidth / 2.0);
		float y = (float) (mViewHeight / 2.0 + mMoveLen);
		FontMetricsInt fmi = mPaint.getFontMetricsInt();
		float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));

		canvas.drawText(mDataList.get(mCurrentSelected).getName(), x, baseline, mPaint);
		for (int i = 1; (mCurrentSelected - i) >= 0; i++)
		{
			drawOtherText(canvas, i, -1);
		}
		for (int i = 1; (mCurrentSelected + i) < mDataList.size(); i++)
		{
			drawOtherText(canvas, i, 1);
		}
	}

	private void drawOtherText(Canvas canvas, int position, int type)
	{
		mPaint.setColor(mUnSelectTextColor);
		mPaint.setTextSize(mUnSelectTextSize);
		float d = (float) (MARGIN_ALPHA * mMinTextSize * position + type* mMoveLen);
		float scale = parabola(mViewHeight / 4.0f, d);
		mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
		float y = (float) (mViewHeight / 2.0 + type * d);
		FontMetricsInt fmi = mPaint.getFontMetricsInt();
		float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
		canvas.drawText(mDataList.get(mCurrentSelected + type * position).getName(),
				(float) (mViewWidth / 2.0), baseline, mPaint);
	}

	private float parabola(float zero, float x)
	{
		float f = (float) (1 - Math.pow(x / zero, 2));
		return f < 0 ? 0 : f;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
			doDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			doMove(event);
			break;
		case MotionEvent.ACTION_UP:
			doUp(event);
			break;
		}
		return true;
	}

	private void doDown(MotionEvent event)
	{
		if (mTask != null)
		{
			mTask.cancel();
			mTask = null;
		}
		mLastDownY = event.getY();
	}

	private void doMove(MotionEvent event)
	{

		mMoveLen += (event.getY() - mLastDownY);

		if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2)
		{
			moveTailToHead();
			mMoveLen = mMoveLen - MARGIN_ALPHA * mMinTextSize;
		} else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2)
		{
			moveHeadToTail();
			mMoveLen = mMoveLen + MARGIN_ALPHA * mMinTextSize;
		}

		mLastDownY = event.getY();
		invalidate();
	}

	private void doUp(MotionEvent event)
	{
		if (Math.abs(mMoveLen) < 0.0001)
		{
			mMoveLen = 0;
			return;
		}
		if (mTask != null)
		{
			mTask.cancel();
			mTask = null;
		}
		mTask = new MyTimerTask(updateHandler);
		timer.schedule(mTask, 0, 10);
	}

	class MyTimerTask extends TimerTask
	{
		Handler handler;

		public MyTimerTask(Handler handler)
		{
			this.handler = handler;
		}

		@Override
		public void run()
		{
			handler.sendMessage(handler.obtainMessage());
		}

	}

	public interface onSelectListener
	{
		void onSelect(String text);
	}
}
