package com.forrestpruitt.psp;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class AudioManager
{
	public static Audio bounce1;
	public static Audio bounce2;
	public Audio win;

	public AudioManager()
	{
		init();
	}

	private void init()
	{
		try
		{
			bounce1 = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("/res/sound-bounce1.wav"));
			bounce2 = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("/res/sound-bounce2.wav"));
			win = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("/res/win.wav"));
		}
		catch (IOException e)
		{
			System.out.println("Failed to laod audio file.");
			e.printStackTrace();
		}

	}

	public void playSound(String soundName)
	{
		switch (soundName)
		{
		case "bounce1":
		{
			bounce1.playAsSoundEffect(1, 1, false);
			break;
		}
		case "bounce2":
		{
			bounce2.playAsSoundEffect(1, 1, false);
			break;
		}
		case "score":
		{
			win.playAsSoundEffect(1, 1, false);
			break;
		}
		}
	}
}
