package ryde.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import ryde.gui.ChessBoard;
import ryde.gui.ChessBoardPanel;
import ryde.gui.MainPlayFrame;



public class Client implements Runnable{
	private static Socket socket;
	public static int count=0;
	private  SocketAddress socketAddress;
	private ChessBoard presentChessBoard=null;
	private ChessBoard clickBoard=null;
	public static MainPlayFrame frame;
	ObjectInputStream objectInputStream=null;
	ObjectOutputStream objectOS;
	OutputStream os;
	InputStream inputStream;
	BufferedReader brin;

	public Client(){
		 Thread t=new Thread(this);
		 t.start();
	}
	/**
	 * 客户端发送消息给服务端
	 * @param socket
	 * @param message
	 */
	public void SendMesToServer(Socket socket,String message){
		try {
			PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
			out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void isContainKey(char map,Object object){
		OutputStream os;
		ObjectOutputStream objectOS;
		try {		
			os = socket.getOutputStream();
			objectOS=new ObjectOutputStream(os);
			os.write("MAPS".getBytes());
			os.write(map);
			objectOS.writeObject(object);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 加入双人游戏
	 */
	public void joinGame(){
		try {
			os=socket.getOutputStream();
			os.write("JOIN".getBytes());
			//os.write();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void sendMoveStepToServer(ChessBoard presentChessBoard,ChessBoard clickBoard){
		
		try {
			os=socket.getOutputStream();
			os.write("MOVE".getBytes());
			objectOS=new ObjectOutputStream(os);
			objectOS.writeObject(presentChessBoard);
			objectOS.writeObject(clickBoard);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param killedChessBoard
	 */
	public static void sendKilledChessAway(ChessBoard killedChessBoard){
		OutputStream os;
		ObjectOutputStream objectOS;
		try {
			os=socket.getOutputStream();
			os.write("KILL".getBytes());
			objectOS=new ObjectOutputStream(os);
			objectOS.writeObject(killedChessBoard);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	/**
	 * 接收服务端消息
	 */
	public void run() {
		try {
			//while (true) {
				socket = new Socket();
				socketAddress = new InetSocketAddress("localhost", 30002);
				while (!socket.isConnected() && !socket.isClosed()) {
					try {
						socket = new Socket();
						socket.connect(socketAddress, 60 * 1000);
						System.out.println("连接");
					} catch (IOException e) {
						// try {
						// //连接失败，5秒后重试
						// Thread.sleep(5 * 1000);
						// } catch (InterruptedException e1) {
						// e1.printStackTrace();
						// }
						// e.printStackTrace();
					}
				}
				joinGame();
				while (true) {
					inputStream = socket.getInputStream();
					byte[] b = new byte[4];
					inputStream.read(b);
					String message = new String(b);
					if (message.equals("JOIN")) {
						inputStream.read(b);
						frame=new MainPlayFrame(Boolean.valueOf(new String(b)),false);
					}else if (message.equals("MOVE")) {
						objectInputStream = new ObjectInputStream(inputStream);
						ChessBoard presentChessBoard = (ChessBoard) objectInputStream.readObject();
						ChessBoard clickBoard = (ChessBoard) objectInputStream.readObject();
						ChessBoardPanel.acceptFromServerAndMoveChess(presentChessBoard, clickBoard);
					}else if(message.equals("LOCK")){
						ChessBoardPanel.turnToMyTrun(true);
					}else if(message.equals("KILL")) {
						objectInputStream = new ObjectInputStream(inputStream);
						ChessBoard killedChessBoard = (ChessBoard) objectInputStream.readObject();
						ChessBoardPanel.killChess(killedChessBoard);
					}
//					else if (message.equals("MAPS")) {
//						System.out.println("接收MAP");
//						objectInputStream = new ObjectInputStream(socket.getInputStream());
//						roomMap = (Map<RoomButton, Socket>) objectInputStream.readObject();
//						socketMap = (Map<Socket, Socket>) objectInputStream.readObject();
//						System.out.println(socketMap.isEmpty());
//					}
					if (message != null) {
						System.out.println("FROM SE:" + message);
					}
					Thread.sleep(1000);
				}
			//}
				
		} catch (IOException e) {
			e.printStackTrace();
			// break;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	public static Socket getSocket() {
		System.out.println("getcount:"+count);
		return socket;
	}
}


	
	

