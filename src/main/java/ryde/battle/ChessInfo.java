package ryde.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ryde.InternetChess.Chess;
import ryde.InternetChess.Pawn;

public class ChessInfo {
	public static List<Chess> AIChessList=new ArrayList<Chess>();
	public static List<Chess> playerChessList=new ArrayList<Chess>();
	
	public ChessInfo(){
		for (int i = 0; i < 8; i++) {
			AIChessList.add(new Pawn(6, i, false));
			playerChessList.add(new Pawn(1, i, true));
		}
	}
	public static boolean AIChessListRemove(Chess chess){
		return AIChessList.remove(chess);
	}
	public static boolean playerChessListRemove(Chess chess){
		return playerChessList.remove(chess);
	}
	public static boolean AIChessListAdd(Chess chess){
		return AIChessList.add(chess);
	}
	public static boolean playerChessListAdd(Chess chess){
		return playerChessList.add(chess);
	}
	
}
