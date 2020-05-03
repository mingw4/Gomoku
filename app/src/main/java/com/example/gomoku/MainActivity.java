package com.example.gomoku;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IBoard {
	private ConstraintLayout clLayout;
	private ArrayList<Grid> lstGrids;
	private ArrayList<Piece> lstPieces;
	private int iSide;
	private int iPiece;

	private boolean bNowBlack;
	private boolean bEnded;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
	protected void onResume() {
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

				find(piece);
			}
			
		}
	};

	boolean gridAvailable(int col, int row) {
		for (Piece P : lstPieces) {
			if (P.getCol() == col && P.getRow() == row) {
				return false;
			}
		}

		return true;
	}
	void find(Piece P) {
		int nVMax = 1;
		int nHMax = 1;
		int nLMax = 1;
		int nRMax = 1;
		for (int i = 0; i < 2; i++) {
			int gainX;
			if (i % 2 == 0) {
				gainX = 1;
			} else {
				gainX = -1;
			}
			int pCol = P.getCol() + gainX;
			int pRow = P.getRow();
			while (pCol >= 0 && pCol < GLib.SIDE) {
				if (sameSideAt(P.getSide(), pCol, pRow)) {
					nHMax++;
					pCol += gainX;
					continue;
				}
				break;
			}
		}
		for (int i = 0; i < 2; i++) {
			int gainY;
			if (i % 2 == 0) {
				gainY = 1;
			} else {
				gainY = -1;
			}
			int pCol = P.getCol();
			int pRow = P.getRow() + gainY;
			while (pRow >= 0 && pRow < GLib.SIDE) {
				if (sameSideAt(P.getSide(), pCol, pRow)) {
					nVMax++;
					pRow += gainY;
					continue;
				}
				break;
			}
		}
		for (int i = 0; i < 2; i++) {
			int gainX;
			int gainY;
			if (i % 2 == 0) {
				gainX = 1;
				gainY = -1;
			} else {
				gainX = -1;
				gainY = 1;
			}
			int pCol = P.getCol() + gainX;
			int pRow = P.getRow() + gainY;
			while (pRow >= 0 && pRow < GLib.SIDE && pCol >= 0 && pCol < GLib.SIDE) {
				if (sameSideAt(P.getSide(), pCol, pRow)) {
					nLMax++;
					pCol += gainX;
					pRow += gainY;
					continue;
				}
				break;
			}
		}
		for (int i = 0; i < 2; i++) {
			int gainX;
			int gainY;
			if (i % 2 == 0) {
				gainX = 1;
				gainY = 1;
			} else {
				gainX = -1;
				gainY = -1;
			}
			int pCol = P.getCol() + gainX;
			int pRow = P.getRow() + gainY;
			while (pRow >= 0 && pRow < GLib.SIDE && pCol >= 0 && pCol < GLib.SIDE) {
				if (sameSideAt(P.getSide(), pCol, pRow)) {
					nRMax++;
					pCol += gainX;
					pRow += gainY;
					continue;
				}
				break;
			}
		}
		if (nHMax == 5 || nVMax == 5 || nLMax == 5 || nRMax == 5) {
			bEnded = true;
			if (P.getSide()) {
                Toast.makeText(getApplicationContext(),"Black Win!",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),"White Win!",Toast.LENGTH_SHORT).show();
            }

        }
	}

	boolean sameSideAt(boolean bBlack, int col, int row)
	{
		for (Piece P : lstPieces)
		{
			if (P.getSide() == bBlack && P.getCol() == col && P.getRow() == row)
				return true;
		}

		return false;
	}
	private void AddGrids() {
		lstGrids.clear();

		for (int i = 0; i < GLib.SIDE * 18; i++) {
			Grid grid = new Grid(this, i);
			grid.setOnClickListener(OnGrid);
			clLayout.addView(grid, iSide, iSide);

			lstGrids.add(grid);
		}
	}

	private void LocateGrids() {
		ConstraintSet set = new ConstraintSet();
		set.clone(clLayout);
		for (int i = 0; i < GLib.SIDE * 18; i++) {
			int X = lstGrids.get(i).getCol();
			int Y = lstGrids.get(i).getRow();

			if (Y == 0) {
				set.connect(lstGrids.get(i).getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
			} else {
				set.connect(lstGrids.get(i).getId(), ConstraintSet.TOP, lstGrids.get(i - GLib.SIDE).getId(), ConstraintSet.BOTTOM);
			}

			if (X == 0) {
				set.connect(lstGrids.get(i).getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
			} else {
				set.connect(lstGrids.get(i).getId(), ConstraintSet.LEFT, lstGrids.get(i - 1).getId(), ConstraintSet.RIGHT);
			}
		}

		set.applyTo(clLayout);
	}

	@Override
	public int getGridID(int col, int row) {
		return lstGrids.get(row * GLib.SIDE + col).getId();
	}

	@Override
	public ConstraintLayout getLayout() {
		return clLayout;
	}
}

