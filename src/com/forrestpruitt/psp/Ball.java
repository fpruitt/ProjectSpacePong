package com.forrestpruitt.psp;

import java.util.Random;

public class Ball extends GameObject
{
	private final float BALL_WIDTH = 25;
	private final float BALL_HEIGHT = 25;
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
		printCoords();
		System.out.println(getY() + "+" + yDirection + "*" + ballSpeed + "+" + delta);
		setX(getX() + xDirection * (ballSpeed * delta));
		setY(getY() + yDirection * (ballSpeed * delta));
		printCoords();
	}


}
