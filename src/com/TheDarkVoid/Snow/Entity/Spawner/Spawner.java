package com.TheDarkVoid.Snow.Entity.Spawner;

import java.util.ArrayList;
import java.util.List;

import com.TheDarkVoid.Snow.Entity.Entity;
import com.TheDarkVoid.Snow.Level.Level;

public abstract class Spawner extends Entity
{
	@SuppressWarnings("unused")//TODO remove
	private List<Entity> entities = new ArrayList<Entity>();

	public enum Type
	{
		MOB, PARTICLE;
	}

	@SuppressWarnings("unused")//TODO remove
	private Type type;

	public Spawner(int x, int y, Type type, int amount, Level level)
	{
		this.x = x;
		this.y = y;
		this.type = type;
		this.level = level;
		
	}
}
