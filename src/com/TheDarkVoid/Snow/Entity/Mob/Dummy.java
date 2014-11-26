package com.TheDarkVoid.Snow.Entity.Mob;

import com.TheDarkVoid.Snow.Graphics.AnimatedSprite;
import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.SpriteSheet;

public class Dummy extends Mob
{
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 4);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 4);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 4);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 4);

	private AnimatedSprite animSprite;
	
	private int time = 0; 
	int xa = 0;
	int ya = 0; 

	private boolean isWalking;

	public Dummy(int x, int y)
	{
		this.x = x << 4;
		this.y = y << 4;
		animSprite = down;
		sprite = down.GetSprite();
	}

	public void Update()
	{
		time++;
		if(time % (random.nextInt(50)+30) == 0)
		{
			xa = random.nextInt(3)-1;
			ya = random.nextInt(3)-1;
			if(random.nextInt(4)==0)
			{
				xa = 0;
				ya = 0;
			}
		}
		if(xa < 0)
		{
			dir = Direction.LEFT;
			animSprite = left;
		} else if( xa > 0)
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
	}

	public void Render(Screen screen)
	{
		screen.RenderMob((int)(x-16), (int)(y-16), this);
	}

}
