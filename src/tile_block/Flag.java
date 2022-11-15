package tile_block;

import java.awt.Graphics;

import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;

public class Flag extends Tile {

	public Flag(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		
	}

	@Override
	public void render(Graphics g) {
	g.drawImage(Game.flag[2].getBufferedImage(),getX(), getY()+(64*3),width , 90, null);
	for(int i=0;i<3;i++) {
		g.drawImage(Game.flag[1].getBufferedImage(),getX(), getY()+(64*i),width , 80, null);
	}		
	g.drawImage(Game.flag[0].getBufferedImage(),getX(), getY()-64 ,width , 80, null);
	}

	@Override
	public void tick() {
		
	}

}
