package com.TheDarkVoid.Snow.Entity;

import java.util.Random;

import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.Sprite;
import com.TheDarkVoid.Snow.Level.Level;

public class Entity
{
	protected double x, y;
	protected Sprite sprite;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public void Update(){
		
	}
	
	public void Render(Screen screen)
	{
		if(sprite != null) screen.RenderSprite((int)x, (int)y, sprite, true);
	}
	
	public void Remove()
	{
		//Remove from level
		removed = true;
	}
	
	public boolean isRemoved()
	{
		return removed;
	}
	
	public void Init(Level level)
	{
		this.level = level;
	}
	
	public Sprite GetSprite()
	{
		return sprite;
	}
	
	public double GetX()
	{
		return x;
	}
	
	public double GetY()
	{
		return y;
	}
}
