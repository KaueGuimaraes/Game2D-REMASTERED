package com.kauescompany.entities;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import com.kauescompany.graphics.Spritesheet;
import com.kauescompany.graphics.UI;
import com.kauescompany.main.Game;
import com.kauescompany.main.Sound;

public class Player extends Entity{
	
	public static boolean right = false, left = false, jump = false;
	public static boolean attack = false;
	public static double spd = 1.2;
	public static final double initSpd = spd;
	
	private static final double addSpd = 0.1;
	
	public static int frames = 0, maxFrames = 100, waitFrames = 0, waitMaxFrames = 70;
	
	public static final int maxLife = 100;
	public static int life = maxLife;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		waitFrames++;
		
		if(right) {
			this.x+=spd;
		}else if(left) {
			this.x-=spd;
		}
		
		if(jump && waitFrames >= waitMaxFrames) {
			frames++;
			
			this.y = (Game.HEIGHT-48-16);
			
			if(frames >= maxFrames) {
				frames = 0;
				waitFrames = 0;
			}
		}else {
			jump = false;
			
			this.y = (Game.HEIGHT-48);
		}
		
		if(attack && !jump) {
			attack = false;
		}
		
		if(attack) {
			this.setSprite(16, 0, 16, 16);
		}else {
			this.setSprite(0, 0, 16, 16);
		}
		
		if(this.getX() > Game.WIDTH-16) {
			spd += addSpd;
			this.setX(0);
			this.revivalEnemy();
		}
		
		if(attack && this.getX() >= Game.WIDTH-32) {
			this.killEnemy();
		}
		
		if(this.isColliding(this, Game.enemy.bullet) && Game.enemy.attack) {
			life--;
			Sound.hurt.play();
		}
		
		if(life <= 0) {
			Game.State = "GameOver";
		}
	}
	
	public static void restart(double spd2, int life2) {
		spd = spd2;
		life = life2;
	}
	
	public void killEnemy() {
		Game.enemy.life = false;
		Game.enemy.attack = false;
		Game.enemy.attack = false;
		Game.enemy.frames1 = 0;
	}
	
	public void revivalEnemy() {
		Game.enemy.life = true;
	}
	
}
