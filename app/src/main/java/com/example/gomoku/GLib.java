package com.example.gomoku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

class GLib
{
	final static int SIDE = 18;

	static int getSide(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		assert wm != null;
		Display display = wm.getDefaultDisplay();

		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);

		Point size = new Point();
		display.getSize(size);

		TypedArray typedArray = context.obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
		float titleHeight = typedArray.getDimension(0, 0);
		typedArray.recycle();

		return (int)(dm.heightPixels - titleHeight) / SIDE;
	}
}