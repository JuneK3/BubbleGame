package main;

import javax.swing.*;

public class Player extends JLabel implements Moveable {
	// 위치 상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	private ImageIcon playerR, playerL;

	public Player() {
		initObject();
		initSetting();
	}

	private void initSetting() {
		x = 55;
		y = 535;

		left = false;
		right = false;
		up = false;
		down = false;

		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
	}

	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}

	@Override
	public void left() {
		setIcon(playerL);
		x = x - 10;
		setLocation(x, y);
	}

	@Override
	public void right() {
		setIcon(playerR);
		x = x + 10;
		setLocation(x, y);
	}

	@Override
	public void up() {

	}

	@Override
	public void down() {

	}
}
