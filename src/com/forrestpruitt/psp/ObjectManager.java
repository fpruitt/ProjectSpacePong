package com.forrestpruitt.psp;

import java.util.LinkedList;

public class ObjectManager
{
	// Global Collections of Objects
	static LinkedList<GameObject> objects = new LinkedList<GameObject>();
	static LinkedList<GameObject> enemies = new LinkedList<GameObject>();

	public ObjectManager()
	{
		initObjects();
	}

	private static void initObjects()
	{
		// Create the two paddles
		PlayerPaddle bottomPaddle = new PlayerPaddle(0, "bottomPaddle",
				"/res/white.png");
		bottomPaddle.setY(Game.SCREEN_HEIGHT - 10);
		bottomPaddle.setX(Game.SCREEN_WIDTH / 2 + bottomPaddle.width / 2);

		PlayerPaddle topPaddle = new PlayerPaddle(1, "topPaddle",
				"/res/white.png");
		topPaddle.setX(Game.SCREEN_WIDTH / 2 + topPaddle.width / 2);
		topPaddle.setY(5);

		// Create the walls
		GameObject leftWall = new GameObject(2, "leftWall");
		leftWall.setWidth(10);
		leftWall.setHeight(Game.SCREEN_HEIGHT);
		leftWall.setX(0);
		leftWall.setY(0);
		leftWall.initTexture("/res/white.png");
		GameObject rightWall = new GameObject(3, "rightWall");
		rightWall.setWidth(10);
		rightWall.setHeight(Game.SCREEN_HEIGHT);
		rightWall.setX(Game.SCREEN_WIDTH - 10);
		rightWall.setY(0);
		rightWall.initTexture("/res/white.png");

		// Add Objects to list
		objects.add(bottomPaddle);
		objects.add(topPaddle);
		objects.add(leftWall);
		objects.add(rightWall);
	}

	public void drawObjects()
	{

	}

}
