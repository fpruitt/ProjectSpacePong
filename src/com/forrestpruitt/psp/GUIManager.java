package com.forrestpruitt.psp;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GUIManager
{
	// Initialize Score Objects and add Textures
	public static GameObject p1S0 = new GameObject(-1, "scoreP1C0");
	public static GameObject p1S1 = new GameObject(-1, "scoreP1C1");
	public static GameObject p1S2 = new GameObject(-1, "scoreP1C2");
	public static GameObject p1S3 = new GameObject(-1, "scoreP1C3");
	public static GameObject p1S4 = new GameObject(-1, "scoreP1C4");
	public static GameObject p1S5 = new GameObject(-1, "scoreP1C5");
	public static GameObject p1S6 = new GameObject(-1, "scoreP1C6");
	public static GameObject p1S7 = new GameObject(-1, "scoreP1C7");
	public static GameObject p1S8 = new GameObject(-1, "scoreP1C8");
	public static GameObject p1S9 = new GameObject(-1, "scoreP1C9");

	public static GameObject p2S0 = new GameObject(-1, "scoreP2C0");
	public static GameObject p2S1 = new GameObject(-1, "scoreP2C1");
	public static GameObject p2S2 = new GameObject(-1, "scoreP2C2");
	public static GameObject p2S3 = new GameObject(-1, "scoreP2C3");
	public static GameObject p2S4 = new GameObject(-1, "scoreP2C4");
	public static GameObject p2S5 = new GameObject(-1, "scoreP2C5");
	public static GameObject p2S6 = new GameObject(-1, "scoreP2C6");
	public static GameObject p2S7 = new GameObject(-1, "scoreP2C7");
	public static GameObject p2S8 = new GameObject(-1, "scoreP2C8");
	public static GameObject p2S9 = new GameObject(-1, "scoreP2C9");

	public static ArrayList<GameObject> player1Score = new ArrayList<GameObject>();
	public static ArrayList<GameObject> player2Score = new ArrayList<GameObject>();
	public static ArrayList<Texture> textures = new ArrayList<Texture>();

	private static final float CHAR_SIZE = 16; // Width and height the same
	private static final float OFFSET_FROM_SIDE = 16;
	private static final float OFFSET_FROM_BOTTOM = 4;


	public static void init()
	{
		player1Score.add(p1S0);
		player1Score.add(p1S1);
		player1Score.add(p1S2);
		player1Score.add(p1S3);
		player1Score.add(p1S4);
		player1Score.add(p1S5);
		player1Score.add(p1S6);
		player1Score.add(p1S7);
		player1Score.add(p1S8);
		player1Score.add(p1S9);

		player2Score.add(p2S0);
		player2Score.add(p2S1);
		player2Score.add(p2S2);
		player2Score.add(p2S3);
		player2Score.add(p2S4);
		player2Score.add(p2S5);
		player2Score.add(p2S6);
		player2Score.add(p2S7);
		player2Score.add(p2S8);
		player2Score.add(p2S9);
		

		

		for(int i = 0; i < 8; i++)
		{
			player1Score.get(i).setWidth(CHAR_SIZE);
			player1Score.get(i).setHeight(CHAR_SIZE);
			player1Score.get(i).setX(CHAR_SIZE*i + OFFSET_FROM_SIDE);
			player1Score.get(i).setY(Game.SCREEN_HEIGHT - (Game.SCREEN_MENU_HEIGHT - OFFSET_FROM_BOTTOM));
			player2Score.get(i).setWidth(CHAR_SIZE);
			player2Score.get(i).setHeight(CHAR_SIZE);
			player2Score.get(i).setX(Game.SCREEN_WIDTH - OFFSET_FROM_SIDE - (CHAR_SIZE * (8 - i)));
			player2Score.get(i).setY(Game.SCREEN_HEIGHT - (Game.SCREEN_MENU_HEIGHT - OFFSET_FROM_BOTTOM));
		}

		for (int i = 0; i <= 9; i++)
		{
			try
			{
				Texture texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("/res/char_" + i + ".png"));
				textures.add(i, texture);
				System.out.println("Loaded texture for char " + i);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		resetScores();

	}

	public static void update()
	{
		ArrayList<String> numTextures1 = scoreToTextures(Game.player1Score);
		ArrayList<String> numTextures2 = scoreToTextures(Game.player2Score);
		assert (numTextures1.size() == 8);
		assert (numTextures2.size() == 8);
		for(int i = 0; i < 8; i++)
		{
			// player1Score.get(i).initTexture(numTextures1.get(i));
			// player2Score.get(i).initTexture(numTextures2.get(i));
			player1Score.get(i).setTexture(textures.get(Integer.parseInt(numTextures1.get(i))));
			player2Score.get(i).setTexture(textures.get(Integer.parseInt(numTextures2.get(i))));
			player1Score.get(i).draw();
			player2Score.get(i).draw();
		}

	}
	
	/**
	 * Sets all scores in player1Score and player2Score to 0.
	 */
	private static void resetScores()
	{
		for (int i = 0; i < 8; i++)
		{
			player1Score.get(i).setTexture(textures.get(0));
			player2Score.get(i).setTexture(textures.get(0));
		}
	}

	private static ArrayList<String> scoreToTextures(int numScore)
	{
		String score = Integer.toString(numScore);
		ArrayList<String> textureLocations = new ArrayList<String>();
		
		//Start by padding in number of zeroes necessary to make an 8-integer score
		int leadingZeroes = 8 - score.length(); 
		assert leadingZeroes > 0;
		for(int i = 0; i < leadingZeroes; i++)
		{
			//textureLocations.add("/res/char_0.png");
			textureLocations.add("0");

		}
		//map numbers to textures
		for (int i = 0; i < score.length(); i++)
		{
			textureLocations.add(score.substring(i, i + 1));
		}
		return textureLocations;
	}
}
