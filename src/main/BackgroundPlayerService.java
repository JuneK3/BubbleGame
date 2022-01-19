package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

// 메인스레드는 키보드 이벤트를 처리하기 바쁨.
// 백그라운드에서 Player를 계속 관찰함
public class BackgroundPlayerService implements Runnable {

	private BufferedImage image;
	private Player player;
	private List<Bubble> bubbles;

	public BackgroundPlayerService(Player player) {
		this.player = player;
		this.bubbles = player.getBubbles();
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			// 1. 버블 충돌 체크
			for (int i = 0; i < bubbles.size(); i++) {
				Bubble bubble = bubbles.get(i);
				if (bubble.getState() == 1) {
					if ((Math.abs(player.getX() - bubble.getX()) < 10) &&
							Math.abs(player.getY() - bubble.getY()) > 0 && Math.abs(player.getY() - bubble.getY()) < 50) {
						System.out.println("적군 사살 완료");
						bubble.clearBubbled();
						break;
					}
				}
			}

			// 2. 벽 충돌 체크
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
			} else {
//				System.out.println(player.isUp() + " " + player.isDown());
				// 흰색인 경우 위나 아래로 이동하는 상태가 아닌 경우에만 아래로 하강
				// 제일 밑의 바닥에 충돌한 경우 setDown(false)로 하강 방지
				if (!player.isUp() && !player.isDown()) {
					player.down();
				}
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
