package com.TheDarkVoid.Snow.Level;

import java.util.Random;

public class RandomLevel extends Level
{
	private Random random = new Random();

	public RandomLevel(int width, int height)
	{
		super(width, height);
	}

	protected void GenerateLevel()
	{
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				if(random == null)
					random = new Random();
				tilesInt[x + y * width] = random.nextInt(64);
			}
		}
	}
}
