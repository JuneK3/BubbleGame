package main.component;

import lombok.Getter;
import lombok.Setter;
import main.service.BackgroundEnemyService;
import main.BubbleFrame;
import main.state.EnemyDirection;
import main.Moveable;

import javax.swing.*;

@Getter
@Setter
public class Enemy extends JLabel implements Moveable {
	private BubbleFrame mContext;
	// 위치 상태
	private int x;
	private int y;

	private int state; // 0: 살아있는 상태, 1: 물방울에 갇힌 상태

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	// 플레이어의 방향
	private EnemyDirection enemyDirection;

	// 플레이어 속도 상태
	private final int SPEED = 3;
	private final int JUMP_SPEED = 1;

	private ImageIcon enemyR, enemyL;

	public Enemy(BubbleFrame mContext) {
		this.mContext = mContext;
		initObject();
		initSetting();
		initBackgroundEnemyService();
		right();
	}

	private void initBackgroundEnemyService() {
		new Thread(new BackgroundEnemyService(this)).start();
	}

	private void initSetting() {
		x = 480;
		y = 178;

		left = false;
		right = false;
		up = false;
		down = false;

		state = 0;

		enemyDirection = EnemyDirection.RIGHT;
		setIcon(enemyR);
		setSize(50, 50);
		setLocation(x, y);
	}

	private void initObject() {
		enemyR = new ImageIcon("image/enemyR.png");
		enemyL = new ImageIcon("image/enemyL.png");
	}

	@Override
	public void left() {
		left = true;
		enemyDirection = EnemyDirection.LEFT;
		new Thread(() -> {
			while (left) {
				setIcon(enemyL);
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
		enemyDirection = EnemyDirection.RIGHT;
		new Thread(() -> {
			while (right) {
				setIcon(enemyR);
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
}
