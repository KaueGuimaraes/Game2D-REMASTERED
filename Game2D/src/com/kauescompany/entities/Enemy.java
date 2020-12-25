package com.kauescompany.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.kauescompany.main.Game;

public class Enemy extends Entity{
	
	private static BufferedImage[] sprites;
	private static BufferedImage gun;
	private static int index = 0, maxIndex = 3;
	
	private static int frames = 0;
	private static final int maxFrames = 20;
	
	public static int frames1 = 0;
	private static final int maxFrames1 = 200;
	
	public static boolean attack = false;
	public static boolean attackPast = false;
	
	public static boolean life = true;
	
	public static EnemyAttack bullet;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		sprites = new BufferedImage[4];
		
		gun = Game.spritesheet.getSprite(160-16, 16, 16, 16);
		
		bullet = new EnemyAttack(0, this.getY()+6, Game.WIDTH-24, 3, null);
		
		sprites[0] = Game.spritesheet.getSprite(160 - 16, 0, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(160 - 32, 0, 16, 16);
		sprites[2] = Game.spritesheet.getSprite(160 - 48, 0, 16, 16);
		sprites[3] = Game.spritesheet.getSprite(160 - 64, 0, 16, 16);
	}
	
	public void tick() {
		if(attack) {
			bullet.tick();
		}
		else {
			frames1++;
		}
		
		frames++;
		
		if(frames >= maxFrames) {
			frames = 0;
			index++;
		}
		
		if(index > maxIndex) {
			index = 0;
		}
		
		if(frames1 >= (maxFrames1-70)) {
			attackPast = true;
		}
		
		if(frames1 >= maxFrames1) {
			frames1 = 0;
			
			//Ataque
			
			attack = true;
		}
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX(), this.getY(), null);
		
		g.drawImage(gun, this.getX()-9, this.getY()+1, null);
		
		if(attackPast) {
			g.setColor(Color.RED);
			g.fillRect(this.getX()-9, this.getY()+6, 1, 3);
		}
		
		if(attack) {
			bullet.render(g);
		}
	}
	
}
