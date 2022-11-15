package tile_block;

import java.awt.Color;
import java.awt.Graphics;

import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;

public class Stairs extends Tile {

	public Stairs(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);

	}

	@Override// 
	
	public void render(Graphics g) {

		g.drawImage(Game.flag[2].getBufferedImage(),x,y,width , height, null);
		
	}
	@Override
	public void tick() {
	
		
	}

}
