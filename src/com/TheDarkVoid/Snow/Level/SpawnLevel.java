package com.TheDarkVoid.Snow.Level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.TheDarkVoid.Snow.Entity.Mob.Dummy;
import com.TheDarkVoid.Snow.Entity.Mob.Shooter;
import com.TheDarkVoid.Snow.Entity.Mob.Star;


public class SpawnLevel extends Level
{
	public SpawnLevel(String path)
	{
		super(path);
	}

	protected void LoadLevel(String path)
	{
		try
		{
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			height = image.getHeight();
			width = image.getWidth();
			tiles = new int[height * width];
			image.getRGB(0, 0, width, height, tiles, 0, width);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Failed to Load Level");
		}
		Add(new Shooter(20,35));
		Add(new Star(21,25));
		for(int i= 0; i<1; i++)
		{
			Add(new Dummy(21,38));
		}
	}

	protected void GenerateLevel()
	{
	}
}
