package main.component;

import lombok.Getter;
import lombok.Setter;
import main.service.BackgroundPlayerService;
import main.BubbleFrame;
import main.Moveable;
import main.state.PlayerDirection;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player extends JLabel implements Moveable {
	private BubbleFrame mContext;
	private List<Bubble> bubbles;
	
	private int state; // 0: 살아있는 상태, 1: 죽은 상태
	
	// 위치 상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	// 플레이어의 방향
	private PlayerDirection playerDirection;

	// 플레이어 속도 상태
	private final int SPEED = 4;
	private final int JUMP_SPEED = 2;

	// 외벽 충돌 상태
	private boolean leftWallCrash;
	private boolean rightWallCrash;

	private ImageIcon playerR, playerL, playerRdie, playerLdie;

	public Player(BubbleFrame mContext) {
		this.mContext = mContext;
		initObject();
		initSetting();
		initBackgroundPlayerService();
	}

	private void initBackgroundPlayerService() {
		new Thread(new BackgroundPlayerService(this)).start();
	}

	private void initSetting() {
		x = 80;
		y = 535;

		left = false;
		right = false;
		up = false;
		down = false;

		leftWallCrash = false;
		rightWallCrash = false;

		playerDirection = PlayerDirection.RIGHT;
		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
	}

	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
		playerRdie = new ImageIcon("image/playerRdie.png");
		playerLdie = new ImageIcon("image/playerLdie.png");
		bubbles = new ArrayList<>();
	}

	@Override
	public void left() {
		left = true;
		playerDirection = PlayerDirection.LEFT;
		new Thread(() -> {
			while (left && getState() == 0) {
				setIcon(playerL);
				x = x - SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void right() {
		right = true;
		playerDirection = PlayerDirection.RIGHT;
		new Thread(() -> {
			while (right && getState() == 0) {
				setIcon(playerR);
				x = x + SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	// left + up, right + up이 가능해야 함
	@Override
	public void up() {
		up = true;
		new Thread(() -> {
			for (int i = 0; i < 120 / JUMP_SPEED; i++) {
				y = y - JUMP_SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			up = false;
			down();
		}).start();
	}

	@Override
	public void down() {
		down = true;
		new Thread(() -> {
			while (down) {
				y = y + JUMP_SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			down = false;
		}).start();
	}

	@Override
	public void attack() {
		new Thread(()->{
			Bubble bubble = new Bubble(mContext);
			mContext.add(bubble);
			bubbles.add(bubble);
			if(playerDirection == PlayerDirection.LEFT){
				bubble.left();
			}else{
				bubble.right();
			}
		}).start();
	}

	public void die() {
		new Thread(() -> {
			setState(1);
			setIcon(PlayerDirection.RIGHT == playerDirection ? playerRdie : playerLdie);
			try {
				if(!isUp() && !isDown()) {
					up();
				}
				Thread.sleep(2000);
				mContext.remove(this);
				mContext.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("플레이어 사망.");
		}).start();
	}
}
