package com.TheDarkVoid.Snow.Graphics;

public class Sprite
{
	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;

	public static Sprite voidSprite = new Sprite(16, 0x000000);

	//Spawn Level
	public static Sprite spawn_grass = new Sprite(16, 1, 1, SpriteSheet.spawnLevel);
	public static Sprite spawn_snow = new Sprite(16, 4, 1, SpriteSheet.spawnLevel);
	public static Sprite spawn_snowFlower = new Sprite(16, 7, 0, SpriteSheet.spawnLevel);
	public static Sprite spawn_grassFlower = new Sprite(16, 6, 0, SpriteSheet.spawnLevel);
	public static Sprite spawn_snowRock = new Sprite(16, 7, 1, SpriteSheet.spawnLevel);
	public static Sprite spawn_grassRock = new Sprite(16, 6, 1, SpriteSheet.spawnLevel);
	public static Sprite spawn_brick0 = new Sprite(16, 0, 3, SpriteSheet.spawnLevel);
	public static Sprite spawn_brick1 = new Sprite(16, 1, 3, SpriteSheet.spawnLevel);
	public static Sprite spawn_brick2 = new Sprite(16, 2, 3, SpriteSheet.spawnLevel);
	public static Sprite spawn_floor0 = new Sprite(16, 6, 2, SpriteSheet.spawnLevel);
	public static Sprite spawn_floor1 = new Sprite(16, 7, 2, SpriteSheet.spawnLevel);
	public static Sprite spawn_water = new Sprite(16, 3, 3, SpriteSheet.spawnLevel);

	//Mobs
	public static Sprite dummy = new Sprite(32, 0, 0, SpriteSheet.dummy);

	//Projectiles
	public static Sprite projectile_plasma = new Sprite(16, 0, 0, SpriteSheet.projectiles_wizard);

	//Particles
	public static Sprite particle_basic = new Sprite(2, 0xaaffff);

	protected Sprite(SpriteSheet sheet, int width, int height)
	{
		SIZE = (width == height) ? width : -1;
		this.sheet = sheet;
		this.width = width;
		this.height = height;
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet)
	{
		this.SIZE = size;
		this.width = size;
		this.height = size;
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		this.pixels = new int[SIZE * SIZE];
		Load();
	}

	public Sprite(int width, int height, int color)
	{
		this.SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		SetColor(color);
	}

	public Sprite(int size, int color)
	{
		this.SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		SetColor(color);
	}

	public Sprite(int[] spritePixels, int width, int height)
	{
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[spritePixels.length];
		System.arraycopy(spritePixels, 0, this.pixels, 0, spritePixels.length);
	}

	public static Sprite[] Split(SpriteSheet sheet)
	{
		int sW = sheet.SPRITE_WIDTH;
		int sH = sheet.SPRITE_HEIGHT;
		int amount = (sheet.GetWidth() * sheet.GetHeight()) / (sW * sH);
		Sprite[] sprites = new Sprite[amount];
		int cur = 0;
		int[] pixels = new int[sH * sW];
		for(int yp = 0; yp < sheet.GetHeight() / sH; yp++)
		{
			for(int xp = 0; xp < sheet.GetWidth() / sW; xp++)
			{
				for(int y = 0; y < sH; y++)
				{
					for(int x = 0; x < sW; x++)
					{
						int x0 = x + xp * sW;
						int y0 = y + yp * sH;
						pixels[x + y * sW] = sheet.GetPixels()[x0 + y0 * sheet.GetWidth()];
					}
				}
				sprites[cur++] = new Sprite(pixels, sW, sH);
			}
		}
		return sprites;
	}

	private void SetColor(int color)
	{
		for(int i = 0; i < width * height; i++)
		{
			pixels[i] = color;
		}
	}

	public int GetWidth()
	{
		return width;
	}

	public int GetHeight()
	{
		return height;
	}

	private void Load()
	{
		for(int y = 0; y < SIZE; y++)
		{
			for(int x = 0; x < SIZE; x++)
			{
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
			}
		}
	}
}
