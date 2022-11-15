package entity.mob;

import java.awt.Color;
import java.awt.Graphics;

import entity.Entity;
import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;

public class Plant extends Entity {
	private int wait;
	private int pixelsTravelled;
	private boolean moving;
	private int insidePipe;

	public Plant(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		moving=false;
		insidePipe=1;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.Plant.getBufferedImage(),x+30,y,width+10,height+80,null);
		
	}

	@Override
	public void tick() {
	y+=vely;
	x+=velx;
		

		if(insidePipe%3==0)setVely(-1);
		else if(insidePipe%3==1)setVely(0);
		else if(insidePipe%3==2) setVely(1);
		wait++;
	if(wait>=180) {
		insidePipe++;
		wait=0;
	}

	}
}
