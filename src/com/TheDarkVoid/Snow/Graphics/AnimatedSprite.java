package com.TheDarkVoid.Snow.Graphics;

public class AnimatedSprite extends Sprite
{
	private int frame = 0;
	private Sprite sprite;
	private int rate = 8;
	private int time = 0;
	private int length = -1;

	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length)
	{
		super(sheet, width, height);
		this.length = length;
		sprite = sheet.GetSprites()[0];
		if(length > sheet.GetSprites().length)
			System.err.println("Error! Length of animation is too long.");
	}

	public void Update()
	{
		time++;
		if(time % rate == 0)
		{
			if(frame >= length - 1)
				frame = 0;
			else
				frame++;
			sprite = sheet.GetSprites()[frame];
		}
		//System.out.println(sprite + ", Frames: " + frame);
	}

	public Sprite GetSprite()
	{
		return sprite;
	}

	public void SetFrame(int frame)
	{
		if(frame > sheet.GetSprites().length - 1)
		{
			System.err.println("Error! Length of animation is too long.");
			return;
		}
		this.frame = frame;
		sprite = sheet.GetSprites()[frame];

	}

}
