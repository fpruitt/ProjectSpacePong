package com.forrestpruitt.psp;

import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game
{
	public final static int SCREEN_WIDTH = 800;
	public final static int SCREEN_HEIGHT = 600;
	public final static int SCREEN_MENU_HEIGHT = 50;
	public final static int SCREEN_FIELD_HEIGHT = SCREEN_HEIGHT - SCREEN_MENU_HEIGHT;

	public static int player1Score = 0;
	public static int player2Score = 0;
	public static final int GOAL_POINT_VALUE = 200;
	public static final int BOUNCE_POINT_VALUE = 5;
	public static final int ALIEN_POINT_VALUE = 10;
	public static int scoreMultiplier = 1;
	public static int hitsWithoutDying = 14;

	public static int enemiesRemaining;

	public static int NUM_PLAYERS;
	public final String GAME_NAME = "Project Space Pong";
	public static AudioManager audio;
	ObjectManager manager;



	public static Timer timer;

	public Game(int numPlayers)
	{
		NUM_PLAYERS = numPlayers + 1; // Offset zero index to 1 index, ie 1 or 2
										// players
	}

	public void start()
	{
		timer = new Timer();


		// initialize opengl context
		try
		{
			initGL(SCREEN_WIDTH, SCREEN_HEIGHT);
		}
		catch (LWJGLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		audio = new AudioManager();
		manager = new ObjectManager();
		GUIManager.init();

		// standard game loop
		while (!Display.isCloseRequested())
		{
			update();
			Display.sync(60);
			Display.update();
		}

		Display.destroy();
		AL.destroy();
		System.exit(0);
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
		GL11.glOrtho(0, width, 0, height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void update()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		timer.tick();
		Display.setTitle(GAME_NAME + "   FPS: " + timer.getFPS());
		manager.updateObjects();
		handleScoreMultiplier();
		GUIManager.update();

	}

	/**
	 * Increase score multiplier if players have reached threshold
	 */
	private void handleScoreMultiplier()
	{
		// System.out.println("Hits without dying: " + hitsWithoutDying);
		if (hitsWithoutDying <= 15)
		{
			scoreMultiplier = 1;
			GUIManager.updateMultiplier(1);
		}
		if (scoreMultiplier == 1)
		{
			if (hitsWithoutDying > 15)
			{
				scoreMultiplier = 2;
				GUIManager.updateMultiplier(2);
			}
		}
		else if (scoreMultiplier == 2)
		{
			if (hitsWithoutDying > 30)
			{
				scoreMultiplier = 3;
				GUIManager.updateMultiplier(3);
			}
		}
		else if (scoreMultiplier == 3)
		{
			if (hitsWithoutDying > 50)
			{
				scoreMultiplier = 4;
				GUIManager.updateMultiplier(4);
			}
		}
	}

	public void startOver()
	{
		player1Score = 0;
		player2Score = 0;
		scoreMultiplier = 1;
		hitsWithoutDying = 0;

		Object[] options =
		{ "Single Player", "Multiplayer" };
		JDialog dialog = new JDialog();
		dialog.setTitle("How Many Players?");
		int numPlayers = JOptionPane.showOptionDialog(dialog, "Will this be a single player or two-player game?", "A Silly Question",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		NUM_PLAYERS = numPlayers + 1;

		ObjectManager.initObjects();
	}
	public static void main(String[] args) throws IOException, LWJGLException
	{
		/*
		 * JDialog dialog = new JDialog(); dialog.setTitle("How Many Players?");
		 * dialog.setSize(300, 200); dialog.setLocationRelativeTo(null); JLabel
		 * prompt = new JLabel(
		 * "Will this be a single player or two player game?"); JButton single =
		 * new JButton("Single Player"); JButton multi = new
		 * JButton("Multiplayer"); dialog.add(prompt); dialog.add(single);
		 * dialog.add(multi); dialog.setVisible(true);
		 */
		Object[] options =
		{ "Single Player", "Multiplayer" };
		JDialog dialog = new JDialog();
		dialog.setTitle("How Many Players?");
		int numPlayers = JOptionPane.showOptionDialog(dialog,
				"Will this be a single player or two-player game?",
				"A Silly Question", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		// If numPlayers is 0, single player game will be launched.
		// Otherwise, no AI will be started for top paddle and two players
		// will be playing.
		Game gamebase = new Game(numPlayers);
		gamebase.start();
	}

}