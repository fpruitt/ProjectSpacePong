package com.forrestpruitt.psp;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game
{
	public final static int SCREEN_WIDTH = 800;
	public final static int SCREEN_HEIGHT = 600;
	public final String GAME_NAME = "Project Space Pong";

	// Game Objects
	PlayerPaddle player;
	GameObject leftWall;
	GameObject rightWall;

	Timer timer;

	public void start()
	{
		timer = new Timer();

		// initialize opengl context
		initGL(SCREEN_WIDTH, SCREEN_HEIGHT);

		// standard game loop
		while (!Display.isCloseRequested())
		{
			update();
			rednerGL();
			Display.sync(60);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			Display.update();
		}

		Display.destroy();

	}




	public static void initGL(int width, int height) throws LWJGLException
	{

		// open window of appropriate size
		Display.setDisplayMode(new DisplayMode(width, height));
		Display.create();
		Display.setVSyncEnabled(true);

		// enable 2D textures
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		// set "clear" color to black
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// set viewport to entire window
		GL11.glViewport(0, 0, width, height);

		// set up orthographic projectionr
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void update()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		timer.updateFPS();
		Display.setTitle(GAME_NAME + "   FPS: " + timer.getFPS());
	}

	public static void main(String[] args) throws IOException, LWJGLException
	{
		Game gamebase = new Game();
		gamebase.start();
	}

}