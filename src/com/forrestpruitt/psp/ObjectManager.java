package com.forrestpruitt.psp;

import java.util.LinkedList;

import org.lwjgl.opengl.GL11;

public class ObjectManager
{
	// Global Collections of Objects
	static LinkedList<GameObject> objects = new LinkedList<GameObject>();
	static LinkedList<GameObject> enemies = new LinkedList<GameObject>();
	private final static int PADDLE_HEIGHT_OFFSET = 5;
	private final static int WALL_WIDTH = 10;

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
		// Make Invaders
		initEnemies();
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
		doRemove();
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

	private static void initEnemies()
	{
		float xMargins = 2 * EnemyShip.SHIP_WIDTH;
		float yMargins = 2 *EnemyShip.SHIP_WIDTH;
		float workingRoomY = Game.SCREEN_FIELD_HEIGHT - yMargins;
		float workingRoomX = Game.SCREEN_WIDTH - xMargins;
		float columnsOfInvaders = 7;
		float rowsOfInvaders = 5;
		// Shipwidth * columns is minimum room needed to fit all invaders on the screen
		// there is extra room; we want to space the invaders out evenly in this room.
		// working room minus minimum room is the amount of space extra
		// spaces between invaders is equal to number of invaders
		// extra space divided by number of spaces between invaders is space between invaders
		float xSpaceBetweenInvaders = ((workingRoomX - (EnemyShip.SHIP_WIDTH * columnsOfInvaders)) / columnsOfInvaders);
		float ySpaceBetweenInvaders = ((workingRoomY - (EnemyShip.SHIP_HEIGHT * rowsOfInvaders)) / rowsOfInvaders) / 2f;
		System.out.println(xSpaceBetweenInvaders + "," + ySpaceBetweenInvaders);
		// place invaders
		for (int i = 0; i < rowsOfInvaders; i++)
		{
			for (int j = 0; j < columnsOfInvaders; j++)
			{
				if (j != 3)
				{// TODO: NOT HARDCODED VALUE
					EnemyShip ship = new EnemyShip(100, "enemy", "/res/enemy.png");
					// Set the direction of the ship; the bottom rows go towards bottom player, top rows towards top player
					if (i < (rowsOfInvaders / 2))
						ship.setDirection(-1);
					else
						ship.setDirection(1);
					// x = width * column # + xSpaceBetweenInvaders + x margin/2
					ship.setX((j * ship.getWidth()) + xSpaceBetweenInvaders * j + (xMargins));
					ship.setY((yMargins * 2) + (i * EnemyShip.SHIP_HEIGHT) + ySpaceBetweenInvaders * i);
					ship.printCoords();
					objects.add(ship);
				}
			}
		}
	}

}
