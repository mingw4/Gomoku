package com.example.gomoku;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

@SuppressLint("ViewConstructor")
public class Piece extends AppCompatButton
{
	private int iX;
	private int iY;
	private boolean bBlack;

	private IBoard iBoard;

	public Piece(Context context, boolean bBlack, IBoard iBoard)
	{
		super(context);
		setId(generateViewId());

		this.bBlack = bBlack;
		this.iBoard = iBoard;

		setBackground(context.getDrawable(bBlack ? R.drawable.black : R.drawable.white));
	}

	public void setLocation(int x, int y)
	{
		iX = x;
		iY = y;

		int DestId = iBoard.getGridID(iX, iY);

		ConstraintLayout kLayout = iBoard.getLayout();

		ConstraintSet set = new ConstraintSet();
		set.clone(kLayout);

		set.connect(getId(), ConstraintSet.START, DestId, ConstraintSet.START);
		set.connect(getId(), ConstraintSet.END, DestId, ConstraintSet.END);

		set.connect(getId(), ConstraintSet.TOP, DestId, ConstraintSet.TOP);
		set.connect(getId(), ConstraintSet.BOTTOM, DestId, ConstraintSet.BOTTOM);

		set.applyTo(kLayout);
	}

	public int getCol()
	{
		return iX;
	}

	public int getRow()
	{
		return iY;
	}

	public boolean getSide()
	{
		return bBlack;
	}
}
