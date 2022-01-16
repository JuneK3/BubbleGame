package main;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable {

	Player player;
	BackgroundBubbleService bubbleService;

	// 위치 상태
	private int x;
	private int y;

	// 동작 상태
	private boolean up;
	private boolean left;
	private boolean right;

	// 적을 맞춘 상태
	private int state; // 0(물방울), 1(적을 가둔 물방울)

	private ImageIcon bubble; // 물방울
	private ImageIcon bubbled; // 적을 가둔 물방울
	private ImageIcon bomb; // 물방울이 터진 상태

	public Bubble(Player player) {
		this.player = player;
		initObject();
		initSetting();
		initThread();
	}

	// 물방울은 대각선 방향이 아닌 한방향으로만 이동하기 때문에
	// 물방울의 이동을 하나의 Thread가 담당하면 된다.
	private void initThread() {
		new Thread(() -> {
			if (player.getPlayerDirection() == PlayerDirection.LEFT) {
				left();
			} else {
				right();
			}
		}).start();
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
		bubbleService = new BackgroundBubbleService(this);
	}


	@Override
	public void left() {
		left = true;
		for (int i = 0; i < 400; i++) {
			x -= 1;
			setLocation(x, y);
			if(bubbleService.leftWall()){
				break;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		up();
	}

	@Override
	public void right() {
		right = true;
		for (int i = 0; i < 400; i++) {
			x += 1;
			setLocation(x, y);
			if(bubbleService.rightWall()){
				break;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		up();
	}

	@Override
	public void up() {
		up = true;
		while(up){
			y--;
			setLocation(x, y);
			if(bubbleService.topWall()){
				break;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
