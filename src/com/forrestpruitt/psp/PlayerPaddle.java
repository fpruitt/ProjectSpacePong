package com.forrestpruitt.psp;

import java.util.LinkedList;

import org.lwjgl.input.Keyboard;

public class PlayerPaddle extends GameObject
{
	// Constants for player ships
	private final int SHIP_SPEED = 40;
	private final float SHIP_WIDTH = 100;
	private final float SHIP_HEIGHT = 15;
	private final int MAX_SHOTS = 2; // The maximum number of shots allowed on screen from this ship at once.
	// Parameters for AI
	public boolean isComputer;
	private final float GAME_DIFFICULTY = 10.0f;
	private int framesSinceMovement = 0; // Used to restrict how quickly computer can make decisions.
	private int lastMove = 0;
	private int AI_DECISION_LAPSE = 15;
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
			if (framesSinceMovement > AI_DECISION_LAPSE)
			{
				float centerOfPaddle = getX() + (getWidth() / 2);
				if (Ball.yDirection * Ball.ballSpeed > 0)
				{
					if (centerOfPaddle < (int) ObjectManager.ball.getX())
					{
						setX(getX() + SHIP_SPEED / GAME_DIFFICULTY);
						lastMove = -1;
					}
					else if (centerOfPaddle > (int) ObjectManager.ball.getX())
					{
						setX(getX() - SHIP_SPEED / GAME_DIFFICULTY);
						lastMove = 1;
					}
					else
					{
						lastMove = 0;
					}
				}

				// Make sure ship is still within bounds, fix if not.
				if (this.getX() < 0)
					this.setX(0);
				else if (this.getX() > Game.SCREEN_WIDTH - this.width)
					this.setX(Game.SCREEN_WIDTH - this.width);
				framesSinceMovement = 0;
			}
			else
			// Move like last frame until framesSinceMovement reaches threshold
			{
				if (lastMove == -1)
				{
					setX(getX() + SHIP_SPEED / GAME_DIFFICULTY);

				}
				else if (lastMove == 1)
				{
					setX(getX() - SHIP_SPEED / GAME_DIFFICULTY);
				}
				if (this.getX() < 0)
				{
					this.setX(0);
					lastMove *= -1; // Reverse direction if hitting a wall.
				}
				else if (this.getX() > Game.SCREEN_WIDTH - this.width)
				{
					this.setX(Game.SCREEN_WIDTH - this.width);
					lastMove *= -1;
				}
				framesSinceMovement++;
			}
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
