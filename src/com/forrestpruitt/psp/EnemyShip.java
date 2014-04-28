package com.forrestpruitt.psp;

public class EnemyShip extends GameObject
{
	private final int SHIP_SPEED = 20;
	public static final float SHIP_WIDTH = 32;
	public static final float SHIP_HEIGHT = 32;

	private int direction;

	public EnemyShip(int id, String tag, String textureLocation)
	{
		super(id, tag);
		// TODO Auto-generated constructor stub
		setWidth(SHIP_WIDTH);
		setHeight(SHIP_HEIGHT);
		initTexture(textureLocation);
	}

	public void setDirection(int direction)
	{
		assert (direction == -1 || direction == 1);
		this.direction = direction;
	}

	public void update()
	{

	}

}
