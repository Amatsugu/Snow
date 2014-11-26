package com.TheDarkVoid.Snow.Entity.Projectile;

import java.util.Random;

import com.TheDarkVoid.Snow.Entity.Entity;
import com.TheDarkVoid.Snow.Graphics.Sprite;

public abstract class Projectile extends Entity
{
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double nx, ny;
	protected double x,y;
	protected double speed, range, damage;
	
	public final Random random = new Random();
	
	public Projectile(double x, double y, double dir)
	{
		xOrigin = x;
		yOrigin = y;
		this.x = x;
		this.y = y;
		angle = dir;
	}
	
	public int GetSpriteSize()
	{
		return sprite.SIZE;
	}
	
	public Sprite GetSprite()
	{
		return sprite;
	}
}
