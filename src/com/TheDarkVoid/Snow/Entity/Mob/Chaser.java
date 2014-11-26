package com.TheDarkVoid.Snow.Entity.Mob;

import java.util.List;

import com.TheDarkVoid.Snow.Graphics.AnimatedSprite;
import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.SpriteSheet;

public class Chaser extends Mob
{

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.chaser_down, 32, 32, 4);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.chaser_left, 32, 32, 4);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.chaser_right, 32, 32, 4);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.chaser_up, 32, 32, 4);

	private AnimatedSprite animSprite;

	private double xa = 0;
	private double ya = 0;
	private double speed = 1;

	private boolean isWalking;

	public Chaser(int x, int y)
	{
		this.x = x << 4;
		this.y = y << 4;
		animSprite = down;
		sprite = down.GetSprite();
	}

	private void Move()
	{
		xa = 0;
		ya = 0;

		Player player = level.GetClientPlayer();
		List<Player> players = level.GetPlayers(this, 100);
		if(players.size() > 0)
		{
			if((int)x < (int)player.GetX())
				xa += speed;
			if((int)x > (int)player.GetX())
				xa -= speed;
			if((int)y < (int)player.GetY())
				ya += speed;
			if((int)y > (int)player.GetY())
				ya -= speed;
		}

		if(xa != 0 || ya != 0)
		{
			Move(xa, ya);
			isWalking = true;
		} else
		{
			isWalking = false;
		}
	}

	public void Update()
	{
		Move();
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
		screen.RenderMob((int) (x - 16), (int) (y - 16), this);
	}
}
