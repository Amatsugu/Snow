package com.TheDarkVoid.Snow.Graphics;

public class Font
{
	private static SpriteSheet font = new SpriteSheet("/fonts/font.png", 16);
	private static Sprite[] characters = Sprite.Split(font);

	private static String charIndex = "ABCDEFGHIJKLM" //
			+ "NOPQRSTUVWXYZ" //
			+ "abcdefghijklm" //
			+ "nopqrstuvwxyz" //
			+ "0123456789.,'"//
			+ "'\"\";:!@$%()-+";

	public Font()
	{

	}

	public void Render(int x, int y, String text, Screen screen)
	{
		Render(x, y, 0, 0, text, screen);
	}

	public void Render(int x, int y, int color, String text, Screen screen)
	{
		Render(x, y, 0, color, text, screen);
	}

	public void Render(int x, int y, int spacing, int color, String text, Screen screen)
	{
		int line = 0;
		int xOffset = 0;
		for(int i = 0; i < text.length(); i++)
		{
			xOffset += 16 + spacing;
			int yOffset = 0;
			char currentChar = text.charAt(i);
			if(currentChar == 'g' || currentChar == 'y' || currentChar == 'p' || currentChar == 'j' || currentChar == 'q')
			{
				yOffset = 2;
			}
			if(currentChar == ',' || currentChar == '.')
			{
				yOffset = 2;
			}
			if(currentChar == '\n')
			{
				line++;
				xOffset = 0;
			}
			int index = charIndex.indexOf(currentChar);
			if(index == -1)
				continue;
			screen.RenderTextCharacter(x + xOffset, y + yOffset + line * 20, characters[index], color, false);
		}
	}
}
