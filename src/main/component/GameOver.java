package main.component;

import javax.swing.*;

public class GameOver extends JLabel {
	// 위치 상태
	private int x;
	private int y;

	private ImageIcon gameOver;

	public GameOver() {
		initObject();
		initSetting();
	}

	private void initSetting() {
		x = 335;
		y = 250;
		setIcon(gameOver);
		setSize(300, 155);
		setLocation(x, y);
	}

	private void initObject() {
		gameOver = new ImageIcon("image/gameOver.png");
	}
}
