package com.example.gomoku;

import androidx.constraintlayout.widget.ConstraintLayout;

public interface IBoard {
	int getGridID(int col, int row);
	ConstraintLayout getLayout();
}