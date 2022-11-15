package entity.mob;

import java.awt.Graphics;
import java.util.Random;

import entity.Entity;
import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;
import tile_block.Tile;

public class Goomba extends Entity {
	private int frame=0;
	private double frameDelay=0;
	private boolean animate=true;
	private Random random = new Random();

	public Goomba(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		
		super(x, y, width, height, solid, id, handler);
		int dir =random.nextInt(2);
		switch(dir) {
		case 0:
			setVelx(-1);
			facing=0;
			break;
		case 1:
			setVelx(1);
			facing=1;
			break;	
		}
	
	}
public void render(Graphics g) {
	if(facing==0) {
		g.drawImage(Game.Goomba[frame+5].getBufferedImage(),x,y,width,height,null);
	}
	else if(facing==1) {
		g.drawImage(Game.Goomba[frame].getBufferedImage(),x,y,width,height,null);
	}
}
public void tick() {
	x+=velx;
	y+=vely;
if (velx==0) {
	vely=5;
}
	for(int i=0;i<handler.tile.size();i++) {
		Tile t =handler.tile.get(i);
		if (t.isSolid()) {
			
			if (getBoundsBottom().intersects(t.getBounds())) {
				setVely(0);
				if(falling) falling=false;
				
				//y=t.getY()-t.height;
			}//fixing it so it can fall of the edge
			else if(!falling) {
				falling=true;
				gravity=0.0;
			}
			if (getBoundsLeft().intersects(t.getBounds())) {
				setVelx(1);
				facing=1;
				
			}
			if (getBoundsRight().intersects(t.getBounds())) {
				setVelx(-1);
				facing=0;
			}
			
		}
	}
	if(falling) {
		gravity+=0.1;
		setVely((int)gravity);}
	
	//for implimenting our animation
	if(velx!=0) {
		//to set our animation speedk
		frameDelay=frameDelay+1;
		if(frameDelay>=1) {
			frame++;
			if(frame>=3) {
				frame=0;
			}
			frameDelay=0;
		}
	}
}
}
