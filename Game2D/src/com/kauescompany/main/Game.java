package com.kauescompany.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.kauescompany.entities.Enemy;
import com.kauescompany.entities.Player;
import com.kauescompany.graphics.Spritesheet;
import com.kauescompany.graphics.UI;

public class Game extends Canvas implements Runnable, KeyListener, MouseMotionListener{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 430;
	public static final int HEIGHT = 220;
	public static final int SCALE = 3;
	
	private BufferedImage image;
	
	public static Spritesheet spritesheet;
	public static Player player;
	public static Enemy enemy;
	public static UI ui;
	
	public static String State = "Menu";
	private static boolean restart = false;
	private static boolean showGameOver = true;
	private static int contGO = 0;
	private static final int maxContGO = 30;
	
	public static boolean save = false;
	
	public Menu menu;
	
	public int mx, my;
	
	//TESTE
	/*
	public int x, y;
	public int spd = 3;
	*/
	//TESTE
	
	public Game() {
		Sound.musicBackgound.loop();
		addKeyListener(this);
		spritesheet = new Spritesheet("/spritesheet.png");
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		enemy = new Enemy(WIDTH-16, HEIGHT-48, 16, 16, spritesheet.getSprite(0, 0, 16, 16));
		player = new Player(0, HEIGHT-48, 16, 16, spritesheet.getSprite(0, 0, 16, 16));
		menu = new Menu();
	}
	
	public void initFrame() {
		frame = new JFrame("Game2D");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		if(State == "Normal") {
			
			if(this.save) {
				this.save = false;
				String[] opt1 = {"Spd", "vida", "X", "Y"};
				int[] opt2 = {(int) this.player.spd, (int) player.life, player.getX(), player.getY()};
				Menu.saveGame(opt1, opt2, 20);
				System.out.println("SALVO!!");
			}
			
			if(menu.pause) {
				State = "Menu";
			}
			
			menu.enter = false;
			menu.up = false;
			menu.down = false;
			restart = false;
			
			player.tick();
			if(enemy.life) {
				enemy.tick();
			}
		
		}else if(State == "GameOver") {
			menu.enter = false;
			menu.up = false;
			menu.down = false;
			menu.pause = false;
			
			if(restart) {
				restartGame(0, 0, player.initSpd, 100);
			}
			
			contGO++;
			
			if(contGO >= maxContGO) {
				contGO = 0;
				
				if(showGameOver) {
					showGameOver = false;
				}else {
					showGameOver = true;
				}
			}
		}else if(State == "Menu") {
			menu.tick();
		}
		
		//TESTE
		/*
		if(x < mx) {
			x += spd;
		}else if(x > mx) {
			x -= spd;
		}
		
		if(y < my) {
			y += spd;
		}else if(y > my) {
			y -= spd;
		}
		*/
		//TESTE
	}
	
	public static void restartGame(int X, int Y, double Spd, int life){
		State = "Normal";
		
		showGameOver = true;
		contGO = 0;
		
		restart = false;
		
		enemy.frames1 = 0;
		enemy.attack = false;
		
		Player.restart(Spd, life);
		
		spritesheet = new Spritesheet("/spritesheet.png");
		
		ui = new UI();
		enemy = new Enemy(Game.WIDTH-16, Game.HEIGHT-48, 16, 16, Game.spritesheet.getSprite(0, 0, 16, 16));
		player = new Player(0, Game.HEIGHT-48, 16, 16, Game.spritesheet.getSprite(X, Y, 16, 16));
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(Color.gray);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.darkGray);
		g.fillRect(0, HEIGHT-32, WIDTH, 48);
		
		/*RENDERIZAÇÃO DO JOGO*/
		
		ui.render(g);
		if(enemy.life) {
			enemy.render(g);
		}
		player.render(g);
		
		/**/
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		if(State == "GameOver") {
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			
			g.setColor(Color.RED);
			
			g.setFont(new Font("arial", 100, 55));
			g.drawString("Game Over", (WIDTH/2) + 245, 150);
			
			if(showGameOver) {
				g.setFont(new Font("arial", 70, 35));
				g.drawString(">Pressione ENTER para reiniciar<", (WIDTH/2) + 140, 300);
			}
		}else if(State == "Menu"){
			menu.render(g);
		}
		
		//TESTE
		/*
		//g.fillRect(x, y, 10, 10);
		g.drawImage(spritesheet.getSprite(94, 0, 16, 16), x, y, null);
		*/
		//TESTE
		
		//TESTE
		/*
		Graphics2D g2 = (Graphics2D) g;
		
		double angleMouse = Math.atan2(200+25 - my, 200+25 - mx);
		
		g2.rotate(angleMouse, 200+25, 200+25);
		//g.setColor(Color.red);
		//g.fillRect(200, 200, 50, 50);
		g.drawImage(spritesheet.getSprite(96, 0, 16, 16), 200, 200, null);
		*/
		//TESTE
		
		bs.show();
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == e.VK_RIGHT ||
			e.getKeyCode() == e.VK_D) {
			player.right = true;
		}else if(e.getKeyCode() == e.VK_LEFT ||
				e.getKeyCode() == e.VK_A) {
			player.left = true;
		}
		
		if(e.getKeyCode() == e.VK_W ||
			e.getKeyCode() == e.VK_SPACE ||
			e.getKeyCode() == e.VK_UP) {
			player.jump = true;
		}
		
		if(e.getKeyCode() == e.VK_F) {
			player.attack = true;
		}
		
		if(e.getKeyCode() == e.VK_F5) {
			if(State == "Normal")
				save = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == e.VK_RIGHT ||
			e.getKeyCode() == e.VK_D) {
			player.right = false;
		}else if(e.getKeyCode() == e.VK_LEFT ||
				e.getKeyCode() == e.VK_A) {
			player.left = false;
			}
		
		if(e.getKeyCode() == e.VK_W ||
				e.getKeyCode() == e.VK_SPACE ||
				e.getKeyCode() == e.VK_UP) {
				player.jump = false;
			}
		
		if(e.getKeyCode() == e.VK_W ||
				e.getKeyCode() == e.VK_UP) {
			menu.up = true;
		}
		
		if(e.getKeyCode() == e.VK_S ||
				e.getKeyCode() == e.VK_DOWN) {
			menu.down = true;
		}
		
		if(e.getKeyCode() == e.VK_F) {
			player.attack = false;
		}
		
		if(e.getKeyCode() == e.VK_ENTER) {
			restart = true;
			menu.enter = true;
		}
		
		if(e.getKeyCode() == e.VK_ESCAPE) {
			if(State == "Normal")
				menu.pause = true;
			menu.esc = true;
		}
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunning) {
			
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: "+frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mx = e.getX();
		this.my = e.getY();
	}
	
}
