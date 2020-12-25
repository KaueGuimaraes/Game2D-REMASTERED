package com.kauescompany.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Menu {
	
	public String[] options = {"Novo Jogo", "Carregar Jogo", "Comandos", "Sair"};
	public String[] comandosList = {"ESC - Pausar o jogo", "A - andar para a esquerda", "D - andar para a direita", "W - voar", "F - atacar", "", "PRESSIONE ESC PARA VOLTAR"};
	
	public int choose = 0, maxChoose = options.length - 1;
	
	public boolean up, down, enter;
	public static boolean pause;
	public boolean comandos;
	public boolean esc;
	
	public void tick() {
		if(comandos) {
			if(esc) {
				comandos = false;
			}
		}else {
			
			esc = false;
			
			if(pause)
				options[0] = "Continuar";
			
			if(up) {
				up = false;
				choose--;
				
				if(choose < 0) {
					choose = maxChoose;
				}
			}
			
			if(down) {
				down = false;
				choose++;
				
				if(choose > maxChoose) {
					choose = 0;
				}
			}
		}
		
		if(enter) {
			
			if(options[choose] == options[1 - 1]) {
				//Novo Jogo
				
				File file = new File("Save.txt");
				file.delete();
				
				Game.State = "Normal";
				pause = false;
			}else if(options[choose] == options[2 -1]) {
				//Carregar Jogo
				
				File file = new File("Save.txt");
				
				if(file.exists()) {
					String saver = loadGame(20);
					applySave(saver);
				}
			}else if(options[choose] == options[3 - 1]) {
				//Comandos
				
				comandos = true;
			}else if(options[choose] == options[4 - 1]) {
				//Sair
				
				System.exit(1);
			}
			
			enter = false;
		}
		
		pause = false;
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0])
			{
				case "Spd":
					Game.restartGame(0, 0, Integer.parseInt(spl2[1]), Game.player.maxLife);
					pause = false;
					break;
					
				case "vida":
					Game.player.life = Integer.parseInt(spl2[1]);
					break;
					
				case "X":
					Game.player.setX(Integer.parseInt(spl2[1]));
					break;
					
				case "Y":
					Game.player.setY(Integer.parseInt(spl2[1]));
					break;
				
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("Save.txt");
		
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("Save.txt"));
				
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for(int i = 0; i < val.length; i++) {
							val[i]-=encode;
							trans[1]+=val[i];
						}
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="/";
					}
				}catch(IOException e) {}
			}catch(FileNotFoundException e) {}
		}
		
		return line;
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("Save.txt"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current+=":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			
			for(int n = 0; n < value.length; n++) {
				value[n]+=encode;
				current+=value[n];
			}
			
			try {
				write.write(current);
				if(i < val1.length - 1)
					write.newLine();
			}catch(IOException e) {}
		}
		
		try {
			write.flush();
			write.close();
		}catch(IOException e) {}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		
		g.setColor(Color.red);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString("Game2D", (Game.WIDTH * Game.SCALE - 170) / 2, (Game.HEIGHT * Game.SCALE - 450) / 2);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 20));
		
		for(int i = 0; i < options.length; i++) {
			if(options[i] == options[choose])
				g.drawString(">", 178 * Game.SCALE, (80 * Game.SCALE) + (i * 20));
			g.drawString(options[i], 185 * Game.SCALE, (80 * Game.SCALE) + (i * 20));
		}
		
		if(comandos) {
			g.setColor(Color.black);
			g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
			
			g.setColor(Color.white);
			g.setFont(new Font("arial", 0, 16));
			
			for(int i = 0; i < comandosList.length; i++) {
				g.drawString(comandosList[i], 100, 100 + (20 * i));
			}
		}
		
	}
	
}
