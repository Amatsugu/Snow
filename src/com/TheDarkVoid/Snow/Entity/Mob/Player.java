package com.TheDarkVoid.Snow.Entity.Mob;

import java.awt.Color;

import com.TheDarkVoid.Snow.Game;
import com.TheDarkVoid.Snow.Entity.Projectile.PlasmaProjectile;
import com.TheDarkVoid.Snow.Entity.Projectile.Projectile;
import com.TheDarkVoid.Snow.Graphics.AnimatedSprite;
import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.SpriteSheet;
import com.TheDarkVoid.Snow.Graphics.UI.UILabel;
import com.TheDarkVoid.Snow.Graphics.UI.UIManager;
import com.TheDarkVoid.Snow.Graphics.UI.UIPanel;
import com.TheDarkVoid.Snow.Input.Keyboard;
import com.TheDarkVoid.Snow.Input.Mouse;
import com.TheDarkVoid.Snow.Util.Vector2i;

public class Player extends Mob
{
	private Keyboard input;
	private boolean isWalking = false;

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 4);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 4);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 4);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 4);

	private AnimatedSprite animSprite = null;

	private int fireRate = 0;
	private double playerSpeed = 1.5;
	
	private UIManager ui;

	public Player(Keyboard input)
	{
		this.input = input;
		animSprite = down;
		sprite = animSprite.GetSprite();
		ui = Game.getUIManager();
	}

	public Player(int x, int y, Keyboard input)
	{
		this.x = x;
		this.y = y;
		this.input = input;
		animSprite = down;
		sprite = animSprite.GetSprite();
		fireRate = PlasmaProjectile.FIRE_RATE;
		ui = Game.getUIManager();
		UIPanel panel = new UIPanel(new Vector2i(Game.GetWidth() - (Game.GetWidth()/4),0), new Vector2i(Game.GetWidth()/4, Game.GetHeight()));
		ui.addPanel(panel);
		panel.addComponent(new UILabel(new Vector2i(10,35), "Label").setColor(new Color(0xffffff)));
	}

	public void Update()
	{
		if(fireRate > 0)
			fireRate--;
		double xa = 0, ya = 0;
		if(input.left)
		{
			xa -= playerSpeed;
			animSprite = left;
		} else if(input.right)
		{
			xa += playerSpeed;
			animSprite = right;
		}
		if(input.up)
		{
			ya -= playerSpeed;
			animSprite = up;
		} else if(input.down)
		{
			ya += playerSpeed;
			animSprite = down;
		}

		if(xa != 0 || ya != 0)
		{
			Move(xa, ya);
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
		Clear();
		UpdateShooting();
	}

	private void Clear()
	{
		for(int i = 0; i < level.GetProjectiles().size(); i++)
		{
			Projectile p = level.GetProjectiles().get(i);
			if(p.isRemoved())
				level.GetProjectiles().remove(i);
		}
	}

	private void UpdateShooting()
	{
		if(Mouse.GetButton() == 1 && fireRate <= 0)
		{
			double dx = Mouse.GetX() - Game.GetWidth() / 2;
			double dy = Mouse.GetY() - Game.GetHeight() / 2;
			double dir = Math.atan2(dy, dx);
			Shoot(x, y, dir);
			fireRate = PlasmaProjectile.FIRE_RATE;
		}
	}

	public void Render(Screen screen)
	{
		screen.RenderMob((int)(x - 16), (int)(y - 16), this);
	}
}
