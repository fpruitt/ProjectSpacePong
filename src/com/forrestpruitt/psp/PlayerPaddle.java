package com.forrestpruitt.psp;

import java.util.LinkedList;

import org.lwjgl.input.Keyboard;

public class PlayerPaddle extends GameObject
{
	// Constants for player ships
	private final float GAME_DIFFICULTY = 1.0f;
	private final int SHIP_SPEED = 40;
	private final float SHIP_WIDTH = 100;
	private final float SHIP_HEIGHT = 15;
	private final int MAX_SHOTS = 2; // The maximum number of shots allowed on
										// screen from this ship at once.
	public boolean isComputer;

	private LinkedList<GameObject> currentColliding = new LinkedList<GameObject>();

	public PlayerPaddle(int id, String tag, String spriteLocation)
	{
		super(id, tag);
		this.setWidth(SHIP_WIDTH);
		this.setHeight(SHIP_HEIGHT);
		this.initTexture(spriteLocation);
		isComputer = (Game.NUM_PLAYERS == 1) ? true : false;
	}

	public void update(float delta)
	{
		pollInput(delta);
	}

	public void pollInput(float delta)
	{
		// There is a more robust way to handle this, but let's do it simply for now..
		// If this is a single player game, only poll for the bottom paddle.
		int wallColliding = checkWallCollisions(); // -1 = left wall, 0 = no wall, 1 = right wall
		if (this.tag == "bottomPaddle")
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			{
				if (wallColliding != -1)
				{
					this.setX(this.getX() - (SHIP_SPEED * delta));
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			{
				if (wallColliding != 1)
				{
					this.setX(this.getX() + (SHIP_SPEED * delta));
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
					this.setX(this.getX() - (SHIP_SPEED * delta));
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				if (wallColliding != 1)
				{
					this.setX(this.getX() + (SHIP_SPEED * delta));
				}
			}

			// Make sure ship is still within bounds, fix if not.
			if (this.getX() < 0)
				this.setX(0);
			else if (this.getX() > Game.SCREEN_WIDTH - this.width)
				this.setX(Game.SCREEN_WIDTH - this.width);
		}
		//Paddle AI
		else if (this.tag == "topPaddle" && Game.NUM_PLAYERS == 1)
		{
			
			float centerOfPaddle = getX() + (getWidth()/2);
			if(Ball.yDirection * Ball.ballSpeed > 0)
			{
				if (centerOfPaddle < ObjectManager.ball.getX())
				{
					setX(getX() + SHIP_SPEED / GAME_DIFFICULTY);
				}
				else if (centerOfPaddle > ObjectManager.ball.getX())
				{
					setX(getX() - SHIP_SPEED / GAME_DIFFICULTY);
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
