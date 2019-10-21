package ryde.InternetChess;

import ryde.gui.ChessBoard;

public class Knight extends Chess {
	
	public Knight(int y,int x,boolean isEnemy) {
		super(y,x,"knight",30,isEnemy);
	}
	
	@Override
	/**
	 * 判断点击的位置是否能前进
	 * @param b 点击的目标位置
	 * @param bs 全部棋盘
	 * @return
	 */
	public boolean isWalkable(ChessBoard click, ChessBoard[][] bs) {
		// 目标位置为空或者是敌人
		if (click.getChess() == null || click.getChess().isEnemy()!=isEnemy()) {
			int betweenX, betweenY;// 要前进的棋盘位置和当前位置的坐标差
			betweenX = Math.abs(this.getCoorX() - click.getCoorX());
			betweenY = Math.abs(this.getCoorY() - click.getCoorY());
			if (betweenX == 1 && betweenY == 2 || betweenX == 2 && betweenY == 1)
				return true;
		}	
		return false;
	}

}
