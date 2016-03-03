package com.thelionking.campus.syllabus;
//Download by http://www.codefans.net
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

public class SlidingLayout extends LinearLayout implements OnTouchListener {
	public static final int SNAP_VELOCITY =200;
	private int screenWidth;
	private int leftEdge;
	private int rightEdge = 0;
	private int leftLayoutPadding = 80;
	private float xDown;
	private float xMove;
	private float xUp;
	private boolean isLeftLayoutVisible;
	private View leftLayout;
	private View rightLayout;
	private View mBindView;
	private MarginLayoutParams leftLayoutParams;
	private MarginLayoutParams rightLayoutParams;
	private VelocityTracker mVelocityTracker;
	@SuppressWarnings("deprecation")
	public SlidingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
	}

	public void setScrollEvent(View bindView) {
		mBindView = bindView;
		mBindView.setOnTouchListener(this);
	}
	public void scrollToLeftLayout() {
		new ScrollTask().execute(30);
	}
	public void scrollToRightLayout() {
		new ScrollTask().execute(-30);
	}
	public boolean isLeftLayoutVisible() {
		return isLeftLayoutVisible;
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			// 获取左侧布局对象
			leftLayout = getChildAt(0);
			leftLayoutParams = (MarginLayoutParams) leftLayout.getLayoutParams();
			// 重置左侧布局对象的宽度为屏幕宽度减去leftLayoutPadding
			leftLayoutParams.width = screenWidth - leftLayoutPadding;
			// 设置最左边距为负的左侧布局的宽度
			leftEdge = -leftLayoutParams.width;
			leftLayoutParams.leftMargin = leftEdge;
			leftLayout.setLayoutParams(leftLayoutParams);
			// 获取右侧布局对象
			rightLayout = getChildAt(1);
			rightLayoutParams = (MarginLayoutParams) rightLayout.getLayoutParams();
			rightLayoutParams.width = screenWidth;
			rightLayout.setLayoutParams(rightLayoutParams);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		createVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 手指按下时，记录按下时的横坐标
			xDown = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			// 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整左侧布局的leftMargin值，从而显示和隐藏左侧布局
			xMove = event.getRawX();
			int distanceX = (int) (xMove - xDown);
			if (isLeftLayoutVisible) {
				leftLayoutParams.leftMargin = distanceX;
			} else {
				leftLayoutParams.leftMargin = leftEdge + distanceX;
			}
			if (leftLayoutParams.leftMargin < leftEdge) {
				leftLayoutParams.leftMargin = leftEdge;
			} else if (leftLayoutParams.leftMargin > rightEdge) {
				leftLayoutParams.leftMargin = rightEdge;
			}
			leftLayout.setLayoutParams(leftLayoutParams);
			break;
		case MotionEvent.ACTION_UP:
			// 手指抬起时，进行判断当前手势的意图，从而决定是滚动到左侧布局，还是滚动到右侧布局
			xUp = event.getRawX();
			if (wantToShowLeftLayout()) {
				if (shouldScrollToLeftLayout()) {
					scrollToLeftLayout();
				} else {
					scrollToRightLayout();
				}
			} else if (wantToShowRightLayout()) {
				if (shouldScrollToContent()) {
					scrollToRightLayout();
				} else {
					scrollToLeftLayout();
				}
			}
			recycleVelocityTracker();
			break;
		}
		return isBindBasicLayout();
	}
	private boolean wantToShowRightLayout() {
		return xUp - xDown < 0 && isLeftLayoutVisible;
	}
	private boolean wantToShowLeftLayout() {
		return xUp - xDown > 0 && !isLeftLayoutVisible;
	}
	private boolean shouldScrollToLeftLayout() {
		return xUp - xDown > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
	}

	private boolean shouldScrollToContent() {
		return xDown - xUp + leftLayoutPadding > screenWidth / 2
				|| getScrollVelocity() > SNAP_VELOCITY;
	}
	private boolean isBindBasicLayout() {
		if (mBindView == null) {
			return false;
		}
		String viewName = mBindView.getClass().getName();
		return viewName.equals(LinearLayout.class.getName())
				|| viewName.equals(RelativeLayout.class.getName())
				|| viewName.equals(FrameLayout.class.getName())
				|| viewName.equals(TableLayout.class.getName());
	}
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}
	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = leftLayoutParams.leftMargin;
			// 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
			while (true) {
				leftMargin = leftMargin + speed[0];
				if (leftMargin > rightEdge) {
					leftMargin = rightEdge;
					break;
				}
				if (leftMargin < leftEdge) {
					leftMargin = leftEdge;
					break;
				}
				publishProgress(leftMargin);
				// 为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。
				sleep(20);
			}
			if (speed[0] > 0) {
				isLeftLayoutVisible = true;
			} else {
				isLeftLayoutVisible = false;
			}
			return leftMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			leftLayoutParams.leftMargin = leftMargin[0];
			leftLayout.setLayoutParams(leftLayoutParams);
		}

		@Override
		protected void onPostExecute(Integer leftMargin) {
			leftLayoutParams.leftMargin = leftMargin;
			leftLayout.setLayoutParams(leftLayoutParams);
		}
	}
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
