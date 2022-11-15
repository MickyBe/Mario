package entity;

import java.awt.Graphics;

import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;
import tile_block.Tile;

public class Coin extends Entity {

	public Coin(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		
		super(x, y, width, height, solid, id, handler);

	}


	public void render(Graphics g) {
		g.drawImage(Game.coin.getBufferedImage(),x,y,width,height,null);
		
	}

	
	public void tick() {
		
		
	}

}
