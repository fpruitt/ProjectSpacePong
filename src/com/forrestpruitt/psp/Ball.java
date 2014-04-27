package com.forrestpruitt.psp;

import java.util.Random;

public class Ball extends GameObject
{
	private final float BALL_WIDTH = 25;
	private final float BALL_HEIGHT = 25;
	// Sweet spots on paddle to check--these are threshold values
	// |_ __ ____ __ _|
	private final float SPOT_ONE = -1f;
	private final float SPOT_TWO = -.5f;
	private final float SPOT_THREE = 0f;
	private final float SPOT_FOUR = .5f;
	private final float SPOT_FIVE = 1f;
	private float ballSpeed = 20;
	private float xDirection; // -1 to 1, -1=left, 1=right
	private float yDirection; // -1 to 1, -1=down, 1=up
	private Random generator = new Random();
	public Ball(int id, String tag, String textureLocation)
	{
		super(id, tag);
		this.setWidth(BALL_WIDTH);
		this.setHeight(BALL_HEIGHT);
		this.initTexture(textureLocation);
		reset(); // Set ball to it's initial position and direction
	}

	// Reset the ball's location and direction to initial parameters.
	public void reset()
	{
		// Set ball in middle of the screen.
		setX(Game.SCREEN_WIDTH / 2 - getWidth() / 2);
		setY(Game.SCREEN_HEIGHT / 2 - getHeight() / 2);
		// Randomly decided which way the ball starts
		yDirection = generator.nextInt(2); // Generates either a 0 or a 1
		assert (yDirection >= -1 && yDirection <= 1);
		yDirection = (yDirection == 0) ? -1 : 1; // Adjusts to either -1 or 1
		xDirection = 0; // Ball starts going either up or down, no left-right movement initially!
	}
	
	public void update(float delta)
	{
		setX(getX() + xDirection * (ballSpeed * delta));
		setY(getY() + yDirection * (ballSpeed * delta));
		if (getY() >= Game.SCREEN_HEIGHT - getHeight())
		{
			// Bottom paddle scores
			reset();
			// TODO: Add logic for scoring
		}
		if (getY() <= 0)
		{
			// Top paddle scores
			reset();
			// TODO: Add logic for scoring
		}
		checkCollisions();
	}

	private void checkCollisions()
	{
		for (GameObject object : ObjectManager.objects)
		{
			String thisTag = object.getTag();
			// System.out.println("Checking for hits on " + thisTag);
			// If the object is intersecting this paddle and it is a wall
			if (object.isIntersecting(this) && (thisTag.equalsIgnoreCase("leftWall") || thisTag.equalsIgnoreCase("rightWall")))
			{
				xDirection *= -1;
			}
			//If the ball is hitting a paddle
			if (object.isIntersecting(this) && (thisTag.equalsIgnoreCase("bottomPaddle") || thisTag.equalsIgnoreCase("topPaddle")))
			{

				// Decide where on the paddle the ball is hitting
				// First, find out how large each of the 10 chunks need to be
				float chunkSize = object.getWidth() / 10;
				// Get ball offset on paddle
				float paddleOffset = getX() - object.getX();
				// Figure out which 'chunk' the ball is in, and therefore how it should bounce.
				if (paddleOffset <= 1 * chunkSize)
				{
					System.out.println("Collision with left-most zone of paddle.");
					xDirection = SPOT_ONE;
					yDirection *= -1;
				}
				else if (paddleOffset > 1 * chunkSize && paddleOffset <= 4 * chunkSize)
				{
					System.out.println("Collision with left-of-center zone of paddle.");
					xDirection = SPOT_TWO;
					yDirection *= -1;
				}
				else if (paddleOffset > 4 * chunkSize && paddleOffset <= 6 * chunkSize)
				{
					System.out.println("Collision with center zone of paddle.");
					xDirection = SPOT_THREE;
					yDirection *= -1;
				}
				else if (paddleOffset > 7 * chunkSize && paddleOffset <= 9 * chunkSize)
				{
					System.out.println("Collision with right-of-center zone of paddle.");
					xDirection = SPOT_FOUR;
					yDirection *= -1;
				}
				else if (paddleOffset > 9 * chunkSize)
				{
					System.out.println("Collision with right-most zone of paddle.");
					xDirection = SPOT_FIVE;
					yDirection *= -1;
				}
			}
		}
	}


}
