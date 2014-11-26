package com.TheDarkVoid.Snow.Level.Tile;

import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.Sprite;

public class VoidTile extends Tile
{
	public VoidTile(Sprite sprite)
	{
		super(sprite);
	}

	public void Render(int x, int y, Screen screen)
	{
		screen.RenderTile(x << 4, y << 4, this);
	}
}
