package test;

import javax.swing.*;

public class BubbleFrame extends JFrame {
	private JPanel panel1;
	private JButton button1;

	public BubbleFrame() {
		setSize(1000, 640);
		setLayout(null); // absolute layout으로 자유롭게 그림을 그릴수 있다.
		setLocationRelativeTo(null); // 창이 우측 상단에서 생기지 않도록 함.
		setVisible(true); // 패널에 그림을 그릴수 있게됨
	}

	public static void main(String[] args) {
		new BubbleFrame();
	}
}
