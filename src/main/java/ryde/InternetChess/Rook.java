package ryde.InternetChess;

import java.util.ArrayList;

import ryde.gui.ChessBoard;

public class Rook extends Chess {
	public Rook(int y,int x,boolean isEnemy) {
		super(y,x,"rook",50,isEnemy);
	}
	
	@Override
	public boolean isWalkable(ChessBoard click, ChessBoard[][] bs) {
		// 目标位置为空或者是敌人
		if (click.getChess() == null || click.getChess().isEnemy() != isEnemy()) {
			int betweenX, betweenY;// 要前进的棋盘位置和当前位置的坐标差
			betweenX = this.getCoorX() - click.getCoorX();
			betweenY = this.getCoorY() - click.getCoorY();
			if (betweenX == 0) {// ←→
				for (int i = 1; i < Math.abs(getCoorY() - click.getCoorY());) {
					if (getCoorY() > click.getCoorY())
						i = -i;
					if (bs[getCoorY() + i][getCoorX()].getChess() != null)
						return false;
					i = Math.abs(i) + 1;
				}
				return true;
			} else if (betweenY == 0) {// ↑↓
				for (int i = 1; i < Math.abs(getCoorX() - click.getCoorX());) {
					if (getCoorX() > click.getCoorX())
						i = -i;
					if (bs[getCoorY()][getCoorX() + i].getChess() != null)
						return false;
					i = Math.abs(i) + 1;
				}
				return true;
			}
		}
		return false;
	}
}
