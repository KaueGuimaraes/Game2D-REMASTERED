package com.kauescompany.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.kauescompany.main.Game;

public class UI {
	
	public void render(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(10, 10, Game.player.maxLife, 10);
		
		g.setColor(Color.red);
		g.fillRect(10, 10, Game.player.life, 10);
		
		g.setColor(Color.black);
		g.setFont(new Font("arial", 20, 10));
		g.drawString("life: "+Game.player.life, (Game.player.maxLife/2)-8, 20);
		
	}
	
}
