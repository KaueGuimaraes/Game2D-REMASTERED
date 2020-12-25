package com.kauescompany.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.kauescompany.main.Game;

public class Entity {
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.sprite = sprite;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setSprite(int x, int y, int width, int height) {
		this.sprite = Game.spritesheet.getSprite(x, y, width, height);
	}
	
	public int getX() {
		return (int) this.x;
	}
	
	public int getY() {
		return (int) this.y;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public BufferedImage getSprite() {
		return this.sprite;
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX(), e2.getY(), e2.getWidth(), e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(this.getSprite(), this.getX(), this.getY(), null);
	}
	
}
