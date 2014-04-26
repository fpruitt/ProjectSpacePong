package com.forrestpruitt.psp;

public class Ball extends GameObject
{
	private final float BALL_WIDTH = 25;
	private final float BALL_HEIGHT = 25;
	private float xDirection; // -1 to 1, -1=left, 1=right
	private float yDirection; // -1 to 1, -1=down, 1=up

	public Ball(int id, String tag, String textureLocation)
	{
		super(id, tag);
		this.setWidth(BALL_WIDTH);
		this.setHeight(BALL_WIDTH);
		this.initTexture(textureLocation);
	}



}
