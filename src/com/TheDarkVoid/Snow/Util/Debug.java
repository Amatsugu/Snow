package com.TheDarkVoid.Snow.Util;

import com.TheDarkVoid.Snow.Graphics.Screen;

public class Debug
{
	private Debug()
	{
		
	}
	
	public static void DrawRect(Screen screen, int x, int y, int w, int h, int col, boolean fixed)
	{
		screen.DrawRect(x,y,w,h,col,fixed);
	}
	
	public static void DrawRect(Screen screen, int x, int y, int w, int h, boolean fixed)
	{
		screen.DrawRect(x,y,w,h, 0xff0000,fixed);
	}
	
	public static void log(Object msg)
	{
		System.out.println(msg);
	}
	
	public static void log2(Object msg)
	{
		System.out.print(msg);
	}
	
	public static void logError(Object msg)
	{
		System.err.println(msg);
	}
}
