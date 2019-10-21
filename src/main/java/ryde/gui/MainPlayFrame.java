package ryde.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.activation.ActivationID;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ryde.ai.AI;
import ryde.socket.Client;


public class MainPlayFrame extends JFrame implements ActionListener {

	private static JPanel chessBoardPanel;
	static JPanel contentPane;
	//private MesAndOperaJpanel mesAndOperaPanel;
	private JButton backButton;
	static PlayerInfoJpanel player1InfoJpanel,player2InfoJpanel;
	static MainPlayFrame frame;
	static boolean isSinglePlayer;
//	public static boolean isGameOver=false;
	
	private static boolean isEnemy;

	/**
	 * Create the frame.
	 */
	public MainPlayFrame(boolean isEnemy,boolean isSinglePlayer) {
		super("国际象棋");
		this.isEnemy=isEnemy;
		contentPane = new JPanel();
		contentPane.setLayout(null);
	
		//棋盘
		chessBoardPanel=new ChessBoardPanel(isEnemy,isSinglePlayer);
		chessBoardPanel.setBounds(0, 50, 500, 500);
		contentPane.add(chessBoardPanel);
		//头像，姓名，时钟
		player1InfoJpanel=new PlayerInfoJpanel(isEnemy);
		player2InfoJpanel=new PlayerInfoJpanel(!isEnemy);
		player1InfoJpanel.setBounds(0, 0, 250, 50);
		player2InfoJpanel.setBounds(250, 0, 250, 50);
		contentPane.add(player1InfoJpanel);
		contentPane.add(player2InfoJpanel);
		new Thread(player1InfoJpanel).start();
		new Thread(player2InfoJpanel).start();
		
		backButton=new JButton("退出");
		backButton.setBounds(0, 550, 500, 30);
		backButton.addActionListener(this);
		contentPane.add(backButton);
		
		setContentPane(contentPane);
		setSize(510, 620);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	
		if (isSinglePlayer) {
			AI ai=new AI();
			Thread thread=new Thread(ai);
			thread.start();
		}
		
	}
	/**
	 * 游戏结束，重新开始
	 */
//	public static void newGame(){
//			contentPane.remove(chessBoardPanel);
//			ChessBoardPanel chessBoardPanel=new ChessBoardPanel(isEnemy,isSinglePlayer);
//			chessBoardPanel.setBounds(0, 50, 500, 500);
//			contentPane.add(chessBoardPanel);
//	}
	
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
		
	}
	
}
