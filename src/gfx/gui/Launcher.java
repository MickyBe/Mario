package gfx.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import entity.mob.Player;
import mariomain.Game;

public class Launcher {

	public Button[] buttons;
	private int highScore=0;
	public Launcher(){
		buttons=new Button[2];
		buttons[0]=new Button(600,400,400,100,"Start Game");
		buttons[1]=new Button(600,500,400,100,"Exit Game");
	
	}

	public void render (Graphics g) {
		
		highScore=Game.coins+Player.distancetraveled;
		g.drawImage(Game.launcherbackground, 0, 0, Game.getFrameWidth()+10, Game.getFrameHeight()+10, null);
		if(highScore>0) {
		g.setFont(new Font ("courier",Font.BOLD,50));
		g.drawString("HIGH SCORE",700 ,165);
		g.drawString(""+highScore,700,200);
		}
		for (int i=0;i<buttons.length;i++) {
			buttons[i].render(g);
		}
	}
	
}
