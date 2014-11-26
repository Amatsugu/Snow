package com.TheDarkVoid.Snow.Entity.Mob;

import java.util.List;

import com.TheDarkVoid.Snow.Entity.Entity;
import com.TheDarkVoid.Snow.Entity.Projectile.PlasmaProjectile;
import com.TheDarkVoid.Snow.Entity.Spawner.ParticleSpawner;
import com.TheDarkVoid.Snow.Graphics.AnimatedSprite;
import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.SpriteSheet;
import com.TheDarkVoid.Snow.Util.Vector2i;

public class Shooter extends Mob
{
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 4);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 4);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 4);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 4);

	private AnimatedSprite animSprite;

	private int time = 0;
	int xa = 0;
	int ya = 0;
	private int fireRate = 0;

	Entity rand = null;
	private boolean isWalking;

	public Shooter(int x, int y)
	{
		this.x = x << 4;
		this.y = y << 4;
		animSprite = down;
		sprite = animSprite.GetSprite();
		fireRate = PlasmaProjectile.FIRE_RATE;
	}

	public void Update()
	{
		if(fireRate > 0)
			fireRate--;
		time++;
		if(time % (random.nextInt(50) + 30) == 0)
		{
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if(random.nextInt(4) == 0)
			{
				xa = 0;
				ya = 0;
			}
		}
		if(xa < 0)
		{
			dir = Direction.LEFT;
			animSprite = left;
		} else if(xa > 0)
		{
			dir = Direction.RIGHT;
			animSprite = right;
		}
		if(ya < 0)
		{
			dir = Direction.UP;
			animSprite = up;
		} else if(ya > 0)
		{
			dir = Direction.DOWN;
			animSprite = down;
		}
		if(xa != 0 || ya != 0)
		{
			//Move(xa, ya);
			isWalking = true;
		} else
		{
			isWalking = false;
		}
		if(isWalking)
		{
			animSprite.Update();
			sprite = animSprite.GetSprite();
		} else
		{
			animSprite.SetFrame(0);
			sprite = animSprite.GetSprite();
		}
		if(fireRate <= 0)
		{
			ShootRandom();
		}
	}

	private void ShootRandom()
	{
		if(time % (60) == 0)
		{
			List<Entity> entities = level.GetEntities(this, 500);
			entities.add(level.GetClientPlayer());

			int index = random.nextInt(entities.size());
			while (entities.get(index) instanceof ParticleSpawner)
			{
				index = random.nextInt(entities.size());
			}

			rand = entities.get(index);
		}
		if(rand != null)
		{
			double px = rand.GetX();
			double py = rand.GetY();

			double dir = Math.atan2(py - y, px - x);
			Shoot(x, y, dir);
			fireRate = PlasmaProjectile.FIRE_RATE;
		}
	}

	@SuppressWarnings("unused")
	private void ShootClosest()
	{
		List<Entity> entities = level.GetEntities(this, 500);
		entities.add(level.GetClientPlayer());

		double min = 0;
		Entity closest = null;
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			if(e instanceof ParticleSpawner)
				continue;
			Vector2i a = new Vector2i((int) e.GetX(), (int) e.GetY());
			Vector2i b = new Vector2i((int) x, (int) y);
			double dist = Vector2i.Distance(a, b);
			if(i == 0 || dist < min)
			{
				min = dist;
				closest = e;
			}
		}
		if(closest != null)
		{
			double px = closest.GetX();
			double py = closest.GetY();

			double dir = Math.atan2(py - y, px - x);
			Shoot(x, y, dir);
			fireRate = PlasmaProjectile.FIRE_RATE;
		}
	}

	public void Render(Screen screen)
	{
		screen.RenderMob((int) (x - 16), (int) (y - 16), this);
	}

}
