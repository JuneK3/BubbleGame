package main.music;

import java.io.File;

import javax.sound.sampled.*;

public class BGM {
	private AudioInputStream ais;
	private Clip clip;
	public BGM() {
		try {
			ais = AudioSystem.getAudioInputStream(new File("sound/bgm.wav"));
			clip = AudioSystem.getClip();
			clip.open(ais);
			// 소리 설정
			FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			// 볼륨 조정
			control.setValue(-30.0f);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopBGM(){
		clip.stop();
	}
}
