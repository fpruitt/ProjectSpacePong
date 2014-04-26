package com.forrestpruitt.psp;

import java.awt.Rectangle;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class GameObject
{
	Rectangle box;
	private Texture texture;

	// construct an Entity whose box and texture is 'width' pixels
	public GameObject(int width) throws IOException {

	}

	// draw textured quad at the rectangle coordinates
	public void draw()
	{

		float x = (float) box.getX();
		float y = (float) box.getY();
		float width = (float) box.getWidth();
		float height = (float) box.getHeight();

		// make the loaded texture the active texture for the OpenGL context
		// texture.bind();

		// going to send a series of quad vertices...
		GL11.glBegin(GL11.GL_QUADS);

		// top-left of texture tied to top-left of box
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y);

		// top-right
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x + width, y);

		// bottom-right
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x + width, y + height);

		// bottom-left
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y + height);

		GL11.glEnd();
	}
}