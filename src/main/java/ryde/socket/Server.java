package ryde.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ryde.gui.ChessBoard;


public class Server implements Runnable{

	static List<Socket> userSockets;	
	private ServerSocket ss;
	private Socket socket;
	private int thePort;
	public Server(int thePort){
		this.thePort=thePort;
		Thread t=new Thread(this);
		 t.start();
	}
	public static List<Socket> getUserSockets() {
		return userSockets;
	}
		
	/**
	 * 创建接收客户端消息线程
	 */
	public void run() {
		userSockets=new ArrayList<Socket>();
		try {
			ss=new ServerSocket(thePort);
			while(true){//connect					
			socket= ss.accept();
			userSockets.add(socket);
			System.out.println("连接+1");
			System.out.println("size:"+userSockets.size());
			new Thread(new RecClientMes(socket),"接受客户端消息线程").start();			
			}
		}catch(java.net.BindException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "程序已在运行");
			System.exit(0);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	public void close(){
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

 /**
  * A class receive message from client
  * @author Ryde_L
  *
  */
 
class RecClientMes implements Runnable{
	
	private Socket socket;
	private List<Socket> userSockets;
	
	BufferedReader brin;
	ObjectInputStream objectInputStream;
	ObjectOutputStream objectOS;
	OutputStream os;

	
	public RecClientMes(Socket socket){
		this.socket=socket;
		
	}

	public void run() {
		InputStream inputStream;
		Socket enemySocket=null;
		int count=0;
		int count2=0;
		while (socket.isConnected() && !socket.isClosed()) {
				userSockets = Server.getUserSockets();
				for (int i = 0; i < userSockets.size(); i++) {
					if (userSockets.get(i) != socket) {
						enemySocket = userSockets.get(i);
					}
				}
				try {
					inputStream = socket.getInputStream();
					count++;
					if (userSockets.size()==1&&count>1){//等待玩家2
						Thread.sleep(500);
						continue;
					}
					if (userSockets.size()==2&&count2==0&&enemySocket == userSockets.get(1)) {
						count2++;
						os = socket.getOutputStream();
						os.write("LOCK".getBytes());
					}
						
					byte[] b = new byte[4];
					inputStream.read(b);
					String message = new String(b);
					System.out.println("服务端接收消息");
					System.out.println(message);
					//只考虑2人情况
					if (message.equals("JOIN")) {
						os = socket.getOutputStream();
						os.write("JOIN".getBytes());
						if (userSockets.size()==1) 
							os.write("fake".getBytes());
						else 
							os.write("true".getBytes());
					}else if (message.equals("MOVE")) {
						objectInputStream = new ObjectInputStream(socket.getInputStream());
						ChessBoard presentChessBoard = (ChessBoard) objectInputStream.readObject();
						ChessBoard clickBoard = (ChessBoard) objectInputStream.readObject();
						os = enemySocket.getOutputStream();
						os.write(b);
						objectOS = new ObjectOutputStream(os);
						objectOS.writeObject(presentChessBoard);
						objectOS.writeObject(clickBoard);
					} else if (message.equals("KILL")) {
						os = enemySocket.getOutputStream();
						os.write(b);
						objectOS = new ObjectOutputStream(os);
						objectOS.writeObject(new ObjectInputStream(socket.getInputStream()).readObject());
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println(Server.userSockets.remove(socket));
					break;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}catch (Exception e){
					System.out.println(Server.userSockets.remove(socket));
					
				}
			}

			// if(message.equals("exit")){
			// Server.getUserSockets().remove(socket);
			// socket.close();
			// System.out.println("连接-1");
			// }
	}	

	
	
}

