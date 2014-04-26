package com.forrestpruitt.psp;

public class PlayerPaddle extends GameObject
{
	// Constants for player ships
	private final int SHIP_SPEED = 5;
	private final float SHIP_WIDTH = 70;
	private final float SHIP_HEIGHT = 25;
	private final int MAX_SHOTS = 2; // The maximum number of shots allowed on
										// screen from this ship at once.
	private int currentShotsFired = 0;
	private int timeSinceLastShot = 60;

	public PlayerPaddle(int id, String tag, String spriteLocation)
	{
		super(id, tag);
		this.setWidth(SHIP_WIDTH);
		this.setHeight(SHIP_HEIGHT);
		this.initTexture(spriteLocation);
	}

	public void update()
	{

	}


}
