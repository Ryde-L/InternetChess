package ryde.gui;


import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Ryde_L
 *玩家头像、名称及时间
 */
public class PlayerInfoJpanel extends JPanel implements Runnable{
	private int time=0;
	private  boolean isMyTurn;	
	private JLabel headPicLabel,backgroundLabel;
	private JLabel[] clockLabel;
	private ImageIcon headIcon;
	private ImageIcon[] clockIcon;
	private static boolean isNameUsed=false;
	public PlayerInfoJpanel(boolean isEnemy){
		//super();
		headIcon=new ImageIcon("images/default1.jpg");
		clockIcon=new ImageIcon[10];
		for (int i = 0; i < clockIcon.length; i++) {
			clockIcon[i]=new ImageIcon("images/"+i+".png");
		}
		this.setLayout(null);
		headPicLabel =new JLabel();
		headPicLabel.setIcon(headIcon);
		headPicLabel.setBounds(0, 0, 50, 50);
		//headPicLabel.setSize(50, 50);
		String name="我";		
		if (isNameUsed) {
			name="对手";
		}
		isNameUsed=true;
		backgroundLabel=new JLabel(name);
		backgroundLabel.setBounds(50, 0, 50, 50);
		backgroundLabel.setBackground(new Color(0,0,0));
		this.add(headPicLabel);
		this.add(backgroundLabel);
		clockLabel=new JLabel[4];
		for(int i=0;i<4;i++){
			clockLabel[i]=new JLabel();
			clockLabel[i].setIcon(clockIcon[0]);
			clockLabel[i].setBounds(100+i*25, 0, 25, 50);
			this.add(clockLabel[i]);
		}
		
		this.setSize(200, 50);
		this.setVisible(true);
		isMyTurn=!isEnemy;
	}
	
	//计时
	public void run() {
		while (true) {
			if (isMyTurn) {
				int sec1 = time % 60 % 10;
				int sec2 = time % 60 / 10;
				int min1 = time / 60 % 60 % 10;
				int min2 = time / 60 % 60 / 10;
				clockLabel[3].setIcon(clockIcon[sec1]);
				clockLabel[2].setIcon(clockIcon[sec2]);
				clockLabel[1].setIcon(clockIcon[min1]);
				clockLabel[0].setIcon(clockIcon[min2]);
				time++;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public  boolean isMyTurn() {
		return isMyTurn;
	}

	public void setMyTurn(boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
		
	}

}
