package ryde.InternetChess;

import ryde.gui.ChessBoard;

public class Queen extends Chess {
	
	public Queen(int y,int x,boolean isEnemy) {
		super(y,x,"queen",90,isEnemy);
	}
	
	@Override
	public boolean isWalkable(ChessBoard b, ChessBoard[][] bs) {
		// 目标位置为空或者是敌人
		if (b.getChess() == null || b.getChess().isEnemy()!=isEnemy()) {
			int betweenX, betweenY;// 要前进的棋盘位置和当前位置的坐标差
			betweenX = this.getCoorX() - b.getCoorX();
			betweenY = this.getCoorY() - b.getCoorY();
			if (Math.abs(betweenX) == Math.abs(betweenY)) {// ↖↗↙↘
				int j = 1;
				for (int i = 1; i < Math.abs(getCoorY() - b.getCoorY());) {
					if (getCoorY() > b.getCoorY())
						i = -i;
					if (getCoorX() > b.getCoorX())
						j = -j;
					if (bs[getCoorY() + i][getCoorX() + j].getChess() != null)
						return false;
					i = Math.abs(i) + 1;
					j = Math.abs(j) + 1;
				}
				return true;
			} else if (betweenX == 0) {// ←→
				for (int i = 1; i < Math.abs(getCoorY() - b.getCoorY());) {
					if (getCoorY() > b.getCoorY())
						i = -i;
					if (bs[getCoorY() + i][getCoorX()].getChess() != null)
						return false;
					i = Math.abs(i) + 1;
				}
				return true;
			} else if (betweenY == 0) {// ↑↓
				for (int i = 1; i < Math.abs(getCoorX() - b.getCoorX());) {
					if (getCoorX() > b.getCoorX())
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
