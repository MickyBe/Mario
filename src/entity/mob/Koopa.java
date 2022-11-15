package entity.mob;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import entity.Entity;
import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;
import mariomain.states.KoopaState;
import tile_block.Tile;

public class Koopa extends Entity{

	private Random random;
	private int shellCount;
	public Koopa(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	
	setVelx(1);
		 koopaState=KoopaState.WALKING;
	}

	@Override
	public void render(Graphics g) {
		if(koopaState==KoopaState.WALKING)g.drawImage(Game.Koopa[2].getBufferedImage(),x,y,width,height,null);
		if(koopaState==KoopaState.SPINNING)g.drawImage(Game.Koopa[1].getBufferedImage(),x,y,width,height,null);
		else if(koopaState==KoopaState.SHELL)g.drawImage(Game.Koopa[0].getBufferedImage(),x,y,width,height,null);
		
		
	}

	@Override
	public void tick() {
		x+=velx;
		y+=vely;
	if (koopaState==KoopaState.SHELL) {
		setVelx(0);
		shellCount++;
		if(shellCount>=300) {
			shellCount=0;
			koopaState=KoopaState.WALKING;
		}
		if(koopaState==KoopaState.WALKING||koopaState==KoopaState.SPINNING) {
			shellCount=0;
			if(velx==0) {
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
		}
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
					if(koopaState==KoopaState.SPINNING)setVelx(10);
					else setVelx(2);
					facing=1;
					
				}
				if (getBoundsRight().intersects(t.getBounds())) {
					if(koopaState==KoopaState.SPINNING)setVelx(-10);
					else setVelx(-2);
					facing=0;
				}
				
			}
		}
		if(falling) {
			gravity+=0.1;
			setVely((int)gravity);}
		
	}

}
