package com.dk.util;

import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Peter
 */
public class ToastUtil {
	private static TextView tv;
	private static WindowManager mWindowManager;
	private static int mBackGroundResourceId = -1;
	private static int mBackGroundColor = 0;

	private static Handler mUiHandler;
	private static Context mContext;
	private static boolean isShow = true;

	public static void setShow(boolean isShow) {
		ToastUtil.isShow = isShow;
	}

	public static void init(Application context) {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new RuntimeException("Cannot instantiate outside UI thread.");
		}

		mContext = context;
		mUiHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 101:

					showMessage((String) (msg.obj), Toast.LENGTH_SHORT);
					break;
				case 102:
					if (tv.getParent() != null)
						mWindowManager.removeView(tv);
					break;
				}

				super.handleMessage(msg);
			}

		};

	}

	public static void showMessage(final String msg) {
		if (msg == null || msg.trim().length() == 0) {
			return;
		}
		Message message = new Message();
		message.what = 101;
		message.obj = msg;
		mUiHandler.sendMessage(message);
	}

	public static void showMessage(final int msg) {
		Message message = new Message();
		message.what = 101;
		message.obj = mContext.getApplicationContext().getString(msg);
		mUiHandler.sendMessage(message);
	}

	// to do show icon

	private static void showMessage(String msg, int Length) {
		if (!isShow) {
			return;
		}
		mWindowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		if (tv == null) {
			tv = new TextView(mContext);

			tv.setTextSize(14);
			tv.setTextColor(0xff000000);
			if (mBackGroundResourceId > 0) {
				tv.setBackgroundResource(mBackGroundResourceId);

			}

			if (mBackGroundColor != 0) {
				tv.setBackgroundColor(mBackGroundColor);
			}
			tv.setPadding(5, 5, 5, 5);
			tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		}
		tv.setText(msg);

		if (tv.getParent() == null) {
			WindowManager.LayoutParams params = new WindowManager.LayoutParams();
			params.gravity = Gravity.BOTTOM;
			params.alpha = 0.85f;
			params.type = WindowManager.LayoutParams.TYPE_PHONE;
			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
			params.width = WindowManager.LayoutParams.WRAP_CONTENT;
			params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
					| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
					| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
			params.format = PixelFormat.TRANSLUCENT;
			params.verticalMargin = 0.2f;
			params.windowAnimations = 0;
			mWindowManager.addView(tv, params);
			mUiHandler.sendEmptyMessageDelayed(102, 2000);
		} else {
			mUiHandler.removeMessages(102);
			mUiHandler.sendEmptyMessageDelayed(102, 2000);
		}
	}

	public static void cancelCurrentToast() {
		if (tv != null && tv.getParent() != null) {
			mWindowManager.removeView(tv);
		}
	}

	public static void setBackGroundResourceId(int mBackGroundResourceId) {
		ToastUtil.mBackGroundResourceId = mBackGroundResourceId;
		ToastUtil.mBackGroundColor = 0;
	}

	public static void setBackGroundColor(int color) {
		ToastUtil.mBackGroundColor = color;
		ToastUtil.mBackGroundResourceId = -1;
	}

}
