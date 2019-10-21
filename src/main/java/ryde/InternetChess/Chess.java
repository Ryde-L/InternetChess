package ryde.InternetChess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ryde.ai.AI;
import ryde.gui.ChessBoard;

public class Chess implements Serializable,Cloneable{
	private boolean isUnderAttack=false;
	private int step=0;//步数
	private int value=10;//棋的价值
	private int coorX;
	private int coorY;
	private String name;
	private boolean isEnemy;
	private boolean hasSpecialWalkWay;

	public Chess() {

	}

	public Chess(int coorY, int coorX, String name,int value, boolean isEnemy) {
		this.coorX = coorX;
		this.coorY = coorY;
		this.name = name;
		this.isEnemy = isEnemy;
		this.value=value;
		hasSpecialWalkWay = false;
	}

	/**
	 * 
	 * @param click	 当前点击的棋盘位置
	 * @param bs	整个棋盘
	 * @return 	所选位置是否能前进
	 */
	public boolean isWalkable(ChessBoard click, ChessBoard[][] bs) {
		return false;
	}
	
	/**
	 * 返回该棋子可以前进的落脚点，并将在落脚点的对方棋子设为underAttaacked;以及将棋子攻击点保存进哈希表
	 * 
	 * @param bs 棋盘 
	 * @return 以二位数数字作为坐标的List
	 */
	public ArrayList<Integer> searchWalkableWay(ChessBoard[][] bs,Map<Integer, Integer> walkWayMap){
		ArrayList<Integer> walkWayList=new ArrayList<Integer>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (isWalkable(bs[i][j], bs)) {
					if (bs[i][j].getChess()!=null&&bs[i][j].getChess().isEnemy()!=this.isEnemy()) {
						bs[i][j].getChess().setUnderAttack(true);
					}
					walkWayList.add(i*10+j);
					if (getName().equals("pawn")) {
						//兵吃棋和走法不一样，特别添加
						walkWayMap.put(i*10+j+1, getCoorY()*10+getCoorX());
						walkWayMap.put(i*10+j-1, getCoorY()*10+getCoorX());
					}else {
						walkWayMap.put(i*10+j, getCoorY()*10+getCoorX());
					}
				}
			}
		}
		return walkWayList;
	}
	
	
	/**
	 * 棋子的特殊走法，如王车易位
	 * @param presentChessBoard 
	 * 当前已选位置
	 * @param click	
	 * 点击位置
	 * @param bs
	 * 整个棋盘
	 */
	public void specialWalkWay(ChessBoard presentChessBoard,ChessBoard click,ChessBoard[][] bs) {
	}

	@Override
	public Object clone(){
		Chess chess=null;
		try {
			chess=(Chess) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chess;
	}
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isEnemy() {
		return isEnemy;
	}

	public void setEnemy(boolean isEnemy) {
		this.isEnemy = isEnemy;
	}

	public boolean isUnderAttack() {
		return isUnderAttack;
	}

	public void setUnderAttack(boolean isUnderAttack) {
		this.isUnderAttack = isUnderAttack;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
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

	public void setCoor(ChessBoard b) {
		this.coorX = b.getCoorX();
		this.coorY = b.getCoorY();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHasSpecialWalkWay() {
		return hasSpecialWalkWay;
	}

	public void setSpecialWalkWay(boolean hasSpecialWalkWay) {
		this.hasSpecialWalkWay = hasSpecialWalkWay;
	}

	
	
//	
//	public List<Integer> playerChessSearchWalkableWay(ChessBoard[][] bs){
//		List<Integer> playerChessWalkWayList=new ArrayList<Integer>();
//		//仅允许玩家调用
//		if (!isEnemy()) {
//			for (int i = 0; i < 8; i++) {
//				for (int j = 0; j < 8; j++) {
//					if (isWalkable(bs[i][j], bs)) {
//						if (getName().equals("pawn")) {
//							//兵吃棋和走法不一样，特别添加
//							playerChessWalkWayList.add((i)*10+j+1, 1);
//							playerChessWalkWayList.add((i)*10+j-1, 1);
//						}else {
//							playerChessWalkWayList.add(i*10+j, 1);
//						}
//					}
//				}
//			}
//		}
//		
//		return playerChessWalkWayList;
//	}
}
