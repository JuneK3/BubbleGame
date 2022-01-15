package main;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable{

	Player player;

	// 위치 상태
	private int x;
	private int y;

	// 동작 상태
	private boolean up;
	private boolean left;
	private boolean right;

	// 적을 맞춘 상태
	private int state; // 0(물방울), 1(적을 가둔 물방울)

	private	ImageIcon bubble; // 물방울
	private	ImageIcon bubbled; // 적을 가둔 물방울
	private	ImageIcon bomb; // 물방울이 터진 상태

	public Bubble(Player player) {
		this.player = player;
		initObject();
		initSetting();
	}

	private void initSetting() {
		left = false;
		right = false;
		up = false;
		state = 0;

		this.x = player.getX();
		this.y = player.getY();

		setIcon(bubble);
		setSize(50, 50);
	}

	private void initObject() {
		bubble = new ImageIcon("image/bubble.png");
		bubbled = new ImageIcon("image/bubbled.png");
		bomb = new ImageIcon("image/bomb.png");
	}


	@Override
	public void left() {
		
	}

	@Override
	public void right() {

	}

	@Override
	public void up() {

	}
}
