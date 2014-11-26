package com.TheDarkVoid.Snow.Entity.Spawner;

import com.TheDarkVoid.Snow.Entity.Particle.Particle;
import com.TheDarkVoid.Snow.Level.Level;

public class ParticleSpawner extends Spawner
{
	private int life;

	public ParticleSpawner(int x, int y, int life, int amount, Level level)
	{
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for(int i = 0; i < amount; i++)
		{
			level.Add(new Particle(x,y, this.life));
		}
	}

}
