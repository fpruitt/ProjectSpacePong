package com.forrestpruitt.psp;

import org.lwjgl.Sys;

public class Timer
{
	long lastFps, lastFrame, fps;
	float currentDelta = 0;

	/**
	 * Update the frames per second for this tick, as well as set this frame's delta.
	 */
	public void tick()
	{
		// Update FPS Clock
		long time = getTime();
		if (time - lastFps > 1000)
		{
			fps = 0;
			lastFps += 1000;
		}
		fps++;

		int delta = (int) (time - lastFrame);
		lastFrame = time;
		System.out.println("Time since last frame: " + currentDelta);
		currentDelta = (float) delta / 100;
	}

	/**
	 * Get the time since the last frame in seconds.
	 * 
	 * @return the number of milliseconds since the last frame.
	 */
	public float getDelta()
	{
		System.out.println("returning current delta of" + currentDelta);
		if (currentDelta > .2)
			return .002f;
		return currentDelta;
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
