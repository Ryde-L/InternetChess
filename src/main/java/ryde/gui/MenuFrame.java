package ryde.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ryde.socket.Client;
/**
 * 起始菜单界面
 * @author Ryde_L
 *
 */
public class MenuFrame extends JFrame implements ActionListener {
	JPanel contentPane,pane,helpPanel;
	JButton playSingalButton,playDoubleButton,helpButton,exitButton;
	static MenuFrame  menuFrame;
	static Client client;
	static MainPlayFrame mainPlayFrame;
	public MenuFrame(){
		contentPane = new JPanel();
		contentPane.setLayout(null);
		//添加按钮
		playSingalButton=new JButton("人机对战");
		playDoubleButton=new JButton("联网对战");
		helpButton=new JButton("帮助");
		exitButton=new JButton("退出");
		addButton(playSingalButton,200, 150, 100, 30);
		addButton(playDoubleButton,200, 200, 100, 30);
		addButton(helpButton,200, 250, 100, 30);
		addButton(exitButton,200, 300, 100, 30);
		setContentPane(contentPane);
		setSize(510, 620);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	public void addButton(JButton b,int x,int y,int width,int hight){
		b.setBounds(x, y, width, hight);
		b.addActionListener(this);
		contentPane.add(b);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playSingalButton) {
			setVisible(false);
			// 棋盘窗口
			mainPlayFrame = new MainPlayFrame(false, true);
			ChessBoardPanel.coverPanel.setVisible(false);
		} else if (e.getSource() == playDoubleButton) {
			
//			//刷新
//			contentPane.revalidate();
//			repaint();
			client=new Client();
			this.setVisible(false);
			
		
		}else if (e.getSource()==helpButton) {
			JOptionPane.showMessageDialog(contentPane, "并没有什么帮助");
		}else if (e.getSource()==exitButton) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		menuFrame=new MenuFrame();
	}
}
