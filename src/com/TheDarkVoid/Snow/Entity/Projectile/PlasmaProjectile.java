package com.TheDarkVoid.Snow.Entity.Projectile;

import com.TheDarkVoid.Snow.Entity.Spawner.ParticleSpawner;
import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.Sprite;

public class PlasmaProjectile extends Projectile
{
	public final static int FIRE_RATE = 10;

	public PlasmaProjectile(double x, double y, double dir)
	{
		super(x, y, dir);
		range = 200;
		damage = 20;
		speed = 4;
		sprite = Sprite.rotate(Sprite.projectile_arrow, angle);

		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}

	public void Update()
	{
		Move();
		if(level.TileCollision((int) (x + nx), (int) (y + ny), 10, 3, 3))
		{
			//Particle p = new Particle((int)x,(int)y, 50, 500);
			//level.Add(p);
			level.Add(new ParticleSpawner((int) x, (int) y, 50, 50, level));
			Remove();
		}
	}

	public void Render(Screen screen)
	{
		screen.RenderProjectile((int) x - 8, (int) y - 3, this);
	}

	protected void Move()
	{
		x += nx;
		y += ny;
		if(Distance() > range)
		{
			Remove();
		}
	}

	private double Distance()
	{
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x)) + Math.abs((yOrigin - y) * (yOrigin - y)));
		return dist;
	}

}
