package com.forrestpruitt.psp;

import java.io.IOException;

import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Powerup extends GameObject
{
	public String powerupType;
	public int direction;
	private static final float speed = 1f;
	private boolean isActive;
	private int framesSinceActivated = 0;
	protected final int LENGTH_OF_EFFECT = 60; // roughly 10 seconds, 60 fps
	


	public Powerup(int id, String tag, String spriteLocation, String powerupType, float direction, float x, float y)
	{
		super(id, tag);
		this.powerupType = powerupType;
		this.direction = (int) direction;
		this.setX(x);
		this.setY(y);
		this.setWidth(25);
		this.setHeight(25);
		
		try
		{
			setTexture(TextureLoader.getTexture("PNG",
 ResourceLoader.getResourceAsStream(spriteLocation)));
			System.out.println("Loaded texture for " + tag);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	public void update(float delta)
	{
		setY(getY() + direction * speed);
		if (getY() >= Game.SCREEN_FIELD_HEIGHT || getY() <= 0)
		{
			// Powerup was missed, disable it so it'll disappear
			this.enabled = false;
		}

		checkCollisions();
	}

	private void checkCollisions()
	{
		for(GameObject object : ObjectManager.objects)
		{
			if (object.isIntersecting(this) && (object.getTag().equalsIgnoreCase("bottomPaddle") || object.getTag().equalsIgnoreCase("topPaddle")))
			{
				framesSinceActivated++;
				if (powerupType.equalsIgnoreCase("bigPaddle"))
				{
					object.activateLargePowerup();
					this.enabled = false;
				}
				else if (powerupType.equalsIgnoreCase("littlePaddle"))
				{
					object.activateSmallPowerup();
					this.enabled = false;
				}
			}
		}
	}


}
