package tile_block;

import java.awt.Color;
import java.awt.Graphics;

import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;

public class Wall extends Tile {

	public Wall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);

	}

	@Override// experimenting		g.setColor(Color.RED);     g.fillRect(x, y, width, height);
	
	public void render(Graphics g) {
	
		g.drawImage(Game.grass.getBufferedImage(),x,y,width,height,null);
	}

	@Override
	public void tick() {
	
		
	}

}
