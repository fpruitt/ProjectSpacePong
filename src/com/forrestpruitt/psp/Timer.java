package com.forrestpruitt.psp;

import org.lwjgl.Sys;

public class Timer
{
	long lastFps, lastFrame, fps;

	public void updateFPS()
	{
		if (getTime() - lastFps > 1000)
		{
			fps = 0;
			lastFps += 1000;
		}
		fps++;
	}

	/**
	 * Get the number of milliseconds since the last frame.
	 * 
	 * @return the number of milliseconds since the last frame.
	 */
	public int getDelta()
	{
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	public long getTime()
	{
		return ((Sys.getTime() * 1000) / Sys.getTimerResolution());
	}

	public long getFPS()
	{
		return fps;
	}
}
