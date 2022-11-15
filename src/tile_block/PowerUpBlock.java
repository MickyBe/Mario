package tile_block;

import java.awt.Graphics;


import entity.powerup.Mushroom;
import gfx.Sprite;
import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;

public class PowerUpBlock extends Tile {
	private Sprite powerUp;
	private Boolean poppedUp=false;
	
	private int spritey=getY();
	private int type;

	public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler,Sprite powerUp,int type) {
		super(x, y, width, height, solid, id, handler);
		this.type=type;
		this.powerUp=powerUp; 
		
	}

	@Override
	public void render(Graphics g) {
		if(!poppedUp)g.drawImage(powerUp.getBufferedImage(), x, spritey, width,height,null);
		if(!activated)g.drawImage(Game.powerUp.getBufferedImage(), x, y, width, height, null);
		else g.drawImage(Game.usedpowerUp.getBufferedImage(), x, y, width, height, null);
		
	}

	@Override
	public void tick() {
		if(activated&&!poppedUp) {
		spritey--;
		if(spritey<=y-height) {
			//power up is ambiguuse
	if(powerUp==Game.mushroom||powerUp==Game.lifeMushroom)handler.addEntity(new Mushroom(x,spritey,width,height,poppedUp, Id.mushroom,handler,type)); 
			
			poppedUp=true;
		}
		}
	}

}
