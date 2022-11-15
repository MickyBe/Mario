package tile_block;

import java.awt.Color;
import java.awt.Graphics;

import entity.mob.Plant;
import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;

public class Pipe extends Tile {

	public Pipe(int x, int y, int width, int height, boolean solid, Id id, Handler handler,int facing,boolean plant) {
		super(x, y, width, height, solid, id, handler);
		this.facing=facing;
		if(plant) { handler.addEntity(new Plant(getX(),getY()-64,64,64,true, Id.plant,handler));
		}
	}

	
	public void render(Graphics g) {
		g.drawImage(Game.pipe.getBufferedImage(),x,y,width,height,null);
		
	}

	
	public void tick() {
	
		
	}

}
