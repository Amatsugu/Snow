package com.TheDarkVoid.Snow.Entity.Mob;

import com.TheDarkVoid.Snow.Entity.Entity;
import com.TheDarkVoid.Snow.Entity.Projectile.PlasmaProjectile;
import com.TheDarkVoid.Snow.Entity.Projectile.Projectile;
import com.TheDarkVoid.Snow.Graphics.Screen;

public abstract class Mob extends Entity
{
	protected boolean moving = false;

	protected enum Direction
	{
		UP, DOWN, LEFT, RIGHT
	}

	protected Direction dir;

	public void Move(double xa, double ya)
	{
		if(xa != 0 && ya != 0)
		{
			Move(xa, 0);
			Move(0, ya);
			return;
		}
		if(ya > 0)
			dir = Direction.UP;
		if(ya < 0)
			dir = Direction.DOWN;
		if(xa > 0)
			dir = Direction.RIGHT;
		if(xa < 0)
			dir = Direction.LEFT;
		while (xa != 0)
		{
			if(Math.abs(xa) > 1)
			{
				if(!Collision(abs(xa), ya))
				{
					this.x += abs(xa);
				}
				xa -= abs(xa);
			} else
			{
				if(!Collision(abs(xa), ya))
				{
					this.x += xa;
				}
				xa = 0;
			}
		}
		//System.out.println(this.x);
		while (ya != 0)
		{
			if(Math.abs(ya) > 1)
			{
				if(!Collision(xa, abs(ya)))
				{
					this.y += abs(ya);
				}
				ya -= abs(ya);
			} else
			{
				if(!Collision(xa, abs(ya)))
				{
					this.y += ya;
				}
				ya = 0;
			}
		}
		//System.out.println(this.y);
	}

	private int abs(double value)
	{
		if(value < 0)
			return -1;
		else
			return 1;
	}

	public abstract void Update();

	public abstract void Render(Screen screen);

	protected void Shoot(double xp, double yb, double dir)
	{
		Projectile p = new PlasmaProjectile(x, y, dir);
		level.Add(p);
	}

	private boolean Collision(double xa, double ya)
	{
		boolean solid = false;
		for(int c = 0; c < 4; c++)
		{
			double xt = ((x + xa) - c % 2 * 15) / 16;
			double yt = ((y + ya) - c / 2 * 15) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if(c % 2 == 0)
			{
				ix = (int) Math.floor(xt);
			}
			if(c / 2 == 0)
			{
				iy = (int) Math.floor(yt);
			}
			if(level.GetTile(ix, iy).isSolid())
				solid = true;
		}
		return solid;
	}

}
