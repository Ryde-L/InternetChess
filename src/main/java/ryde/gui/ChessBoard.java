package ryde.gui;


import javax.swing.JButton;

import ryde.InternetChess.Chess;

public class ChessBoard extends JButton implements Cloneable{
	private boolean isUnderAttack=false;
	private int coorX,coorY;
	private Chess chess=null;
	public ChessBoard(){
		setContentAreaFilled(false);
	}
	
	public ChessBoard(int coorY,int coorX){
		super();
		this.coorX=coorX;
		this.coorY=coorY;
		setContentAreaFilled(false);
		setBorder(null);
		setBorderPainted(false);
	}

	@Override
	public Object clone(){
		ChessBoard chessBoard=null;
		try {
			
			chessBoard=(ChessBoard) super.clone();
			if (chessBoard.chess!=null) {
				chessBoard.chess=(Chess) chessBoard.chess.clone();
			}
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return chessBoard;
		
	}
	public Chess getChess() {
		return chess;
	}
	public void setChess(Chess chess) {
		this.chess = chess;
	}

	public int getCoorX() {
		return coorX;
	}

	public void setCoorX(int coorX) {
		this.coorX = coorX;
	}

	public int getCoorY() {
		return coorY;
	}

	public void setCoorY(int coorY) {
		this.coorY = coorY;
	}

	public boolean isUnderAttack() {
		return isUnderAttack;
	}

	public void setUnderAttack(boolean isUnderAttack) {
		this.isUnderAttack = isUnderAttack;
	}


}
