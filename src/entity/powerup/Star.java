package entity.powerup;

import java.awt.Graphics;
import java.util.Random;

import entity.Entity;
import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;
import tile_block.Tile;

public class Star extends Entity {

	private Random random;
	public Star(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		random=new Random();
		int dir =random.nextInt(2);
		
		switch(dir) {
		case 0:
			setVelx(-1);
			break;
		case 1:
			setVelx(1);
			break;
		
		}
		falling=true;
		gravity=0.17;

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.star.getBufferedImage(),getX(), getY(), getWidth(),getHeight(), null);
		
	}

	@Override
	public void tick() {
		x+=velx;
		y+=vely;
		for(int i=0;i<handler.tile.size();i++) {
			Tile t=handler.tile.get(i);
			if(t.isSolid()) {
				if(getBoundsBottom().intersects(t.getBounds())) {
					jumping=true;
					gravity=8.0;
					falling=false;
				}
				if(getBoundsLeft().intersects(t.getBounds())) setVelx(3);
				if(getBoundsRight().intersects(t.getBounds())) setVelx(-3);
			}
		}
		
		if(jumping) {
			gravity-=0.17;
			setVely((int)-gravity); 
			if(gravity<=0.5) {
				jumping=false;
				falling=true;
			}
			
			}if(falling) {
				gravity+=0.17;
				setVely((int)gravity);
		}
		
	}

}
