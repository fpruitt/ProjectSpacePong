package com.forrestpruitt.psp;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GameObject
{
	protected int id;
	protected String tag;
	protected float x, lastX, y, lastY, width, height;
	protected boolean drawable, enabled;
	protected Texture texture;
	protected Rectangle2D.Float box;
	protected int timeSincePowerup = 0;
	protected String currentPowerup = "";
	protected int powerupLength = 300;
	/**
	 * Most basic constructor, construct with just an id and tag.
	 * 
	 * @param id
	 *            an ideally unique value to identify this object.
	 * @param tag
	 *            a string representation of this object.
	 */
	public GameObject(int id, String tag) {

		this.id = id;
		this.tag = tag;
		box = new Rectangle2D.Float();
		enabled = true;
	}

	// SETTERS
	public void setX(float x)
	{
		this.lastX = this.x;
		this.x = x;
		box.x = x;
	}

	public void setY(float y)
	{
		this.lastY = this.y;
		this.y = y;
		box.y = y;
	}

	public void setWidth(float width)
	{
		this.width = width;
		this.box.width = width;
	}

	public void setHeight(float height)
	{
		this.height = height;
		this.box.height = height;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	// GETTERS
	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

	public int getID()
	{
		return id;
	}

	public String getTag()
	{
		return tag;
	}

	public Rectangle2D.Float getBox()
	{
		return this.box;
	}

	public boolean isIntersecting(GameObject otherObject)
	{
		if (otherObject.getBox().intersects(this.box))
			return true;
		else
			return false;
	}

	public void draw()
	{
		// System.out.println("Drawing " + tag + "at x=" + x + ", y= " + y);
		float x = (float) box.getX();
		float y = (float) box.getY();
		float width = (float) box.getWidth();
		float height = (float) box.getHeight();

		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		// make the loaded texture the active texture for the OpenGL context
		texture.bind();

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0); // top-left
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(1, 0); // top-right
		GL11.glVertex2f(x + width, y);
		GL11.glTexCoord2f(1, 1); // bottom-right
		GL11.glVertex2f(x + width, y + height);
		GL11.glTexCoord2f(0, 1); // bottom-left
		GL11.glVertex2f(x, y + height);

		GL11.glEnd();
	}

	protected void initTexture(String textureLocation)
	{
		try
		{
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream(textureLocation));
			System.out.println("Loaded texture for " + tag);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	protected void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	public void update(float delta)
	{

	}

	public void activateSmallPowerup()
	{
		if (currentPowerup.equals(""))
		{
			currentPowerup = "small";
			timeSincePowerup = 0;
			setWidth(getWidth() / 2f);
		}
	}

	public void activateLargePowerup()
	{
		if (currentPowerup.equals(""))
		{
			currentPowerup = "big";
			timeSincePowerup = 0;
			setWidth(getWidth() * 2f);
		}
	}

	// debugging method
	protected void printCoords()
	{
		System.out.println("Object with tag " + tag + " is currently at x=" + getX() + ", y=" + getY() + ".");
	}

}