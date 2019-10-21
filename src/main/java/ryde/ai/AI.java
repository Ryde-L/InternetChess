package ryde.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import org.omg.CORBA.PUBLIC_MEMBER;

import ryde.InternetChess.Chess;
import ryde.battle.ChessInfo;
import ryde.gui.ChessBoard;
import ryde.gui.ChessBoardPanel;
import ryde.gui.MainPlayFrame;

public class AI implements Runnable{
	private List<List<Integer>> allAIChessWalkWayList=new ArrayList<List<Integer>>();
	private List<List<Integer>> allAIWalkWayValueList=new ArrayList<List<Integer>>();
	private List<List<Integer>> allPlayerChessWalkWayList=new ArrayList<List<Integer>>();
	public static HashMap<Integer, Integer> allPlayerChesswalkWayMap=new HashMap<Integer, Integer>();
	public  HashMap<Integer, Integer> allAIChesswalkWayMap=new HashMap<Integer, Integer>();
	
	
	
	/**
	 * 查找所有棋子的可落脚点,并将可行结果添加到对应List
	 */
	public void allSearch(){
		for (int i = 0; i < ChessInfo.AIChessList.size(); i++) {
//			System.out.println("AI方"+ChessInfo.AIChessList.get(i).getName()+"可移动方位数 :"+ChessInfo.AIChessList.get(i).searchWalkableWay(ChessBoardPanel.chessboard,allAIChesswalkWayMap));
			allAIChessWalkWayList.add(ChessInfo.AIChessList.get(i).searchWalkableWay(ChessBoardPanel.chessboard,allAIChesswalkWayMap));
		}
		for (int i = 0; i < ChessInfo.playerChessList.size(); i++) {
//			System.out.println("我方"+ChessInfo.playerChessList.get(i).getName()+"可移动方位数 :"+ChessInfo.playerChessList.get(i).searchWalkableWay(ChessBoardPanel.chessboard,allPlayerChesswalkWayMap));		
			allPlayerChessWalkWayList.add(ChessInfo.playerChessList.get(i).searchWalkableWay(ChessBoardPanel.chessboard,allPlayerChesswalkWayMap));
		}
	}
	
	/**
	 * 计算每个走法的得分
	 */
	public void countValue(){
		int highScore=0;
		//第i个棋子
		for (int i = 0; i < allAIChessWalkWayList.size(); i++) {
			List<Integer> walkWayList=allAIChessWalkWayList.get(i);
			List<Integer> eachWayValueList=new ArrayList<Integer>();
			//第i个棋子的第j种走法得分
			for (int j = 0; j < walkWayList.size(); j++) {
				if (ChessBoardPanel.chessboard[walkWayList.get(j)/10][walkWayList.get(j)%10].getChess()==null) {
					//前进得一分
					highScore++;
				}else {
					Chess chess=ChessBoardPanel.chessboard[walkWayList.get(j)/10][walkWayList.get(j)%10].getChess();
					//AI吃到棋子得棋子分
					highScore++;
					highScore+=chess.getValue();
					ChessBoardPanel.chessboard[walkWayList.get(j)/10][walkWayList.get(j)%10].setChess(null);
					//AI吃到的棋子处在玩家保护之中，减分(王除外)
					for (int k = 0; k < ChessInfo.playerChessList.size(); k++) {
						if (!chess.getName().equals("king")&&ChessInfo.playerChessList.get(k).isWalkable(ChessBoardPanel.chessboard[walkWayList.get(j)/10][walkWayList.get(j)%10], ChessBoardPanel.chessboard)) {
							highScore-=chess.getValue();
						}
					}
					ChessBoardPanel.chessboard[walkWayList.get(j)/10][walkWayList.get(j)%10].setChess(chess);
				
				}
				//当前所有AI棋子受到威胁的最大数值
				int maxValue=0;
				//AI棋子受威胁且不受保护
				for (int j2 = 0; j2 < ChessInfo.AIChessList.size(); j2++) {
					if (ChessInfo.AIChessList.get(j2).isUnderAttack()&&!allAIChesswalkWayMap.containsKey(walkWayList.get(j))&&j2!=i) {
						if (ChessInfo.AIChessList.get(j2).getValue()>maxValue) {
							maxValue=ChessInfo.AIChessList.get(j2).getValue();
						}
					
					}
				}
				//扣除最大威胁数值分
				highScore-=maxValue;
				//	
				if (allPlayerChesswalkWayMap.containsKey(walkWayList.get(j))) {
					highScore-=ChessInfo.AIChessList.get(i).getValue();
				}
				
				
				//AI棋子走这一步之后牌面所收到威胁减分
				
				
				
				//该棋子的每一步得分保存进list
				eachWayValueList.add(highScore);
				highScore=0;
			}
			//每个棋子的得分List保存进总List
			allAIWalkWayValueList.add(eachWayValueList);			
		}
		//test
		System.out.println("allAIWalkWayValueList: "+allAIWalkWayValueList);	}
	/**
	 * 根据allWalkWayValueList分析找出得分最高的走法
	 * @return 返回一个整数，其中前两位和后两位分别作为allChessWalkWayList的俩个下标
	 */
	public int findBestWay() {
		int firstIndex = 0;
		int secondIndex = 0;
		int bestValue = -9999;
		int maxValueOfChessUnderAttacked = 0;
		int mostWalkWayOfNotUnderAttcked=-1;
		boolean hasChessUnderAttacked = false;
		// 找出走法数值最大的数值
		for (int i = 0; i < allAIWalkWayValueList.size(); i++) {
			for (int j = 0; j < allAIWalkWayValueList.get(i).size(); j++) {
				if (allAIWalkWayValueList.get(i).get(j) > bestValue) {
					firstIndex = i;
					secondIndex = j;
					bestValue = allAIWalkWayValueList.get(i).get(j);
				}
				// test
				System.out.println(
						ChessInfo.AIChessList.get(i).getName() + " walkvalue :" + allAIWalkWayValueList.get(i).get(j));
			}
		}
		// 筛选相同最大走法数值，若受威胁的，选本身价值最大的，若都不受威胁,选走法最多的
		for (int i = 0; i < allAIWalkWayValueList.size(); i++) {
			for (int j = 0; j < allAIWalkWayValueList.get(i).size(); j++) {
				if (allAIWalkWayValueList.get(i).get(j) == bestValue) {
					if (ChessBoardPanel.chessboard[ChessInfo.AIChessList.get(i).getCoorY()][ChessInfo.AIChessList.get(i)
							.getCoorX()].getChess().isUnderAttack()) {
						hasChessUnderAttacked = true;
					}
					// 有无棋子处于威胁之下
					if (hasChessUnderAttacked) {
						//有，选出价值最大的棋子的坐标
						if (ChessBoardPanel.chessboard[ChessInfo.AIChessList.get(i).getCoorY()][ChessInfo.AIChessList
								.get(i).getCoorX()].getChess().isUnderAttack()
								&& ChessBoardPanel.chessboard[ChessInfo.AIChessList.get(i)
										.getCoorY()][ChessInfo.AIChessList.get(i).getCoorX()].getChess()
												.getValue() > maxValueOfChessUnderAttacked) {
							firstIndex = i;
							secondIndex = j;
							maxValueOfChessUnderAttacked = ChessBoardPanel.chessboard[ChessInfo.AIChessList.get(i)
									.getCoorY()][ChessInfo.AIChessList.get(i).getCoorX()].getChess().getValue();
							System.out.println(ChessBoardPanel.chessboard[ChessInfo.AIChessList.get(i)
									.getCoorY()][ChessInfo.AIChessList.get(i).getCoorX()].getChess().getName()
									+ " under attacked");
						}
					} else {
						//无，选出走法最多的棋子的坐标
						if (allAIWalkWayValueList.get(i).size()>mostWalkWayOfNotUnderAttcked
								&&ChessBoardPanel.chessboard[ChessInfo.AIChessList.get(i).getCoorY()][ChessInfo.AIChessList.get(i).getCoorX()].getChess().getValue()<998) {
							firstIndex = i;
							secondIndex = j;
							mostWalkWayOfNotUnderAttcked=allAIWalkWayValueList.get(i).size();
						}
					}

				}
			}

		}
		return firstIndex * 100 + secondIndex;
	}
	/**
	 * 前进
	 */
	public void moveToBestWay(){
		allSearch();
		countValue();
		int index1=findBestWay();
		int index2=allAIChessWalkWayList.get(index1/100).get(index1%100);
		
		ChessBoardPanel.moveChess(
				ChessBoardPanel.chessboard[ChessInfo.AIChessList.get(index1/100).getCoorY()][ChessInfo.AIChessList.get(index1/100).getCoorX()], 
				ChessBoardPanel.chessboard[index2/10][index2%10]);		
	}
	/**
	 * 清除标识
	 */
	public  void clearStatus(){
		allAIChessWalkWayList.clear();
		allAIWalkWayValueList.clear();
		allPlayerChesswalkWayMap.clear();
		allAIChesswalkWayMap.clear();
		for (int i = 0; i < ChessInfo.AIChessList.size(); i++) {
			ChessInfo.AIChessList.get(i).setUnderAttack(false);
		}
		for (int i = 0; i < ChessInfo.playerChessList.size(); i++) {
			ChessInfo.playerChessList.get(i).setUnderAttack(false);
		}
	}
	

	public void run() {
		try {
			while (true ) {
				if (!ChessBoardPanel.isPlayerTurn()) {
					ChessBoardPanel.setPlayerTurn(true);
					moveToBestWay();
					clearStatus();
					ChessBoardPanel.turnToMyTrun(true);
				}
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
//	 * 查找AI棋子落脚点
//	 * @param chessList AI棋子集合
//	 * @param chessBoards 棋盘
//	 * @return 返回以二位整数集合的集合作为AI棋子落脚点的坐标，x坐标为个位，y坐标为十位
//	 */
//	public List<List<Integer>> searchAIWalkWay(List<Chess> chessList,ChessBoard[][] chessBoards){
//		List<List<Integer>> walkWay=new ArrayList<List<Integer>>();
//		for (int i = 0; i < chessList.size(); i++) {
//			//walkWay.add(chessList.get(i).AIsearchWalkableWay(chessBoards));
//		}
//		return walkWay;
//	}
//	/**
//	 * 查找玩家棋子落脚点
//	 * @param chessList 玩家棋子集合
//	 * @param chessBoards 棋盘
//	 * @return 返回以二位整数集合作为玩家棋子落脚点的坐标，x坐标为个位，y坐标为十位
//	 */
//	public List<Integer> searchPlayerWalkWay(List<Chess> chessList,ChessBoard[][] chessBoards){
//		List<Integer> playerNextStepList=new ArrayList<Integer>();
//		for (int i = 0; i < chessList.size(); i++) {
//			List<Integer> list=chessList.get(i).playerChessSearchWalkableWay(chessBoards);
//			for (int j = 0; j < list.size(); j++) {
//				playerNextStepList.add(list.get(j));
//			}
//		}
//		return playerNextStepList;
//	}
//	
//	/**
//	 * 假设移动AI的棋子,返回移动后的两个棋子集合的集合
//	 */
//	public List<Chess> tryToMoveAIChess(ChessBoard presentChessBoard, ChessBoard clickBoard,List<Chess> playerChessList){
//		List<Chess> newPlayerChessList=new ArrayList<Chess>();
//		for (int i = 0; i < playerChessList.size(); i++) {
//			newPlayerChessList.add((Chess) playerChessList.get(i).clone());
//		}
//		
//		Chess chess=presentChessBoard.getChess();
//		chess.setCoor(clickBoard);//更新坐标
//		chess.setStep(chess.getStep()+1);
//		Icon icon=presentChessBoard.getIcon();
//		presentChessBoard.setChess(null);//棋子移动
//		presentChessBoard.setIcon(null);//去除图案
//		presentChessBoard = clickBoard;// 当前位置更新
//		presentChessBoard.setChess(chess);//棋子移动
//		presentChessBoard.setIcon(icon);//更新棋子图案
//		newPlayerChessList.remove(clickBoard);
//		return newPlayerChessList;
//		
//	}
//	/**
//	 * 还原备份棋盘
//	 */
//	public void restoreBackupBoard(ChessBoard presentChessBoard, ChessBoard clickBoard,List<Chess> playerChessList){
//		
//	}
//	/**
//	 * 
//	 * @param isFirstTime 是否首轮递归
//	 * @param n 	递归次数
//	 * @param highestScore 	每步得分
//	 * @param AIChessList	AI所有棋子List
//	 * @param playerChessList	玩家所有棋子List
//	 * @param chessBoards	棋盘
//	 */
//	public int countValues(boolean isFirstTime,int n,int highestScore,List<Chess> AIChessList,List<Chess> playerChessList,ChessBoard[][] chessBoards){
//		if (n==0) {
//			return 0;
//		}
//		List<List<Integer>> AIWalkWayList= searchAIWalkWay(AIChessList,chessBoards);
//		List<Integer> playerNextStepList=searchPlayerWalkWay(playerChessList,chessBoards);
//		
//		//复制副本
//		ChessBoard[][] boards=new ChessBoard[8][8];
//		for (int i = 0; i < 8; i++) {
//			for (int j = 0; j < 8; j++) {
//				boards[i][j]=(ChessBoard) chessBoards[i][j].clone();
//			}
//		}
//		
//		//AI第i个棋子
//		for (int i = 0; i < AIWalkWayList.size(); i++) {
//			List<Integer> walkWayList=AIWalkWayList.get(i);
//			List<Integer> eachWayValueList=new ArrayList<Integer>();
//			//AI第i个棋子的第j种走法得分
//			for (int j = 0; j < walkWayList.size(); j++) {
//				if (boards[walkWayList.get(j)/10][walkWayList.get(j)%10].getChess()==null) {
//					//前进得一分
//					highestScore++;
//				}else {
//					//AI吃到棋子得棋子分
//					highestScore++;
//					highestScore+=boards[walkWayList.get(j)/10][walkWayList.get(j)%10].getChess().getValue();
//				}
//				//当前所有AI棋子每收到威胁扣对应分
//				for (int j2 = 0; j2 < AIChessList.size(); j2++) {
//					if (AIChessList.get(j2).isUnderAttack()) {
//						highestScore-=AIChessList.get(j2).getValue();
//					}
//				}
//				//首轮递归当前棋子尝试前进，若不受威胁加分回来，其他轮不加，因为棋子在二轮递归是不能前进的，非AI回合
//				if (!playerNextStepList.contains(walkWayList.get(j))&&isFirstTime) {
//					highestScore+=AIChessList.get(i).getValue();
//				}
//				
//				//备份
//				Chess chess=AIChessList.get(i);
//				ChessBoard backupPresentBoard=(ChessBoard) boards[chess.getCoorY()][chess.getCoorX()].clone();
//				ChessBoard backupClickBoard=(ChessBoard) boards[walkWayList.get(j)/10][walkWayList.get(j)%10].clone();
//				//模拟前进
//				List<Chess> newPlayerChessList=tryToMoveAIChess(boards[chess.getCoorY()][chess.getCoorX()], boards[walkWayList.get(j)/10][walkWayList.get(j)%10],playerChessList);
//				//highestScore+=递归
//				
//				//还原备份
//				
//				//AI棋子走这一步之后牌面所收到威胁减分
//				
//				//该棋子的每一步得分保存进list
//				eachWayValueList.add(highestScore);
//				highestScore=0;
//			}
//			//每个棋子的得分List保存进总List
//			if (isFirstTime) {
//				allAIWalkWayValueList.add(eachWayValueList);
//				System.out.println("allAIWalkWayValueList: "+allAIWalkWayValueList);
//			}
//						
//		}
//		return highestScore;
//		
//		
//	}
}
