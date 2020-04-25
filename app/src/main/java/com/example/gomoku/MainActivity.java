package com.example.gomoku;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IBoard
{
	private ConstraintLayout clLayout;
	private ArrayList<Grid> lstGrids;
	private ArrayList<Piece> lstPieces;
	private int iSide;
	private int iPiece;

	private boolean bNowBlack;
	private boolean bEnded;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		clLayout = findViewById(R.id.constraintLayout);
		lstGrids = new ArrayList<>();
		lstPieces = new ArrayList<>();

		iSide = GLib.getSide(this);
		iPiece = iSide * 2 / 3;

		bNowBlack = true;
		bEnded = false;
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		AddGrids();
		LocateGrids();
	}

	private View.OnClickListener OnGrid = new View.OnClickListener()
	{
		@Override
		public void onClick(View view)
		{
			Grid grid = (Grid) view;

			if (gridAvailable(grid.getCol(), grid.getRow()) && !bEnded)
			{
				Piece piece = new Piece(getBaseContext(), bNowBlack, MainActivity.this);
				clLayout.addView(piece, iPiece, iPiece);

				lstPieces.add(piece);
				piece.setLocation(grid.getCol(), grid.getRow());

				bNowBlack = !bNowBlack;
			}
		}
	};

	boolean gridAvailable(int col, int row)
	{
		for (Piece P : lstPieces)
		{
			if (P.getCol() == col && P.getRow() == row)
				return false;
		}

		return true;
	}

	private void AddGrids()
	{
		lstGrids.clear();

		for (int i = 0; i < GLib.SIDE * 18; i++)
		{
			Grid grid = new Grid(this, i);
			grid.setOnClickListener(OnGrid);
			clLayout.addView(grid, iSide, iSide);

			lstGrids.add(grid);
		}
	}

	private void LocateGrids()
	{
		ConstraintSet set = new ConstraintSet();
		set.clone(clLayout);

		for (int i = 0; i < GLib.SIDE * 18; i++)
		{
			int X = lstGrids.get(i).getCol();
			int Y = lstGrids.get(i).getRow();

			if (Y == 0)
			{
				set.connect(lstGrids.get(i).getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
			}
			else
			{
				set.connect(lstGrids.get(i).getId(), ConstraintSet.TOP, lstGrids.get(i - GLib.SIDE).getId(), ConstraintSet.BOTTOM);
			}

			if (X == 0)
			{
				set.connect(lstGrids.get(i).getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
			}
			else
			{
				set.connect(lstGrids.get(i).getId(), ConstraintSet.LEFT, lstGrids.get(i - 1).getId(), ConstraintSet.RIGHT);
			}
		}

		set.applyTo(clLayout);
	}

	@Override
	public int getGridID(int col, int row)
	{
		return lstGrids.get(row * GLib.SIDE + col).getId();
	}

	@Override
	public ConstraintLayout getLayout()
	{
		return clLayout;
	}
}