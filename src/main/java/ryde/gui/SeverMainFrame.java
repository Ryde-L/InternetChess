package ryde.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ryde.socket.Server;
/**
 * 服务端主界面
 * @author Ryde_L
 *
 */
public class SeverMainFrame extends JFrame implements ActionListener{
	JButton severButton;
	JPanel contentPanel;
	Server s=new Server(30002);
	public SeverMainFrame(){
		contentPanel=new JPanel();
		contentPanel.setLayout(null);
		severButton=new JButton("关闭服务");
		severButton.setBounds(50, 50, 100, 50);
		severButton.addActionListener(this);
		contentPanel.add(severButton);
		setContentPane(contentPanel);
		setSize(200,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
			s.close();
			System.exit(0);
	}
	public static void main(String[] args) {
		SeverMainFrame severFrame=new SeverMainFrame();
	}

}
