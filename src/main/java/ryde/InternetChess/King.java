package ryde.InternetChess;

import javax.swing.Icon;

import ryde.gui.ChessBoard;
import ryde.gui.ChessBoardPanel;

public class King extends Chess {
	

	public King(int y,int x,boolean isEnemy){
		super(y,x,"king",900,isEnemy);
		setSpecialWalkWay(true);
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
		if (click.getChess() == null || click.getChess().isEnemy() != isEnemy()) {
			int betweenX, betweenY;// 要前进的棋盘位置和当前位置的坐标差
			betweenX = this.getCoorX() - click.getCoorX();
			betweenY = this.getCoorY() - click.getCoorY();
			switch (Math.abs(betweenX)) {
			case 0:// ↑↓↖↗↙↘
				if (Math.abs(betweenY) == 1 || Math.abs(betweenY) == 0)
					return true;
				break;
			case 1:// ←→↖↗↙↘
				if (Math.abs(betweenY) == 1 || Math.abs(betweenY) == 0)
					return true;
				break;
			}
		}
		return false;
	}

	@Override
	/**
	 * 王车易位
	 */
	public void specialWalkWay(ChessBoard presentChessBoard,ChessBoard clickBoard,ChessBoard[][] bs){
		if(getStep()==0&&clickBoard.getCoorY()==getCoorY()){
			int rookCoorX=0;;
			if(clickBoard.getCoorX()>getCoorX())
				rookCoorX=7;
			if ((clickBoard.getCoorX()==getCoorX()-2||clickBoard.getCoorX()==getCoorX()+2)&&
					bs[getCoorY()][rookCoorX].getChess()!=null&&bs[getCoorY()][rookCoorX].getChess().getStep()==0) {
				System.out.println("in");
				for(int i=0;i<Math.abs(this.getCoorX()-rookCoorX);){
					if (getCoorX()>clickBoard.getCoorX()) 
						i=-i;
					if(Math.abs(i)<3){
						if (bs[getCoorY()][getCoorX()+i].isUnderAttack()) 
							return;
					}
					if(Math.abs(i)>0)
					if(bs[getCoorY()][getCoorX()+i].getChess()!=null)
						return;
					System.out.println(i);
					i=Math.abs(i)+1;
				}
				System.out.println("王车易位");
				//移动棋子
				ChessBoardPanel.moveChess(presentChessBoard,clickBoard);
				if (rookCoorX==0) 
					clickBoard=bs[getCoorY()][3];
				else
					clickBoard=bs[getCoorY()][5];
				presentChessBoard=bs[getCoorY()][rookCoorX];
				ChessBoardPanel.moveChess(presentChessBoard,clickBoard);
				ChessBoardPanel.turnToMyTrun(false);
			}
		}	
	}
}
