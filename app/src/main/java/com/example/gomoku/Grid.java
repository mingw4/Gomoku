package com.example.gomoku;

import android.content.Context;
import android.view.View;

public class Grid extends View
{
	private int iX;
	private int iY;

	public Grid(Context context, int index)
	{
		super(context);

		iX = index % 18;
		iY = index / 18;
		setId(generateViewId());

		if (iX == 0)
		{
			if (iY == 0)
			{
				setBackground(context.getDrawable(R.drawable.tl));
			}
			else if (iY == 17)
			{
				setBackground(context.getDrawable(R.drawable.bl));
			}
			else
			{
				setBackground(context.getDrawable(R.drawable.left));
			}
		}
		else if (iX == 17)
		{
			if (iY == 0)
			{
				setBackground(context.getDrawable(R.drawable.tr));
			}
			else if (iY == 17)
			{
				setBackground(context.getDrawable(R.drawable.br));
			}
			else
			{
				setBackground(context.getDrawable(R.drawable.right));
			}
		}
		else
		{
			if (iY == 0)
			{
				setBackground(context.getDrawable(R.drawable.top));
			}
			else if (iY == 17)
			{
				setBackground(context.getDrawable(R.drawable.bottom));
			}
			else
			{
				setBackground(context.getDrawable(R.drawable.center));
			}
		}
	}

	int getCol()
	{
		return iX;
	}

	int getRow()
	{
		return iY;
	}
}
