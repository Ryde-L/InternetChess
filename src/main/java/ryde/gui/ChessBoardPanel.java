package ryde.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ryde.InternetChess.Bishop;
import ryde.InternetChess.Chess;
import ryde.InternetChess.King;
import ryde.InternetChess.Knight;
import ryde.InternetChess.Pawn;
import ryde.InternetChess.Queen;
import ryde.InternetChess.Rook;
import ryde.battle.ChessInfo;

/**
 * 8*8棋盘按钮
 * @author Ryde_L
 *
 */
public class ChessBoardPanel extends JPanel implements ActionListener{
	private ImageIcon myPawnIcon,myKingIcon,myRookIcon,myBishopIcon,myQueenIcon,myKnigthIcon;
	private ImageIcon p2PawnIcon,p2KingIcon,p2RookIcon,p2BishopIcon,p2QueenIcon,p2KnigthIcon;
	private ChessBoard presentChessBoard=null;
	private JLabel chessboardLabel;
	static JTextArea coverPanel;
	private ChessInfo chessInfo=null;
	private static Chess myKing;
	private static Chess enemyKing;
	public static boolean isSinglePlayer,isPlayerTurn;
	public static  ChessBoard [][]  chessboard;
	public ChessBoardPanel(boolean isEnemy,boolean isSinglePlayer){
		this.isSinglePlayer=isSinglePlayer;
		isPlayerTurn=!isEnemy;
//		if (isSinglePlayer) {
//			ChessInfo chessInfo=new ChessInfo();
//		}
		setLayout(null);
		//棋盘按钮 
		chessboard=new ChessBoard[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {				
				chessboard[i][j] = new ChessBoard(i,j);
				chessboard[i][j].setBounds(j * 53+40, i * 53+35, 54, 54);
				chessboard[i][j].addActionListener(this);
				add(chessboard[i][j]);				
			}
		}		
		presentChessBoard=chessboard[7][4];
		initAllChesses(isEnemy);
		chessboardLabel=new JLabel(new ImageIcon("images/board3.jpg"));
		chessboardLabel.setBounds(0, 0, 500, 500);
		add(chessboardLabel);
		coverPanel=new JTextArea();
		coverPanel.setBounds(0, 0, 510, 510);
		coverPanel.setOpaque(false);
		coverPanel.setEditable(false);
		coverPanel.setVisible(true);
		add(coverPanel);
		setComponentZOrder(coverPanel, 0);//设置遮罩在最上0
	}


	public void initAllChesses(boolean isEnemy){
		myPawnIcon=new ImageIcon("images/pawn.png");
		myRookIcon=new ImageIcon("images/rook.png");
		myKnigthIcon=new ImageIcon("images/knight.png");
		myBishopIcon=new ImageIcon("images/bishop.png");
		myQueenIcon=new ImageIcon("images/queen.png");
		myKingIcon=new ImageIcon("images/king.png");
	
		p2PawnIcon=new ImageIcon("images/p2pawn.png");
		p2RookIcon=new ImageIcon("images/p2rook.png");
		p2KnigthIcon=new ImageIcon("images/p2knight.png");
		p2BishopIcon=new ImageIcon("images/p2bishop.png");
		p2QueenIcon=new ImageIcon("images/p2queen.png");
		p2KingIcon=new ImageIcon("images/p2king.png");
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){				
				if(i==1){
					Pawn pawn=new Pawn( i,j,!isEnemy);
					chessboard[i][j].setIcon(p2PawnIcon);
					chessboard[i][j].setChess(pawn);
					if (isSinglePlayer) 
						ChessInfo.AIChessListAdd(pawn);
					
				} 
				if (i==6) {
					Pawn pawn=new Pawn( i,j,isEnemy);
					chessboard[i][j].setIcon(myPawnIcon);
					chessboard[i][j].setChess(pawn);
					if (isSinglePlayer) 
						ChessInfo.playerChessListAdd(pawn);
				}
			}
		}
		//p2
		Rook rook=new Rook( 0,0,!isEnemy);
		ChessInfo.AIChessListAdd(rook);
		chessboard[0][0].setIcon(p2RookIcon);
		chessboard[0][0].setChess(rook);
		
		Knight knight=new Knight( 0,1,!isEnemy);
		ChessInfo.AIChessListAdd(knight);
		chessboard[0][1].setIcon(p2KnigthIcon);
		chessboard[0][1].setChess(knight);
		
		Bishop bishop=new Bishop( 0,2,!isEnemy);
		ChessInfo.AIChessListAdd(bishop);
		chessboard[0][2].setIcon(p2BishopIcon);
		chessboard[0][2].setChess(bishop);
		
		Queen queen=new Queen(0, 3,!isEnemy);
		ChessInfo.AIChessListAdd(queen);
		chessboard[0][3].setIcon(p2QueenIcon);
		chessboard[0][3].setChess(queen);
		
		King king=new King(0, 4,!isEnemy);
		ChessInfo.AIChessListAdd(king);
		chessboard[0][4].setIcon(p2KingIcon);
		chessboard[0][4].setChess(king);
		
		Bishop bishop2=new Bishop( 0,5,!isEnemy);
		ChessInfo.AIChessListAdd(bishop2);
		chessboard[0][5].setIcon(p2BishopIcon);
		chessboard[0][5].setChess(bishop2);
		
		Knight knight2=new Knight( 0,6,!isEnemy);
		ChessInfo.AIChessListAdd(knight2);
		chessboard[0][6].setIcon(p2KnigthIcon);
		chessboard[0][6].setChess(knight2);
		
		Rook rook2=new Rook( 0,7,!isEnemy);
		ChessInfo.AIChessListAdd(rook2);
		chessboard[0][7].setIcon(p2RookIcon);
		chessboard[0][7].setChess(rook2);
		
		//p1
		Rook rook3=new Rook( 7,0,isEnemy);
		ChessInfo.playerChessList.add(rook3);
		chessboard[7][0].setIcon(myRookIcon);
		chessboard[7][0].setChess(rook3);
		
		Knight knight3=new Knight( 7,1,isEnemy);
		ChessInfo.playerChessList.add(knight3);
		chessboard[7][1].setIcon(myKnigthIcon);
		chessboard[7][1].setChess(knight3);
		
		
		Bishop bishop3=new Bishop( 7,2,isEnemy);
		ChessInfo.playerChessList.add(bishop3);
		chessboard[7][2].setIcon(myBishopIcon);
		chessboard[7][2].setChess(bishop3);
		
		Queen queen2=new Queen(7, 3,isEnemy);
		ChessInfo.playerChessList.add(queen2);
		chessboard[7][3].setIcon(myQueenIcon);
		chessboard[7][3].setChess(queen2);
		
		
		King king2=new King(7, 4,isEnemy);
		ChessInfo.playerChessList.add(king2);
		chessboard[7][4].setIcon(myKingIcon);
		chessboard[7][4].setChess(king2);
		
		Bishop bishop4=new Bishop( 7,5,isEnemy);
		ChessInfo.playerChessList.add(bishop4);
		chessboard[7][5].setIcon(myBishopIcon);
		chessboard[7][5].setChess(bishop4);
		
		Knight knight4=new Knight( 7,6,isEnemy);
		ChessInfo.playerChessList.add(knight4);
		chessboard[7][6].setIcon(myKnigthIcon);
		chessboard[7][6].setChess(knight4);
		
		Rook rook4=new Rook( 7,7,isEnemy);
		ChessInfo.playerChessList.add(rook4);
		chessboard[7][7].setIcon(myRookIcon);
		chessboard[7][7].setChess(rook4);
		
		if (isEnemy) {
			myKing=king2;
			enemyKing=king;
		}else {
			myKing=king;
			enemyKing=king2;
		}
		
 	}
	
	public static void moveChess(ChessBoard presentChessBoard, ChessBoard clickBoard) {
		if (!isSinglePlayer) {//双人对战
			MenuFrame.client.sendMoveStepToServer(presentChessBoard, clickBoard);
		}
		if (clickBoard.getChess()!=null) {//吃到棋子,从棋子List中移除
			if (clickBoard.getChess().isEnemy()) {
				ChessInfo.AIChessListRemove(clickBoard.getChess());
			}else {
				ChessInfo.playerChessListRemove(clickBoard.getChess());
			}
			System.out.println("single eat enemy :CBPanel moveChess");
		}
		Chess chess=presentChessBoard.getChess();
		Chess chess2=clickBoard.getChess();
		chess.setCoor(clickBoard);//更新坐标
		chess.setStep(chess.getStep()+1);
		Icon icon=presentChessBoard.getIcon();
		presentChessBoard.setChess(null);//棋子移动
		presentChessBoard.setIcon(null);//去除图案
		presentChessBoard = clickBoard;// 当前位置更新
		presentChessBoard.setChess(chess);//棋子移动
		presentChessBoard.setIcon(icon);//更新棋子图案
		isGameOver(chess2);
	}
	/**
	 * 接受服务端发来的对方棋子的移动
	 * @param present
	 * @param click
	 */
	public static void acceptFromServerAndMoveChess(ChessBoard present, ChessBoard click) {
		ChessBoard presentChessBoard=chessboard[present.getCoorY()][present.getCoorX()];
		ChessBoard clickBoard=chessboard[click.getCoorY()][click.getCoorX()];
		Chess chess=presentChessBoard.getChess();
		Chess chess2=click.getChess();
		if (chess.getName().equals("pawn")&&chess.getStep()==0
				&&Math.abs(present.getCoorY()-click.getCoorY())==2){
			//兵
			((Pawn) chess).setFirstStep(2);
		}
		chess.setCoor(clickBoard);//更新坐标
		chess.setStep(chess.getStep()+1);
		Icon icon=presentChessBoard.getIcon();
		presentChessBoard.setChess(null);//棋子移动
		presentChessBoard.setIcon(null);//去除图案
		presentChessBoard = clickBoard;// 当前位置更新
		presentChessBoard.setChess(chess);//棋子移动
		presentChessBoard.setIcon(icon);//更新棋子图案
	
		if (chess2 != null) {
			if (chess2.getCoorY() == myKing.getCoorY() && chess2.getCoorX() == myKing.getCoorX()) {
				System.out.println("我方获胜");
				JOptionPane.showMessageDialog(null, "我方获胜");
//				MainPlayFrame.newGame();
				System.exit(0);
			} else if (chess2.getCoorY() == enemyKing.getCoorY() && chess2.getCoorX() == enemyKing.getCoorX()) {
				JOptionPane.showMessageDialog(null, "敌方获胜");
				System.exit(0);
//				MainPlayFrame.newGame();
			}
		}
		
		//我的计时开始，未考虑特殊走法
		turnToMyTrun(!isPlayerTurn);
	}
	/**
	 * 
	 */
	public static void isGameOver(Chess chess){
		if (chess==myKing) {
			JOptionPane.showMessageDialog(null, "我方获胜");
			System.exit(0);
//			MainPlayFrame.newGame();
		}else if (chess==enemyKing) {
			JOptionPane.showMessageDialog(null, "敌方获胜");
			System.exit(0);
//			MainPlayFrame.newGame();
		}
	}
	/**
	 * 转到我的回合
	 * @param isEnable true可点击，false不可
	 */
	public static void turnToMyTrun(boolean isEnable) {
		MainPlayFrame.player1InfoJpanel.setMyTurn(isEnable);
		MainPlayFrame.player2InfoJpanel.setMyTurn(!isEnable);
		ChessBoardPanel.coverPanel.setVisible(!isEnable);
		isPlayerTurn = isEnable;
	}
	
	/**
	 * 点击棋盘
	 */
	public void actionPerformed(ActionEvent e) {
		ChessBoard clickBoard=(ChessBoard) e.getSource();//当前所点击的位置
		//若当前有已选的棋盘位置
		if (presentChessBoard != null&&presentChessBoard.getChess()!=null) {
			if (clickBoard!= presentChessBoard) {
				boolean walkable = presentChessBoard.getChess().isWalkable(clickBoard,chessboard);
				//点击位置可以前进
				if (walkable) {
					moveChess(presentChessBoard, clickBoard);
					//我的回合结束，停止计时
					turnToMyTrun(!isPlayerTurn);
				}else if(clickBoard.getChess()!=null&&!clickBoard.getChess().isEnemy()){
						presentChessBoard = clickBoard;// 更换操作棋子
						System.out.println("更换操作棋子");
				}else if (presentChessBoard.getChess()!=null&&presentChessBoard.getChess().isHasSpecialWalkWay()) {
					presentChessBoard.getChess().specialWalkWay(presentChessBoard, clickBoard, chessboard);
					System.out.println("特殊走法");
				}else{
					System.out.println("不能走");
				}
			}
		}else{//过后尝试和前面的合并
			if(clickBoard.getChess()!=null&&!clickBoard.getChess().isEnemy()){
				presentChessBoard = clickBoard;// 当前操作位置更新
			}
		}
		
		//王被将军
	}

	public static void killChess(ChessBoard killedChessBoard) {
		chessboard[killedChessBoard.getCoorY()][killedChessBoard.getCoorX()].setIcon(null);
		chessboard[killedChessBoard.getCoorY()][killedChessBoard.getCoorX()].setChess(null);
	}
	public static boolean isPlayerTurn() {
		return isPlayerTurn;
	}

	public static void setPlayerTurn(boolean isPlayerTurn) {
		ChessBoardPanel.isPlayerTurn = isPlayerTurn;
	}

}
