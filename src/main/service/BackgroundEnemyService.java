package main.service;

import main.component.Enemy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// 메인스레드는 키보드 이벤트를 처리하기 바쁨.
// 백그라운드에서 Player를 계속 관찰함
public class BackgroundEnemyService implements Runnable {

	private BufferedImage image;
	private Enemy enemy;

	public BackgroundEnemyService(Enemy enemy) {
		this.enemy = enemy;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (enemy.getState() == 0) {
			// 벽 충돌 체크
			// 색상 확인
			Color leftColor = new Color(image.getRGB(enemy.getX() - 10, enemy.getY() + 25));
			Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 15, enemy.getY() + 25));

			// 둘중 하나라도 -1(흰색)이 아닌 경우 바닥 충돌로 인식
			int bottomColor = image.getRGB(enemy.getX() + 15, enemy.getY() + 50 + 5)
					+ image.getRGB(enemy.getX() + 50 - 15, enemy.getY() + 50 + 5);

			// 바닥 충돌 확인
			if (bottomColor != -2) { // 흰색이 아닌 경우 바닥 충돌로 인식
//				System.out.println(bottomColor);
//				System.out.println("바닥 충돌");
				enemy.setDown(false);
			} else {
//				System.out.println(enemy.isUp() + " " + enemy.isDown());
				// 흰색인 경우 위나 아래로 이동하는 상태가 아닌 경우에만 아래로 하강
				// 제일 밑의 바닥에 충돌한 경우 setDown(false)로 하강 방지
				if (!enemy.isUp() && !enemy.isDown()) {
					enemy.down();
				}
			}

			if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
//				System.out.println("왼쪽 벽에 충돌");
				enemy.setLeft(false);
				if (!enemy.isRight()){
					enemy.right();
				}
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
//				System.out.println("오른쪽 벽에 충돌");
				enemy.setRight(false);
				if (!enemy.isLeft()){
					enemy.left();
				}
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
