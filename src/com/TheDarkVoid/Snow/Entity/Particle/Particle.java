package com.TheDarkVoid.Snow.Entity.Particle;

import com.TheDarkVoid.Snow.Entity.Entity;
import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.Sprite;

public class Particle extends Entity
{
	private Sprite sprite;

	private int life;
	private int time;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	public Particle(int x, int y, int life)
	{
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(20) - 10);
		sprite = Sprite.particle_basic;
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}

	public void Update()
	{
		time++;
		if(time >= 9001)
			time = 0;
		if(time > life)
			Remove();
		za -= 0.1;
		if(zz < 0)
		{
			zz = 0;
			za *= -0.5;
			xa *= 0.4;
			ya *= 0.4;
		}

		Move(xx + xa, (yy + ya) + (zz + za));
	}

	private void Move(double x, double y)
	{
		if(Collision(x, y))
		{
			this.xa *= -.5;
			this.ya *= -.5;
			this.za *= -.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}

	public boolean Collision(double x, double y)
	{
		boolean solid = false;
		for(int c = 0; c < 4; c++)
		{
			double xt = (x - c % 2 * 16) / 16;
			double yt = (y - c / 2 * 16) / 16;
			//System.out.println("("+xt +","+yt+")");
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
			if(level.GetTile((int) ix, (int) iy).isSolid())
				solid = true;
		}
		return solid;
	}

	public void Render(Screen screen)
	{
		screen.RenderSprite((int) xx-1, (int) yy - (int) zz, sprite, true);
	}
}
