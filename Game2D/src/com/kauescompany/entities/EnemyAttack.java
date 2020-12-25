package com.kauescompany.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.kauescompany.main.Game;

public class EnemyAttack extends Entity{
	
	private static int frames = 0;
	private static final int maxFrames = 80;
	
	public EnemyAttack(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		frames++;
		
		if(frames >= maxFrames) {
			Game.enemy.attack = false;
			Game.enemy.attackPast = false;
			frames = 0;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	
}
