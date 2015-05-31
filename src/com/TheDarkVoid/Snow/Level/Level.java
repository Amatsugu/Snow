package com.TheDarkVoid.Snow.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.TheDarkVoid.Snow.Entity.Entity;
import com.TheDarkVoid.Snow.Entity.Mob.Player;
import com.TheDarkVoid.Snow.Entity.Particle.Particle;
import com.TheDarkVoid.Snow.Entity.Projectile.Projectile;
import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Level.Tile.Tile;
import com.TheDarkVoid.Snow.Util.Vector2i;

public class Level
{
	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Player> players = new ArrayList<Player>();

	public static Level spawnLevel = new SpawnLevel("/levels/spawnLevel.png");

	private Comparator<Node> nodeSorter = new Comparator<Node>()
	{
		public int compare(Node n0, Node n1)
		{
			if(n1.fCost < n0.fCost)
				return +1;
			if(n1.fCost > n0.fCost)
				return -1;
			return 0;
		}

	};

	public Level(int width, int height)
	{
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		GenerateLevel();
	}

	public Level(String path)
	{
		LoadLevel(path);
		GenerateLevel();
	}

	protected void GenerateLevel()
	{
	}

	protected void LoadLevel(String path)
	{

	}

	public List<Projectile> GetProjectiles()
	{
		return projectiles;
	}

	@SuppressWarnings("unused")
	private void Time()
	{

	}

	public boolean TileCollision(int x, int y, int size, int xOffset, int yOffset)
	{
		boolean solid = false;
		for(int c = 0; c < 4; c++)
		{
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			//System.out.println("("+xt +","+yt+")");
			if(GetTile(xt, yt).isSolid())
				solid = true;
		}
		return solid;
	}

	public void Add(Entity e)
	{
		e.Init(this);
		if(e instanceof Particle)
		{
			particles.add((Particle) e);
		} else if(e instanceof Projectile)
		{
			projectiles.add((Projectile) e);
		} else if(e instanceof Player)
		{
			players.add((Player) e);
		} else
		{
			entities.add(e);
		}
	}

	public void Update()
	{
		for(int i = 0; i < entities.size(); i++)
		{
			entities.get(i).Update();
		}
		for(int i = 0; i < projectiles.size(); i++)
		{
			projectiles.get(i).Update();
		}
		for(int i = 0; i < particles.size(); i++)
		{
			particles.get(i).Update();
		}
		for(int i = 0; i < players.size(); i++)
		{
			players.get(i).Update();
		}
		Remove();
	}

	private void Remove()
	{
		for(int i = 0; i < entities.size(); i++)
		{
			if(entities.get(i).isRemoved())
				entities.remove(i);
		}
		for(int i = 0; i < projectiles.size(); i++)
		{
			if(projectiles.get(i).isRemoved())
				projectiles.remove(i);
		}
		for(int i = 0; i < particles.size(); i++)
		{
			if(particles.get(i).isRemoved())
				particles.remove(i);
		}
		for(int i = 0; i < players.size(); i++)
		{
			if(players.get(i).isRemoved())
				players.remove(i);
		}
	}

	public void Render(int xScroll, int yScroll, Screen screen)
	{
		screen.SetOffset(xScroll, yScroll);
		int x0 = Math.round(xScroll >> 4);
		int x1 = Math.round((xScroll + screen.width + 16) >> 4);
		int y0 = Math.round(yScroll >> 4);
		int y1 = Math.round((yScroll + screen.height + 16) >> 4);

		for(int y = y0; y < y1; y++)
		{
			for(int x = x0; x < x1; x++)
			{
				GetTile(x, y).Render(x, y, screen);
			}
		}
		for(int i = 0; i < entities.size(); i++)
		{
			entities.get(i).Render(screen);
		}
		for(int i = 0; i < projectiles.size(); i++)
		{
			projectiles.get(i).Render(screen);
		}
		for(int i = 0; i < particles.size(); i++)
		{
			particles.get(i).Render(screen);
		}
		for(int i = 0; i < players.size(); i++)
		{
			players.get(i).Render(screen);
		}
	}

	public List<Entity> GetEntities(Entity e, int radius)
	{
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int) e.GetX();
		int ey = (int) e.GetY();
		for(int i = 0; i < entities.size(); i++)
		{
			Entity en = entities.get(i);
			if(en.equals(e))
				continue;
			int x = (int) en.GetX();
			int y = (int) en.GetY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if(distance <= radius)
				result.add(en);
		}
		return result;
	}

	public List<Player> GetPlayers(Entity e, int radius)
	{
		List<Player> result = new ArrayList<Player>();
		int ex = (int) e.GetX();
		int ey = (int) e.GetY();
		for(int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			int x = (int) player.GetX();
			int y = (int) player.GetY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if(distance <= radius)
				result.add(player);
		}
		return result;
	}

	public List<Player> GetPlayers()
	{
		return players;
	}

	public Player GetPlayerAt(int index)
	{
		return players.get(index);
	}

	public Player GetClientPlayer()
	{
		return players.get(0);
	}

	//0xff000000 Void
	//0xff808080 GrassRock
	//0xff00ff00 Grass
	//0xffffff00 GrassFlower
	//0xffc9c9ff SnowRock
	//0xff0000ff SnowFlower
	//0xff00ffff Snow
	public Tile GetTile(int x, int y)
	{
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.Void;
		if(tiles[x + y * width] == Tile.col_Spawn_Grass)
		{
			return Tile.Spawn_Grass;
		} else if(tiles[x + y * width] == Tile.col_Spawn_GoldBrick)
		{
			return Tile.Spawn_GoldBrick;
		} else if(tiles[x + y * width] == Tile.col_Spawn_GrayBrick)
		{
			return Tile.Spawn_GrayBrick;
		} else if(tiles[x + y * width] == Tile.col_Spawn_RedBrick)
		{
			return Tile.Spawn_RedBrick;
		} else if(tiles[x + y * width] == Tile.col_Spawn_Snow)
		{
			return Tile.Spawn_Snow;
		} else if(tiles[x + y * width] == Tile.col_Spawn_DarkFloor)
		{
			return Tile.Spawn_DarkFloor;
		} else if(tiles[x + y * width] == Tile.col_Spawn_LightFloor)
		{
			return Tile.Spawn_LightFloor;
		} else if(tiles[x + y * width] == Tile.col_Spawn_Water)
		{
			return Tile.Spawn_Water;
		}

		return Tile.Void;
	}
	
	public int GetWidth()
	{
		return width;
	}
	
	public int GetHeight()
	{
		return height;
	}
	
	public int[] GetTilePixels()
	{
		return tiles;
	}

	//A* Pathfinder

	public List<Node> findPath(Vector2i start, Vector2i goal)
	{
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, Vector2i.Distance(start, goal));
		openList.add(current);
		while (openList.size() > 0)
		{
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if(current.tile.equals(goal))
			{
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null)
				{
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for(int i = 0; i < 9; i++)
			{
				if(i == 4)
					continue;
				int x = current.tile.GetX();
				int y = current.tile.GetY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = GetTile(x + xi, y + yi);
				if(at == null)
					continue;
				if(at.isSolid())
					continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (Vector2i.Distance(current.tile, a)) == 1 ? 1 : 0.95;
				double hCost = Vector2i.Distance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if(VecInList(closedList, a) && gCost >= current.gCost) 
					continue;
				if(!VecInList(openList, a) || gCost < current.gCost)
					openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}

	private boolean VecInList(List<Node> list, Vector2i vector)
	{
		for(Node n : list)
		{
			if(n.tile.equals(vector))
				return true;
		}
		return false;
	}

}
