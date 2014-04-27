package com.forrestpruitt.psp;

import java.util.LinkedList;

import org.lwjgl.opengl.GL11;

public class ObjectManager
{
	// Global Collections of Objects
	static LinkedList<GameObject> objects = new LinkedList<GameObject>();
	static LinkedList<GameObject> enemies = new LinkedList<GameObject>();
	private final static int PADDLE_HEIGHT_OFFSET = 5;
	public static Ball ball = new Ball(3, "ball", "/res/white.png");
	public ObjectManager()
	{
		initObjects();
	}

	private static void initObjects()
	{
		// Create the two paddles
		PlayerPaddle bottomPaddle = new PlayerPaddle(0, "bottomPaddle", "/res/paddleBottom.png");
		bottomPaddle.setY(PADDLE_HEIGHT_OFFSET);
		bottomPaddle.setX(Game.SCREEN_WIDTH / 2 - bottomPaddle.width / 2);

		PlayerPaddle topPaddle = new PlayerPaddle(1, "topPaddle", "/res/paddleTop.png");
		topPaddle.setX(Game.SCREEN_WIDTH / 2 - bottomPaddle.width / 2);
		topPaddle.setY(Game.SCREEN_FIELD_HEIGHT - topPaddle.height
				- PADDLE_HEIGHT_OFFSET);

		// Create the walls
		GameObject leftWall = new GameObject(2, "leftWall");
		leftWall.setWidth(10);
		leftWall.setHeight(Game.SCREEN_FIELD_HEIGHT);
		leftWall.setX(0);
		leftWall.setY(0);
		leftWall.initTexture("/res/white.png");
		GameObject rightWall = new GameObject(3, "rightWall");
		rightWall.setWidth(10);
		rightWall.setHeight(Game.SCREEN_FIELD_HEIGHT);
		rightWall.setX(Game.SCREEN_WIDTH - 10);
		rightWall.setY(0);
		rightWall.initTexture("/res/white.png");

		// Make the ball


		// Add Objects to list
		objects.add(bottomPaddle);
		objects.add(topPaddle);
		objects.add(leftWall);
		objects.add(rightWall);
		// objects.add(ball);
	}

	// Logically update then draw each object
	public void updateObjects()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		for (GameObject object : objects)
		{
			object.update(Game.timer.getDelta());
			object.draw();
		}
		// Call static ball update seperately
		ball.update(Game.timer.getDelta());
		ball.draw();
	}

	public void doRemove()
	{
		for (int i = 0; i < objects.size(); i++)
		{
			if (!objects.get(i).enabled)
			{
				objects.remove(i);
			}
		}
	}

}
