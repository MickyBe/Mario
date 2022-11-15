package entity.powerup;

import java.awt.Graphics;
import java.util.Random;

import entity.Entity;
import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;
import tile_block.Tile;

public class Mushroom extends Entity{
	
	private Random random = new Random();

	public Mushroom(int x, int y, int width, int height, boolean solid, Id id, Handler handler,int type) {
		super(x, y, width, height, solid, id, handler);
		this.type=type;
		int dir =random.nextInt(2);
		switch(dir) {
		case 0:
			setVelx(-1);
			break;
		case 1:
			setVelx(1);
			break;
		
		}
	
	}
	public void render(Graphics g) {
		switch(getType()) {
		case 0:
			g.drawImage(Game.mushroom.getBufferedImage(), x, y, width, height, null);
			break;
		case 1:
			g.drawImage(Game.lifeMushroom.getBufferedImage(), x, y, width, height, null);
			break;
		}
		
	}
	public void tick() {
		x+=velx;
		y+=vely;
		
		for(int i=0;i<handler.tile.size();i++) {
			Tile t =handler.tile.get(i);
			if (t.isSolid()) {
				if (getBoundsBottom().intersects(t.getBounds())) {
					setVely(0);
					if(falling) falling=false;
					//y=t.getY()-t.height;
				}//fixing it so it can fall of the edge
				else if(!falling) {
					gravity=0.0;
					falling=true;
				}
				if (getBoundsLeft().intersects(t.getBounds())) {
					setVelx(1);
				}
				if (getBoundsRight().intersects(t.getBounds())) {
					setVelx(-1);
				}
			}
			
		}
		if(falling) {
			gravity+=0.1;
			setVely((int)gravity);}
	}

}
