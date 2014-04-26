package com.forrestpruitt.psp;

import java.util.LinkedList;

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

	private LinkedList<GameObject> currentColliding = new LinkedList<GameObject>();

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

	/**
	 * 
	 * @return -1 if colliding with left wall, 1 if colliding with right wall, 0
	 *         otherwise.
	 */
	private int checkWallCollisions()
	{
		for(GameObject object : ObjectManager.objects)
		{
			String thisTag = object.getTag();
			//If the object is intersecting this paddle and it is a wall
			if(object.isIntersecting(this) && (thisTag.equalsIgnoreCase("leftWall") || thisTag.equalsIgnoreCase("rightWall")));
			{
				if(thisTag.equalsIgnoreCase("leftWall"))
				{
					return -1;
				}
				else
					return 1;
			}
		}
		return 0;
	}

}
