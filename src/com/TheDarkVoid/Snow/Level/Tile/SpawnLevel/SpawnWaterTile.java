package com.TheDarkVoid.Snow.Level.Tile.SpawnLevel;

import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.Sprite;
import com.TheDarkVoid.Snow.Level.Tile.Tile;

public class SpawnWaterTile extends Tile
{

	public SpawnWaterTile(Sprite sprite)
	{
		super(sprite);
	}

	public void Render(int x, int y, Screen screen)
	{
		screen.RenderTile(x << 4, y << 4, this);
	}

}
