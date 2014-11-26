package com.TheDarkVoid.Snow.Entity.Mob;

import java.util.List;

import com.TheDarkVoid.Snow.Graphics.AnimatedSprite;
import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.Sprite;
import com.TheDarkVoid.Snow.Graphics.SpriteSheet;
import com.TheDarkVoid.Snow.Level.Node;
import com.TheDarkVoid.Snow.Util.Vector2i;

public class Star extends Mob
{

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.chaser_down, 32, 32, 4);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.chaser_left, 32, 32, 4);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.chaser_right, 32, 32, 4);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.chaser_up, 32, 32, 4);

	private AnimatedSprite animSprite;

	private double xa = 0;
	private double ya = 0;
	List<Node> path = null;
	private double speed = 1;
	private int time = 0;

	private boolean isWalking;

	public Star(int x, int y)
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

		int px = (int) level.GetPlayerAt(0).GetX();
		int py = (int) level.GetPlayerAt(0).GetY();
		Vector2i start = new Vector2i((int) GetX() >> 4, (int) GetY() >> 4);
		Vector2i destination = new Vector2i(px >> 4, py >> 4);
		if(time % 10 == 0)
			path = level.findPath(start, destination);
		if(path != null)
		{
			if(path.size() > 0)
			{
				Vector2i vec = path.get(path.size() - 1).tile;
				if(x < vec.GetX() << 4)
					xa += speed;
				if(x > vec.GetX() << 4)
					xa -= speed;
				if(y < vec.GetY() << 4)
					ya += speed;
				if(y > vec.GetY() << 4)
					ya -= speed;
			}
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
		time++;
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
		if(path!= null)
		{
			for(int i = 0; i < path.size(); i++)
			{
				screen.RenderSprite(path.get(i).tile.GetX()*16+8, path.get(i).tile.GetY()*16+8, new Sprite(2,0x00ffff), true);
			}
		}
		screen.RenderMob((int) (x - 16), (int) (y - 16), this);
	}
}
