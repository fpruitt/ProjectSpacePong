package com.forrestpruitt.psp;

import java.util.LinkedList;

import org.lwjgl.input.Keyboard;

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
		pollInput();
	}

	public void pollInput()
	{
		// If this is a single player game, only poll for the bottom paddle.
		int wallColliding = checkWallCollisions(); // -1 = left wall, 0 = no
													// wall, 1 = right wall
		if (this.tag == "bottomPaddle")
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			{
				if (wallColliding != -1)
				{
					this.setX(this.getX() - SHIP_SPEED);
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			{
				if (wallColliding != 1)
				{
					System.out.println("Moving right...");
					this.setX(this.getX() + SHIP_SPEED);
				}
			}

			// Make sure ship is still within bounds, fix if not.
			if (this.getX() < 0)
				this.setX(0);
			else if (this.getX() > Game.SCREEN_WIDTH - this.width)
				this.setX(Game.SCREEN_WIDTH - this.width);
		}
		else if (this.tag == "topPaddle" && Game.NUM_PLAYERS == 2)
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
			{
				if (wallColliding != -1)
				{
					this.setX(this.getX() - SHIP_SPEED);
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				if (wallColliding != 1)
				{
					this.setX(this.getX() + SHIP_SPEED);
				}
			}

			// Make sure ship is still within bounds, fix if not.
			if (this.getX() < 0)
				this.setX(0);
			else if (this.getX() > Game.SCREEN_WIDTH - this.width)
				this.setX(Game.SCREEN_WIDTH - this.width);
		}
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
			String thisTag = this.getTag();
			//If the object is intersecting this paddle and it is a wall
			if(object.isIntersecting(this) && (thisTag.equalsIgnoreCase("leftWall") || thisTag.equalsIgnoreCase("rightWall")));
			{
				if(thisTag.equalsIgnoreCase("leftWall"))
				{
					return -1;
				}
				else if (thisTag.equalsIgnoreCase("rightWall"))
					return 1;
			}
		}
		return 0;
	}

}
