package com.mochilo.bouncing;

import sun.audio.*;
import java.io.*;

public class SoundHelper {

	public void backgroundMusic() {

		AudioStream backgroundMusic;
		AudioData musicData;
		AudioPlayer musicPlayer = AudioPlayer.player;
		ContinuousAudioDataStream loop = null;
		try {
			backgroundMusic = new AudioStream(getClass().getResourceAsStream("/music/mechaWorld.wav"));
			musicData = backgroundMusic.getData();
			loop = new ContinuousAudioDataStream(musicData);
			musicPlayer.start(loop);
		} catch (IOException error) {
			System.out.println(error);
		}
	}
	
	public void bounceMusic(){
		
		AudioStream soundEffect;
		AudioPlayer musicPlayer = AudioPlayer.player;
		try {
			soundEffect = new AudioStream(getClass().getResourceAsStream("/music/mechaWorld.wav"));
			musicPlayer.start(soundEffect);
			musicPlayer.stop(soundEffect);
		} catch (IOException error) {
			System.out.println(error);
		}
		
	}

}
