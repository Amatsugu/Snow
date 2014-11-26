package com.TheDarkVoid.Snow.Level.Tile;

import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.Sprite;
import com.TheDarkVoid.Snow.Level.Tile.SpawnLevel.SpawnBrickTile;
import com.TheDarkVoid.Snow.Level.Tile.SpawnLevel.SpawnFloorTile;
import com.TheDarkVoid.Snow.Level.Tile.SpawnLevel.SpawnGrassTile;

public class Tile
{
	public Sprite sprite;

	public static Tile Void = new VoidTile(Sprite.voidSprite);
	public static Tile GrassFlower = new GrassTile(Sprite.spawn_grassFlower);
	public static Tile GrassRock = new GrassTile(Sprite.spawn_grassRock);
	public static Tile SnowFlower = new GrassTile(Sprite.spawn_snowFlower);
	public static Tile SnowRock = new GrassTile(Sprite.spawn_snowRock);
	//Spawn Level
	public static Tile Spawn_Grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile Spawn_Snow = new SpawnGrassTile(Sprite.spawn_snow);
	public static Tile Spawn_GrayBrick = new SpawnBrickTile(Sprite.spawn_brick0);
	public static Tile Spawn_GoldBrick = new SpawnBrickTile(Sprite.spawn_brick1);
	public static Tile Spawn_RedBrick = new SpawnBrickTile(Sprite.spawn_brick2);
	public static Tile Spawn_Water = new SpawnBrickTile(Sprite.spawn_water);
	public static Tile Spawn_DarkFloor = new SpawnFloorTile(Sprite.spawn_floor0);
	public static Tile Spawn_LightFloor = new SpawnFloorTile(Sprite.spawn_floor1);
	
	public final static int col_Spawn_Grass = 0xff00ff00;
	public final static int col_Spawn_Snow = 0; //TODO set snow Color id value
	public final static int col_Spawn_Water = 0; //TODO set water color id value
	public final static int col_Spawn_GrayBrick = 0xffc9c9c9;
	public final static int col_Spawn_RedBrick = 0xffae0000;
	public final static int col_Spawn_GoldBrick = 0xffff7e00;
	public final static int col_Spawn_DarkFloor = 0xff722600;
	public final static int col_Spawn_LightFloor = 0xffb78823;

	public Tile(Sprite sprite)
	{
		this.sprite = sprite;
	}

	public void Render(int x, int y, Screen screen)
	{

	}

	public boolean isSolid()
	{
		return false;
	}
}
