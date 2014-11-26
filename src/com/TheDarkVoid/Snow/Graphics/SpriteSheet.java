package com.TheDarkVoid.Snow.Graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SpriteSheet
{
	private String path;
	public final int SIZE;
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int width, height;
	public int[] pixels;

	public static SpriteSheet tiles = new SpriteSheet("/textures/SpriteSheet.png", 256);
	public static SpriteSheet spawnLevel = new SpriteSheet("/textures/SpawnLevel.png", 128);
	public static SpriteSheet projectiles_wizard = new SpriteSheet("/textures/Projectiles/WizardProjectiles.png", 48);

	public static SpriteSheet player = new SpriteSheet("/textures/playerSprites.png", 128, 128);
	public static SpriteSheet player_down = new SpriteSheet(player, 0, 0, 1, 4, 32);
	public static SpriteSheet player_up = new SpriteSheet(player, 1, 0, 1, 4, 32);
	public static SpriteSheet player_right = new SpriteSheet(player, 2, 0, 1, 4, 32);
	public static SpriteSheet player_left = new SpriteSheet(player, 3, 0, 1, 4, 32);

	public static SpriteSheet dummy = new SpriteSheet("/textures/dummySprites.png", 128, 128);
	public static SpriteSheet dummy_down = new SpriteSheet(dummy, 0, 0, 1, 4, 32);
	public static SpriteSheet dummy_up = new SpriteSheet(dummy, 1, 0, 1, 4, 32);
	public static SpriteSheet dummy_right = new SpriteSheet(dummy, 2, 0, 1, 4, 32);
	public static SpriteSheet dummy_left = new SpriteSheet(dummy, 3, 0, 1, 4, 32);

	public static SpriteSheet chaser = new SpriteSheet("/textures/chaserSprites.png", 128, 128);
	public static SpriteSheet chaser_down = new SpriteSheet(chaser, 0, 0, 1, 4, 32);
	public static SpriteSheet chaser_up = new SpriteSheet(chaser, 1, 0, 1, 4, 32);
	public static SpriteSheet chaser_right = new SpriteSheet(chaser, 2, 0, 1, 4, 32);
	public static SpriteSheet chaser_left = new SpriteSheet(chaser, 3, 0, 1, 4, 32);

	public Sprite[] sprites;

	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize)
	{
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int h = height * spriteSize;
		int w = width * spriteSize;
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		if(SPRITE_WIDTH == SPRITE_HEIGHT)
			SIZE = w;
		else
			SIZE = -1;
		pixels = new int[w * h];
		for(int y0 = 0; y0 < h; y0++)
		{
			int yp = yy + y0;
			for(int x0 = 0; x0 < w; x0++)
			{
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
			}
		}
		int frame = 0;
		sprites = new Sprite[width * height];
		for(int ya = 0; ya < height; ya++)
		{
			for(int xa = 0; xa < width; xa++)
			{
				int[] spritePixels = new int[spriteSize * spriteSize];
				for(int y0 = 0; y0 < spriteSize; y0++)
				{
					for(int x0 = 0; x0 < spriteSize; x0++)
					{
						//System.err.println(spritePixels.length + ":" + pixels.length);
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}

	public SpriteSheet(String path, int size)
	{
		this.path = path;
		SIZE = size;
		SPRITE_WIDTH = size;
		SPRITE_HEIGHT = size;
		pixels = new int[SIZE * SIZE];
		Load();
	}

	public SpriteSheet(String path, int width, int height)
	{
		this.path = path;
		SIZE = -1;
		SPRITE_WIDTH = width;
		SPRITE_HEIGHT = height;
		pixels = new int[width * height];
		Load();
	}

	public Sprite[] GetSprites()
	{
		return sprites;
	}

	public int GetWidth()
	{
		return width;
	}

	public int GetHeight()
	{
		return height;
	}

	public int[] GetPixels()
	{
		return pixels;
	}

	private void Load()
	{
		try
		{
			System.out.print("Loading: " + path + "...");
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			System.out.println(" Success!");
		} catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Load Failed...");
		}
	}

}
