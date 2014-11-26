package com.TheDarkVoid.Snow.Graphics;

import java.util.Random;

import com.TheDarkVoid.Snow.Entity.Mob.Mob;
import com.TheDarkVoid.Snow.Entity.Mob.Star;
import com.TheDarkVoid.Snow.Entity.Projectile.Projectile;
import com.TheDarkVoid.Snow.Level.Tile.Tile;

public class Screen
{
	public int[] pixels;

	public int width, height;
	public int xOffset, yOffset;

	public final int MAP_SIZE = 8;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;

	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	
	private final int ALPHA_COL = 0xffff00ff;

	public Random random = new Random();

	public Screen(int width, int height)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		for(int i = 0; i < MAP_SIZE * MAP_SIZE; i++)
		{
			tiles[i] = random.nextInt(0xff00ff);
		}
		tiles[0] = 0;
	}

	public void Clear()
	{
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = 0x100010;
		}
	}

	public void RenderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed)
	{
		if(fixed)
		{
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sheet.SPRITE_HEIGHT; y++)
		{
			int ya = y + yp;
			for(int x = 0; x < sheet.SPRITE_WIDTH; x++)
			{
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.SPRITE_WIDTH];
			}
		}
	}
	
	public void RenderTextCharacter(int xp, int yp, Sprite sprite, int color, boolean fixed)
	{
		if(fixed)
		{
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sprite.GetHeight(); y++)
		{
			int ya = y + yp;
			for(int x = 0; x < sprite.GetWidth(); x++)
			{
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int col = sprite.pixels[x + y * sprite.GetWidth()];
				if(col != ALPHA_COL)
					pixels[xa + ya * width] = color;
			}
		}
	}

	public void RenderSprite(int xp, int yp, Sprite sprite, boolean fixed)
	{
		if(fixed)
		{
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sprite.GetHeight(); y++)
		{
			int ya = y + yp;
			for(int x = 0; x < sprite.GetWidth(); x++)
			{
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int col = sprite.pixels[x + y * sprite.GetWidth()];
				if(col != ALPHA_COL)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void RenderTile(int xp, int yp, Tile tile)
	{
		xp -= xOffset;
		yp -= yOffset;
		int size = tile.sprite.SIZE;
		for(int y = 0; y < size; y++)
		{
			int ya = yp + y;
			for(int x = 0; x < size; x++)
			{
				int xa = xp + x;
				if(xa < -size || xa >= width || ya < 0 || ya >= height)
					break;
				if(xa < 0)
					xa = 0;
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * size];
			}
		}
	}

	public void RenderProjectile(int xp, int yp, Projectile p)
	{
		xp -= xOffset;
		yp -= yOffset;
		int size = p.GetSpriteSize();
		for(int y = 0; y < size; y++)
		{
			int ya = yp + y;
			for(int x = 0; x < size; x++)
			{
				int xa = xp + x;
				if(xa < -size || xa >= width || ya < 0 || ya >= height)
					break;
				if(xa < 0)
					xa = 0;
				int col = p.GetSprite().pixels[x + y * size];
				if(col != ALPHA_COL)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void RenderMob(int xp, int yp, Mob mob)
	{
		xp -= xOffset;
		yp -= yOffset;
		int size = 32;
		for(int y = 0; y < size; y++)
		{
			int ya = yp + y;
			int ys = y;
			//if(flip == 2 || flip == 3)
			//ys = (size-1)-y;
			for(int x = 0; x < size; x++)
			{
				int xa = xp + x;
				int xs = x;
				//if(flip == 1 || flip == 3)
				//xs = (size-1)-x;
				if(xa < -size || xa >= width || ya < 0 || ya >= height)
					break;
				if(xa < 0)
					xa = 0;
				int col = mob.GetSprite().pixels[xs + ys * size];
				if((mob instanceof Star) && (col == 0xffff0000))// || col == 0xff161616 || col == 0xff141414 || col == 0xff171717 || col == 0xff181818 || col == 0xff191919))
					col = 0xffdd0000 * random.nextInt(2) + 100;
				if(col != ALPHA_COL)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void RenderMob(int xp, int yp, Sprite sprite, int flip)
	{
		xp -= xOffset;
		yp -= yOffset;
		int size = 32;
		for(int y = 0; y < size; y++)
		{
			int ya = yp + y;
			int ys = y;
			if(flip == 2 || flip == 3)
				ys = (size - 1) - y;
			for(int x = 0; x < size; x++)
			{
				int xa = xp + x;
				int xs = x;
				if(flip == 1 || flip == 3)
					xs = (size - 1) - x;
				if(xa < -size || xa >= width || ya < 0 || ya >= height)
					break;
				if(xa < 0)
					xa = 0;
				int col = sprite.pixels[xs + ys * size];
				if(col != ALPHA_COL)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void SetOffset(int xOffset, int yOffset)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void DrawRect(int xp, int yp, int w, int h, int col, boolean fixed)
	{
		if(fixed)
		{
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int x = xp; x < xp + w; x++)
		{
			if(x < 0 || x >= this.width || yp >= this.height)
				continue;
			if(yp > 0)
				pixels[x + yp * this.width] = col;
			if(yp + h >= this.height)
				continue;
			if(yp + h > 0)
				pixels[x + (yp + h) * this.width] = col;
		}
		for(int y = yp; y <= yp + h; y++)
		{
			if(xp >= this.width || y < 0 || y >= this.height)
				continue;
			if(xp > 0)
				pixels[xp + y * this.width] = col;
			if(xp + w >= this.width)
				continue;
			if(xp + w > 0)
				pixels[(xp + w) + y * this.width] = col;
		}
	}
}
