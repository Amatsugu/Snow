package com.TheDarkVoid.Snow.Graphics.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.TheDarkVoid.Snow.Util.Vector2i;

public class UILabel extends UIComponent
{
	public String text;
	private Font font;
	
	public UILabel(Vector2i position, String text)
	{
		super(position);
		this.text = text;
		font = new Font("Helvetica", Font.BOLD, 32);
		color = new Color(0xff00ff);
	}
	
	public UILabel setColor(Color color)
	{
		this.color = color;
		return this;
	}
	
	public UILabel setFont(Font font)
	{
		this.font = font;
		return this;
	}
	
	public void Render(Graphics g)
	{
		g.setFont(font);
		g.setColor(color);
		g.drawString(text, position.x+offset.x, position.y+offset.y);
	}
}
