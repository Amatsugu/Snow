package com.TheDarkVoid.Snow.Level.Tile.SpawnLevel;

import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.Sprite;
import com.TheDarkVoid.Snow.Level.Tile.Tile;

public class SpawnFloorTile extends Tile
{

	public SpawnFloorTile(Sprite sprite)
	{
		super(sprite);
	}

	public void Render(int x, int y, Screen screen)
	{
		screen.RenderTile(x << 4, y << 4, this);
	}

}
