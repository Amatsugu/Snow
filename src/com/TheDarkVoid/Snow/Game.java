package com.TheDarkVoid.Snow;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.TheDarkVoid.Snow.Entity.Mob.Player;
import com.TheDarkVoid.Snow.Graphics.Font;
import com.TheDarkVoid.Snow.Graphics.Screen;
import com.TheDarkVoid.Snow.Graphics.Sprite;
import com.TheDarkVoid.Snow.Input.Keyboard;
import com.TheDarkVoid.Snow.Input.Mouse;
import com.TheDarkVoid.Snow.Level.Level;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	public String title = "Snow";
	//Screen Size
	private static int WIDTH = 300;
	private static int HEIGHT = WIDTH / 16 * 9; //~168
	private static int SCALE = 3;
	//Thread(s)
	private Thread thread;

	private boolean isRunning = false;
	private JFrame frame;

	private Keyboard key;
	private Screen screen;
	private Level level;
	private Player player;
	@SuppressWarnings("unused")
	//TODO remove this
	private Font font;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private int[] frameLog = new int[120];
	private int curIndex = 0;
	private boolean isDebug = false;

	//private List<int> frameLog = new ArrayList<int>();

	public Game()
	{
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);

		screen = new Screen(WIDTH, HEIGHT);
		frame = new JFrame();
		key = new Keyboard();
		level = Level.spawnLevel;
		TileCoordinate playerSpawn = new TileCoordinate(20, 20);
		player = new Player(playerSpawn.x(), playerSpawn.y(), key);
		level.Add(player);
		font = new Font();

		addKeyListener(key);
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public static int GetWidth()
	{
		return WIDTH * SCALE;
	}

	public static int GetHeight()
	{
		return HEIGHT * SCALE;
	}

	public synchronized void Start()
	{
		isRunning = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void Stop()
	{
		isRunning = false;
		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; //Update Rate
		double deltaTime = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (isRunning)
		{
			long now = System.nanoTime();
			deltaTime += (now - lastTime) / ns;
			lastTime = now;

			while (deltaTime >= 1)
			{
				Update();
				updates++;
				deltaTime--;
			}
			Render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				frameLog[curIndex] = frames;
				curIndex++;
				if(curIndex > 119)
					curIndex = 0;
				frame.setTitle(title + " | " + frames + "fps, " + updates + "ups");
				updates = 0;
				frames = 0;

			}
		}
		Stop();
	}

	public void Update()
	{
		key.Update();
		level.Update();
	}

	public void Render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		screen.Clear();
		double xScroll = player.GetX() - screen.width / 2;
		double yScroll = player.GetY() - screen.height / 2;
		level.Render((int) xScroll, (int) yScroll, screen);
		//font.Render(10, 100, 0, "hey \nahere \nhere", screen);
		//player.Render(screen);
		if(key.GetKey(KeyEvent.VK_F3))
		{
			isDebug = !isDebug;
		}
		if(isDebug)
		{
			for(int i = 0; i < frameLog.length; i++)
			{
				if(frameLog[i] != 0)
				{
					int h = HEIGHT - ((int) (((double) frameLog[i] / 1000d) * 30d));
					Sprite sprite = new Sprite(1, HEIGHT, (0x00ffff) * (i * 2));
					screen.RenderSprite(i - 1, h, sprite, false);
				}
			}
		}
		//screen.RenderSheet(32, 32, SpriteSheet.player_down, false);
		screen.RenderSprite(0, 0, new Sprite(level.GetTilePixels(), level.GetWidth(), level.GetHeight()), false);
		screen.RenderSprite((int) player.GetX() / 16, (int) player.GetY() / 16, new Sprite(1, 0xff00ff), false);
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.magenta);
		//g.setFont(new Font("Helvetica", 0, 20));
		//g.fillRect(Mouse.GetX()-32, Mouse.GetY()-32, 64, 64);
		//if(isDebug)
		//	g.drawString("(" + player.GetX() + "," + player.GetY() + ") M: " + Mouse.GetButton(), 5, 25);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args)
	{
		//Set up game frame/Window
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.Start();

	}
}
