package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// 메인스레드는 키보드 이벤트를 처리하기 바쁨.
// 백그라운드에서 Player를 계속 관찰함
public class BackgroundPlayerService implements Runnable {

	private BufferedImage image;
	private Player player;

	public BackgroundPlayerService(Player player) {
		this.player = player;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			// 색상 확인
			Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
			Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));

			// 둘중 하나라도 -1(흰색)이 아닌 경우 바닥 충돌로 인식
			int bottomColor = image.getRGB(player.getX() + 15, player.getY() + 50 + 5)
					+ image.getRGB(player.getX() + 50 - 15, player.getY() + 50 + 5);

			// 바닥 충돌 확인
			if (bottomColor != -2) { // 흰색이 아닌 경우 바닥 충돌로 인식
//				System.out.println(bottomColor);
//				System.out.println("바닥 충돌");
				player.setDown(false);
			}

			if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
//				System.out.println("왼쪽 벽에 충돌");
				player.setLeft(false);
				player.setLeftWallCrash(true);
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
//				System.out.println("오른쪽 벽에 충돌");
				player.setRight(false);
				player.setRightWallCrash(true);
			} else {
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
