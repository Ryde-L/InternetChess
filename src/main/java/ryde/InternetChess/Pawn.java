package ryde.InternetChess;

import ryde.gui.ChessBoard;
import ryde.gui.ChessBoardPanel;
import ryde.socket.Client;

public class Pawn extends Chess {

	private int firstPlace;
	private int firstStep;
	public Pawn(int y,int x,boolean isEnemy){
		super(y,x,"pawn",10,isEnemy);
		setSpecialWalkWay(true);
		firstPlace=y;
	}
	
	@Override
	public boolean isWalkable(ChessBoard click, ChessBoard[][] bs) {
		// 目标位置为空或者是敌人
		if (click.getChess() == null || click.getChess().isEnemy() != isEnemy()) {
			int betweenX, betweenY;// 要前进的棋盘位置和当前位置的坐标差
			betweenX = getCoorX() - click.getCoorX();
			betweenY = getCoorY() - click.getCoorY();
			if (betweenX == 0&&click.getChess() == null) {
				switch (betweenY) {
				case 1:// ↑
					if (firstPlace == 6)
							return true;
					break;
				case 2:// ↑*2
					if (firstPlace == 6&&bs[getCoorY()-1][getCoorX()].getChess()==null&&getStep() == 0){
							firstStep = 2;
							return true;
						}
					break;
				case -1:// ↓
					if (firstPlace == 1)
							return true;
					break;
				case -2:// ↓*2
					if (firstPlace == 1&&bs[getCoorY()+1][getCoorX()].getChess()==null&&getStep() == 0){
							firstStep = 2;
							return true;
					}
					break;
				}
			} else if (click.getChess() == null) {// 斜对面无人不能走
				return false;
			} else {//斜
				if (firstPlace == 6 && Math.abs(betweenX) == 1 && betweenY == 1 && click.getChess().isEnemy() != isEnemy())
					return true;
				if (firstPlace == 1 && Math.abs(betweenX) == 1 && betweenY == -1 && click.getChess().isEnemy() != isEnemy())
					return true;
			}
		}
		return false;
	}
	//吃过路兵
	@Override
	public void specialWalkWay(ChessBoard presentChessBoard,ChessBoard click,ChessBoard[][] bs){
		if (click.getChess() == null || click.getChess().isEnemy()) {
			int betweenX, betweenY;// 要前进的棋盘位置和当前位置的坐标差
			betweenX = getCoorX() - click.getCoorX();
			betweenY = getCoorY() - click.getCoorY();
			int enemyPawnCooY=0;
			int enemyPawnCooX=click.getCoorX();
			if (betweenX == 1) {
				//斜左
				if(firstPlace==6&&betweenY==1||firstPlace==1&&betweenY==-1){
					enemyPawnCooY=getCoorY();
				}
			}else if (betweenX == -1) {
				//斜右
				if(firstPlace==6&&betweenY==1||firstPlace==1&&betweenY==-1){
					enemyPawnCooY=getCoorY();
				}
			}
			//吃过路兵
			if (bs[enemyPawnCooY][enemyPawnCooX].getChess()!=null&&
					bs[enemyPawnCooY][enemyPawnCooX].getChess().getName().equals("pawn")&&
					((Pawn) bs[enemyPawnCooY][enemyPawnCooX].getChess()).firstStep==2) {
				Client.sendKilledChessAway(bs[enemyPawnCooY][enemyPawnCooX]);
				ChessBoardPanel.moveChess(presentChessBoard,click);
				bs[enemyPawnCooY][enemyPawnCooX].setIcon(null);
				bs[enemyPawnCooY][enemyPawnCooX].setChess(null);
				ChessBoardPanel.turnToMyTrun(false);
			}
		}
	}
	public int getFirstStep() {
		return firstStep;
	}

	public void setFirstStep(int firstStep) {
		this.firstStep = firstStep;
	}

}

